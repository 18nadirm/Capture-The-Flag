package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Map 
{
    private ArrayList<Barrier> barriers;
    private Flag redFlag; 
    private Flag blueFlag;
    private Jail redJail;
    private Jail blueJail;
    
    public void makeMap()
    {
        //The full map is 3000x2000
    }
    
    public void drawMainDisplay(Graphics g, Player c, int xOffset, int yOffset)
    {
        //This will be 700x700
        g.setColor(Color.yellow);
        g.fillRect(xOffset, yOffset, 700, 700);
        
    }
    
    public void drawRadarDisplay(Graphics g, Player c, int xOffset, int yOffset)
    {
        //This will be 300x200
        g.setColor(Color.yellow);
        g.fillRect(xOffset, yOffset, 300, 200);
    }
    
    public void drawFullDisplay(Graphics g, int xOffset, int yOffset)
    {
        //This will be for the server-side view and be 900x600
        
    }
}
