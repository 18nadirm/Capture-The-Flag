package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Map 
{
    private ArrayList<Barrier> barriers;
    private ArrayList<PowerUp> powerups;
    public ArrayList<Player> players;
    private ArrayList<Projectile> lasers;
    
    private Flag redFlag; 
    private Flag blueFlag;
    private Jail redJail;
    private Jail blueJail;
    
    //NEEDS A CONSTRUCTOR TO INITIALIZE VARIABLES!!!
    public Map()
    {
        barriers = new ArrayList<Barrier>();
        players = new ArrayList<Player>();
        redFlag = new Flag(200,200,Color.RED);
        blueFlag = new Flag(2900,1500,Color.BLUE);
        
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
        g.setColor(Color.BLUE);
        g.drawRect(xOffset,yOffset,150,200);  //maybe use fill and a lighter shade?
        g.setColor(Color.RED);
        g.drawRect(xOffset+150, yOffset, 150, 200);
        g.setColor(Color.BLACK);
        for (Barrier i: barriers)
        {
            i.draw(g, 10, xOffset, yOffset);
        }
        for (Player i: players)
        {
            i.draw(g, 10, xOffset, yOffset);
        }
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
        for (Player i: players)
        {
            i.draw(g, 4, xOffset, yOffset);
        }
        
    }
}
