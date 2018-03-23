package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import util.GenericComm;

public class Map 
{
    public ArrayList<Barrier> barriers;
    public ArrayList<PowerUp> powerups;
    public ArrayList<Projectile> lasers;
    public HashMap<Integer, Player> playersMap;
    
    
    private Flag redFlag; 
    private Flag blueFlag;
    private Jail redJail;
    private Jail blueJail;
    
    //NEEDS A CONSTRUCTOR TO INITIALIZE VARIABLES!!!
    public Map()
    {
        barriers = new ArrayList<Barrier>();
        playersMap = new HashMap<Integer, Player>();
        redFlag = new Flag(100,100,Color.RED);
        blueFlag = new Flag(2900,1500,Color.RED);
        
//        makeMap();
    }
    public void makeMap()
    {
        //The full map is 3000x2000

    }
    
    public void drawMainDisplay(Graphics g, Player c, int xOffset, int yOffset)
    {
        //Draw a box around the display
        g.drawRect(xOffset, yOffset, 700, 700);
        
        //Calculate offset to center display on player.
        int xdiff=350-c.x;
        int ydiff=350-c.y;
        //The actual offset to display on the screen.
        int xOff = xdiff+xOffset; 
        int yOff = ydiff+yOffset;
        
        //Draw the center line??  
        if (c.x>=1150 && c.x<1850)
        {
            int linx = (c.x-350)+(1500-xdiff);
            g.drawRect(linx-1,10,2,200);
        }
        //Draw myself to the screen
        c.draw(g,1,xOff,yOff);
        //Shade the side(s) of the playing field
        //======================================
        
        //Draw the objects.
        for (Barrier i: barriers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        
        Collection<Player> players = playersMap.values();
        for (Player i: players)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
//        for (PowerUp i: powerups)
//        {
//            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
//                i.draw(g, 1, xOff, yOff);
//        }
//        for (Projectile i: lasers)
//        {
//            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
//                i.draw(g, 1, xOff, yOff);
//        }

        //These aren't working yet...
        if (redFlag.x-xdiff>=0 && redFlag.x-xdiff<=700 && redFlag.y-ydiff>=0 && redFlag.y-ydiff<=700)
            redFlag.draw(g,1,xOff,yOff);
        if (blueFlag.x-xdiff>=0 && blueFlag.x-xdiff<=700 && blueFlag.y-ydiff>=0 && blueFlag.y-ydiff<=700)
            blueFlag.draw(g,1,xOff,yOff);
//        if (redJail.x-xdiff>=0 && redJail.x-xdiff<=700 && redJail.y-ydiff>=0 && redJail.y-ydiff<=700)
//            redJail.draw(g,1,xOff,yOff);
//        if (blueJail.x-xdiff>=0 && blueJail.x-xdiff<=700 && blueJail.y-ydiff>=0 && blueJail.y-ydiff<=700)
//            blueJail.draw(g,1,xOff,yOff);
        //This will be 700x700
        
    }
    
    public void drawRadarDisplay(Graphics g, Player c, int xOffset, int yOffset)
    {
        g.drawRect(xOffset, yOffset, 300, 200);
        Color blue = new Color(0, 0, 255, 100);
        g.setColor(blue);
        g.fillRect(xOffset,yOffset,150,200);  //maybe use fill and a lighter shade?
        Color red = new Color(255, 0, 0, 100);
        g.setColor(red);
        g.fillRect(xOffset+150, yOffset, 150, 200);
        g.setColor(Color.BLACK);
        for (Barrier i: barriers)
        {
            i.draw(g, 10, xOffset, yOffset);
        }
        for (Player i: playersMap.values())
        {
            i.draw(g, 10, xOffset, yOffset);
        }
        //Sets the redFlag to 0 and resets its position on the miniMap
        //Scale method not working
        redFlag.draw(g,10,xOffset, yOffset);
        blueFlag.draw(g,10,xOffset, yOffset);
//        redJail.draw(g,10,xOffset, yOffset);
//        blueJail.draw(g,10,xOffset, yOffset);
        
        //You can see all tanks, unless stealthy (Yes, just tanks!)
        //Whatever your teammates can see, you can see (SAVE THIS FOR LATER - OR ELIMINATE)
        //Idea: make red dots appear where there are projectiles being shot
        //This will be 300x200
        
    }
    
    public void drawFullDisplay(Graphics g, int xOffset, int yOffset)
    {
        //This will be for the server-side view and be 600x400
        g.drawRect(xOffset, yOffset, 750, 500);
        for (Player i: playersMap.values())
        {
            i.draw(g, 4, xOffset, yOffset);
        }
        
    }
    
    /**
     * This method updates (animates) all of the GameObjects
     * Players, Projectiles, etc based on their known status.  
     * This will need to occur both on the Client and Server sides.
     */
    public void updateGameObjects()
    {
        for(Player p : this.getPlayerList())
        {
            p.x += (int)(p.getVelocity()*Math.cos(p.getDirectionRadians())); 
            p.y += (int)(p.getVelocity()*Math.sin(p.getDirectionRadians())); 
        }
    }
    
    public Collection<Player> getPlayerList()
    {
        return playersMap.values();
    }
    
    //For network communication
    public String pack()
    {
        String result = "MAP";
        for(Player p : this.getPlayerList())
        {
            result += ","+p.pack();
        }
        
        return result;
    }
    /**
     * This method unpacks all of a game's data and returns the 
     * packed String for the particular player unpacking the Map.
     * @param s
     * @param playerNum
     * @return 
     */
    public String unpack(String full, int playerNum)
    {
        debugMsg("For: " + playerNum + "  Map unpacking: "+full);
        String playerString = "NotFound";
        
        //clear the lists to refill them.  
        playersMap.clear();
        
        //Unpack the info (separate by commas)
        String[] parts = full.split(",");
        for(String s : parts)
        {
            if(s.startsWith("PLA"))
            {
                Player p = new Player(s); //unpack and construct!
                int num = p.id;
                playersMap.put(num, p);
                if(p.id == playerNum)
                    playerString = s;
            }
            else if(s.startsWith("FLA"))
            {
            }
            else if(s.startsWith("PRO"))
            {
            }
            else if(s.startsWith("POW"))
            {
            }
        }

        return playerString; 
    } //end of unpack method.  
    
    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }
    
}
