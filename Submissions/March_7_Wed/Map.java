package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Map 
{
    private ArrayList<Barrier> barriers;
    private ArrayList<PowerUp> powerups;
    private ArrayList<Player> players;
    private ArrayList<Projectile> lasers;
    
    private Flag redFlag; 
    private Flag blueFlag;
    private Jail redJail;
    private Jail blueJail;
    
    public void makeMap()
    {
        
        //The full map is 3000x2000
    }
    
    public void drawMainDisplay(Graphics g, Player c)
    {
        g.drawRect(10, 10, 700, 700);
        
        int xdiff=350-c.x;
        int ydiff=350-c.y;
        if (c.x>=1150 && c.x<1850)
        {
            int linx = (c.x-350)+(1500-xdiff);
            g.drawRect(linx-1,10,2,200);
        }
        c.draw(g,1,xdiff,ydiff);
        for (Barrier i: barriers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xdiff, ydiff);
        }
        for (Player i: players)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xdiff, ydiff);
        }
        for (PowerUp i: powerups)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xdiff, ydiff);
        }
        for (Projectile i: lasers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xdiff, ydiff);
        }
        if (redFlag.x-xdiff>=0 && redFlag.x-xdiff<=700 && redFlag.y-ydiff>=0 && redFlag.y-ydiff<=700)
            redFlag.draw(g,1,xdiff,ydiff);
        if (blueFlag.x-xdiff>=0 && blueFlag.x-xdiff<=700 && blueFlag.y-ydiff>=0 && blueFlag.y-ydiff<=700)
            blueFlag.draw(g,1,xdiff,ydiff);
        if (redJail.x-xdiff>=0 && redJail.x-xdiff<=700 && redJail.y-ydiff>=0 && redJail.y-ydiff<=700)
            redJail.draw(g,1,xdiff,ydiff);
        if (blueJail.x-xdiff>=0 && blueJail.x-xdiff<=700 && blueJail.y-ydiff>=0 && blueJail.y-ydiff<=700)
            blueJail.draw(g,1,xdiff,ydiff);
        //This will be 700x700
        
    }
    
    public void drawRadarDisplay(Graphics g, Player c)
    {
        g.drawRect(730, 10, 300, 200);
        g.setColor(Color.BLUE);
        g.drawRect(730,10,150,200);
        g.setColor(Color.RED);
        g.drawRect(880, 10, 150, 200);
        g.setColor(Color.BLACK);
        for (Barrier i: barriers)
        {
            i.draw(g, 10, 730, 10);
        }
        for (Player i: players)
        {
            i.draw(g, 10, 730, 10);
        }
        redFlag.draw(g,10,730,10);
        blueFlag.draw(g,10,730,10);
        redJail.draw(g,10,730,10);
        blueJail.draw(g,10,730,10);
        
        //You can see all tanks, unless stealthy
        //Whatever your teammates can see, you can see
        //Idea: make red dots appear where there are projectiles being shot
        //This will be 300x200
        
    }
    
    public void drawFullDisplay(Graphics g)
    {
        //This will be for the server-side view and be 900x600
        
    }
}
