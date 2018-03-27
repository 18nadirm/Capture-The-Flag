package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Barrier implements GameObject
{
    //instance variables ------------------------------------
    int x, y;
    int width, height;

    //constructor(s) ----------------------------------------

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

     /**returns a  that the tank should not have
     *velocity in
     */
     public string cannotMove()
     {
       if(Player.getX() <= 0)
       {         
         if(Player.getY() <= 0)
         {
           return ;
         }
        return ;
       }
       else if(Player.getY() <= 0)
       {
         return ;
       }

     }


    public void draw(Graphics g, int scaleDownFactor, int offsetX, int offsetY)
    {

    }
    public int getStealth() { return 0; }
    public String pack()
    {
        return "BARR";
    }
    public void unpack(String s)
    {

    }

    //other methods -----------------------------------------

}
