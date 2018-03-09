package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Flag implements GameObject
{
    //instance variables ------------------------------------
    int x, y;
    int width, height;
    Color myColor;
    
    
    //constructor(s) ----------------------------------------
    public Flag(int xx, int yy, Color c)
    {
        x=xx; y=yy;
        myColor = c;
        width = 40;
        height = 40;
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
         g.setColor(myColor);
        int displayX = x*scaleDownFactor+offsetX;
        int displayY = y*scaleDownFactor+offsetY;
        int displayWidth = width*scaleDownFactor;
        int displayHeight = height*scaleDownFactor;
        
        int gridDiv = 4;
        int checkWidth = displayWidth/gridDiv;
        int checkHeight = displayHeight/gridDiv;
        for(int row=0; row<gridDiv; row++)
            for(int col=0; col<gridDiv; col++)
            {
                if((row+col) %2 == 0)
                    g.setColor(myColor);
                else
                    g.setColor(Color.YELLOW);
                g.fillRect(displayX+col*checkWidth, 
                           displayY+row*checkHeight,
                           displayWidth/gridDiv, 
                           displayHeight/gridDiv);
            }
    }
    public int getStealth() { return 0; }
    public String pack()
    {
        int C; //C represnts the team color, when the C is 0, the flag is neutral right now, 
        //when C is 1, the color is red, when C is blue, the color is blue 2
        
        if(myColor != Color.BLUE && myColor != Color.RED)
            C = 0;
        
        if(myColor == Color.BLUE)
            C = 2;
        
        if (myColor == Color.RED)
            C = 1;
            
        return "FLAG";
    }
    public void unpack(String s) 
    {
        
    }

    //other methods -----------------------------------------
    
}
