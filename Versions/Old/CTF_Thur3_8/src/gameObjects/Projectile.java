package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile implements GameObject
{
    //instance variables ------------------------------------
    int x, y;
    int width, height;
    
    //constructor(s) ----------------------------------------
    public Projectile(int xGiven, int yGiven, int widthGiven, int heightGiven){
        x = xGiven;
        y = yGiven;
        width = widthGiven;
        height = heightGiven;
    }
    //accessors and modifiers (get and set methods) ---------
    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public void setX(int xG) {x = xG;}
    public void setY(int yG) {y = yG;}
    public void setWidth(int widthG) {width = widthG;}
    public void setHeight(int heightG) {height = heightG;}
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
        //You don't actually want to CHANGE x,y!!! 
        //Just CHANGE them for the display...
        x *= scaleDownFactor;
        y *= scaleDownFactor;
        //need to also scale your width/height !!!
        x += offsetX;
        y += offsetY;
        g.fillRect(x, y, width, height);
    }
    public int getStealth() { return 0; }
    public String pack()
    {
        return "PROJ";
    }
    public void unpack(String s) 
    {
        
    }
    
    //other methods -----------------------------------------
    
}
