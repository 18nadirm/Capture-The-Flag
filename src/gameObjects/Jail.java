package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Jail implements GameObject
{
    int width=80;
    int height=80;

    //instance variables ------------------------------------
    int x, y;
    
    //constructor(s) ----------------------------------------
    public Jail(int xx, int yy)
    {
        x=xx; y=yy;
    }
    //accessors and modifiers (get and set methods) ---------
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,width,height);
    }
    /**
     * Draw the GameObject onto the Graphics page
     * with the appropriate offset and scale factor.  
     * @param g - the Graphics object to be drawn onto.
     * @param scaleDownFactor - the amount to divide your x,y by before drawing.
     * @param offsetX - the amount to add to your x coordinate before drawing. 
     * @param offsetY - the amount to add to your y coordinate before drawing.
     */
    public void draw(Graphics g, int scaleDownFactor, int offsetX, int offsetY)
    {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
    }
    public int getStealth() { return 0; }
    public String pack()
    {
        return "JAIL";
    }
    public void unpack(String s) 
    {
        
    }
    
    //other methods -----------------------------------------
    
}
