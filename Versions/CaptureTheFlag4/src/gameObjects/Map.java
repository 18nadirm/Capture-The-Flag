package gameObjects;
//TODO: Fix makeMap to add Barriers and the Jails (the consistent part of the Map)
//TODO: Fix the main Client display window to show objects in range (Flag, Projectiles, etc...)
//TODO: Fix drawMainDisplay to shade the sides and show central line
//TODO: Add stealth to Radar display
//TODO: Add other objects to radar display
//TODO: Interactions between objects!!!
//TODO: call animate methods of objects (instead of doing the animation here)
//TODO: 
//TODO: 


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import util.GenericComm;

public class Map 
{
    Random randy = new Random();

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
        powerups = new ArrayList<PowerUp>();
        lasers = new ArrayList<Projectile>();
        redFlag = new Flag(300,300,Color.RED);
        blueFlag = new Flag(2900,1500,Color.BLUE);
        redJail = new Jail(100,1000);
        blueJail = new Jail(2900,1000);
        
        makeMap();
    }
    public void makeMap()
    {
        //The full map is 3000x2000
        //Add in the barriers
        
        //Adding in some powerups
        for(int z=0; z<4; z++)
            powerups.add(new PowerUp(randy.nextInt(4)));
    }
    
    public void drawMainDisplay(Graphics g, int playerID, int xOffset, int yOffset)
    {
        //This will be 700x700
        Player thePlayer = this.getPlayerByID(playerID);
        
        //Draw a box around the display
        g.drawRect(xOffset, yOffset, 700, 700);
        
        //Calculate offset to center display on player.
        int xdiff=350-thePlayer.x;
        int ydiff=350-thePlayer.y;
        //The actual offset to display on the screen.
        int xOff = xdiff+xOffset; 
        int yOff = ydiff+yOffset;
        
        //Draw the center line??  
        if (thePlayer.x>=1150 && thePlayer.x<1850)
        {
            int linx = (thePlayer.x-350)+(1500-xdiff);
            g.drawRect(linx-1,10,2,200);
        }
        //Draw myself to the screen
        thePlayer.draw(g,1,xOff,yOff);
        //Shade the side(s) of the playing field
        //======================================
        
        //Draw the objects.
        for (Barrier i: barriers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        
        for (Player i: this.getPlayerList())
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        for (PowerUp i: powerups)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        for (Projectile i: lasers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }

        //These aren't working yet...
        if (redFlag.x-xdiff>=0 && redFlag.x-xdiff<=700 && redFlag.y-ydiff>=0 && redFlag.y-ydiff<=700)
            redFlag.draw(g,1,xOff,yOff);
        if (blueFlag.x-xdiff>=0 && blueFlag.x-xdiff<=700 && blueFlag.y-ydiff>=0 && blueFlag.y-ydiff<=700)
            blueFlag.draw(g,1,xOff,yOff);
        if (redJail.getX()-xdiff>=0 && redJail.getX()-xdiff<=700 && redJail.getY()-ydiff>=0 && redJail.getY()-ydiff<=700)
            redJail.draw(g,1,xOff,yOff);
        if (blueJail.getX()-xdiff>=0 && blueJail.getX()-xdiff<=700 && blueJail.getY()-ydiff>=0 && blueJail.getY()-ydiff<=700)
            blueJail.draw(g,1,xOff,yOff);       
    }
    
    
    
    public void drawRadarDisplay(Graphics g, int playerID, int xOffset, int yOffset)
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
            if (!i.hasStealth)
                i.draw(g, 10, xOffset, yOffset);
        }
        redFlag.draw(g,10,xOffset, yOffset);
        blueFlag.draw(g,10,xOffset, yOffset);
        redJail.draw(g,10,xOffset, yOffset);
        blueJail.draw(g,10,xOffset, yOffset);
        
        //You can see all tanks, unless stealthy (Yes, just tanks!)
        //Whatever your teammates can see, you can see (SAVE THIS FOR LATER - OR ELIMINATE)
        //Idea: make red dots appear where there are projectiles being shot
        //This will be 300x200
        
    }
    /**
     * This is for the server side!!!
     * @param g
     * @param xOffset
     * @param yOffset 
     */
    public void drawServerFullDisplay(Graphics g, int xOffset, int yOffset)
    {
        //This will be for the server-side view and be 600x400
        g.drawRect(xOffset, yOffset, 750, 500);
        redFlag.draw(g, 4, xOffset, yOffset);
        blueFlag.draw(g, 4, xOffset, yOffset);
        redJail.draw(g, 4, xOffset, yOffset);
        blueJail.draw(g, 4, xOffset, yOffset);
        for (Player i: playersMap.values())
            i.draw(g, 4, xOffset, yOffset);
        for (Projectile p : lasers)
            p.draw(g, 4, xOffset, yOffset);
        for (PowerUp p : powerups)
            p.draw(g, 4, xOffset, yOffset);        
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
            if(!p.inJail && p.canMove()) 
            {
                p.x += (int)(p.getVelocity()*Math.cos(p.getDirectionRadians())); 
                p.y += (int)(p.getVelocity()*Math.sin(p.getDirectionRadians())); 
                //check to see if the player is on a flag
                //if the player is on the flag, tell it to pick up the flag
                if(p.getBounds().intersects(redFlag.getBounds()) && !p.teamColor.equals(redFlag.myColor)) 
                    p.pickUpFlag(redFlag);
                if(p.getBounds().intersects(blueFlag.getBounds()) && !p.teamColor.equals(blueFlag.myColor)) 
                    p.pickUpFlag(blueFlag);

            
                //if the player is bumping into another player, find who is in home territory, the other reacts to the tag
                for(Player otherPlayer : this.getPlayerList()) 
                {
                    //if this isn't me, if they are on the opposing team, and if i'm on my side
                    if(p.getID() != otherPlayer.getID() && p.getColor() != otherPlayer.getColor() && p.isOnOwnSide()) 
                    {
                        if(p.getBounds().intersects(otherPlayer.getBounds())) 
                        {
                            otherPlayer.reactToTag();
                        }
                        
                    }
                }
                
                
                
            }
        }
        for(Projectile p : lasers)
        {
            p.x += (int)(p.getXVel());  //if there are separate x and y velocities, you do not need to multiply by the direction
            p.y += (int)(p.getYVel()); 
        }
        
        //TODO: Interactions between objects========================================================
        for(int i=0; i<lasers.size();i++)
        {
            Projectile projectile=lasers.get(i);
            for (Player player: this.getPlayerList())
            {
                if (player.getBounds().intersects(projectile.getBounds()))
                {
                    player.setHealth(player.getHealth()-projectile.getDamage());
                    lasers.remove(i);
                    i-=1;
                }                            
            }
            for (Barrier b: barriers)
            {
                if (b.getBounds().contains(projectile.getBounds()))
                {
                    //Removes laser when it hits a barrier
                    lasers.remove(i);
                    i-=1;
                }
            }
        }
        //Iterates through every player, seeing if they are touching a powerup
        for(Player p : this.getPlayerList())
        {
            for (int i=0; i<powerups.size();i++)
            {
                PowerUp powerup = powerups.get(i);
                //if I am touching a powerup, give me its benefit
                if (p.getBounds().intersects(powerup.getBounds()))
                {
                    //sets stealth
                    if (powerups.get(i).stealthBoost())
                        p.hasStealth=true;
                    //sets health
                    if (powerups.get(i).healthBoost())
                        p.health=p.maxHealth;
                    if (powerups.get(i).fireSpeedBoost())
                        p.launcher.projectileSpeed+=2;
                    //sets speed
                    if (powerups.get(i).staminaBoost())
                    {
                        p.maxSpeed+=2;
                        p.setSpeed(p.getSpeed()+2);
                    }
                    //removes the powerup from the arena and removes it from the list
                    //Currently there is no end time to the powerup
                    powerups.remove(powerups.get(i));
                    i-=1;
                }
                
            }
        }
    }
     

    
    
    public Collection<Player> getPlayerList()
    {
        return playersMap.values();
    }
    
    public Player getPlayerByID(int id)
    {
        if(playersMap.containsKey(id))
            return playersMap.get(id);
        else
            return new Player(id,0,0,Color.YELLOW,"ERROR");
    }
    
    //For network communication ==================================
    public String pack()
    {
        String result = "MAP";
        for(Player p : this.getPlayerList())
        {
            result += ","+p.pack();
        }
        for(Projectile p : this.lasers)
        {
            result += ","+p.pack();
        }
        for(PowerUp p : this.powerups)
        {
            result += ","+p.pack();
        }
        result += ","+redFlag.pack();
        result += ","+blueFlag.pack();
        
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
        lasers.clear();
        
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
                Projectile p = new Projectile(s);
                lasers.add(p);
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
