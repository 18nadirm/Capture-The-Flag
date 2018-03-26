package gameObjects;
//TODO: Add multipliers into Player class for these
//TODO: Calculate and store the end time when constructing a PowerUp
//TODO: Add check to remove Projectiles that have timed out (in Map class)


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class PowerUp implements GameObject
{
    final int WIDTH = 10;
    final int HEIGHT = 10;
    Random randy = new Random();
    //instance variables ------------------------------------
    int x = randy.nextInt(3000);
    int y = randy.nextInt(2000);
    int countdown = 1000;
    int property; //What type of boost this PowerUp gives 
//    int frameCount = 0;
//    boolean hasStealth = false;
//    boolean hasStamina = false;
//    boolean hasHealth = false;
//    boolean hasFireSpeed = false;
    
    //constructor(s) ----------------------------------------
    public PowerUp()
    {
        x = randy.nextInt(3000);
        y = randy.nextInt(2000);
        countdown = 1000;
        property = randy.nextInt(4); 
        
        //to add later
        //int property = randy.nextInt(4);
        //in constructor, have a random int from 1 to 4 determine property
        //if int is 1, stealth (disappears from map grid)
        //if int is 2, firingSpeed (makes projectile speed higher
        //if int is 3, speed (makes player speed higher)
        //if int is 4, health (adds a constant to health)
        //so on
    }
    //accessors and modifiers (get and set methods) ---------
    public int getY(){return y;}
    public int getX(){return x;}
    //each of these methods returns a boolean that says if we have a power up
    //in player/projectile class, the multipliers/add constants are there
    public boolean stealthBoost() { return property == 0; }
    public boolean fireSpeedBoost() { return property == 1; }
    public boolean healthBoost() { return property == 2; }
    public boolean staminaBoost() { return property == 3; }
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,WIDTH,HEIGHT);
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
        //each has a different color
            if(property == 0)
            {
                g.setColor(Color.WHITE);
            }
            else if(property == 1)
            {
                g.setColor(Color.BLUE);
            }
            else if(property == 2)
            {
                g.setColor(Color.RED);
            }
            else if(property == 3)
            {
                g.setColor(Color.ORANGE);
            }
            g.drawRect(x+offsetX, y+offsetY, WIDTH/scaleDownFactor, HEIGHT/scaleDownFactor);
    }
    public String pack()
    {
        String s = "PWR" + "=" + x + "=" + y + "=" + property;
        return s;
    }
    public void unpack(String s)
    {
        String[] vars = s.split("=");

        x = Integer.parseInt(vars[1]);
        y = Integer.parseInt(vars[2]);
        property = Integer.parseInt(vars[3]);
    }
    
    //other methods -----------------------------------------
    
}
