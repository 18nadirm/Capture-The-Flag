package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Player implements GameObject
{
    //instance variables ------------------------------------
    int x, y; 
    final int width = 10;
    int velocity; 
    double direction; //in radians
    Color teamColor;
    String name;
    int energyLevel; //max at maxStamina
    int health;
    boolean inJail;
    boolean hasFlag;
    
    int maxSpeed;
    int maxStealth;
    int maxStamina;
    int maxSightRange;
    int shieldStrength;
    
    Launcher launcher; 
    ArrayList<PowerUp> powerUps; 
    
    //constructor(s) ----------------------------------------
    
    //accessors and modifiers (get and set methods) ---------
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,width,width);
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
        
    }
    public int getStealth()
    {
        return maxStealth;
    }
    public String pack()
    {
        return "CHAR";
    }
    public void unpack(String s) 
    {
        
    }

    //other methods -----------------------------------------
    public void turn(boolean dir)
    {
        
    }
    public void moveForward()
    {
        
    }
    public void moveBackward()
    {
        
    }
    public void sprintForward()
    {
        
    }
    public void reactToHit(Projectile p)
    {
        
    }
    public void reactToTag()
    {
        
    }
}
