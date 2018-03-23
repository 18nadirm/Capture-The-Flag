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
    int velocity; //pixels/frame * frame/second = pixels/second
    int direction; //in degrees
    Color teamColor;
    String name;
    int myPlayerNumber;
    int energyLevel; //max at maxStamina
    int health;
    
    boolean inJail;
    boolean hasFlag;
    
    int maxSpeed = 200; // at speed pix/sec
    int maxStealth;
    int maxStamina = 150;
    int maxHealth = 150;
    int maxSightRange;
    int shieldStrength;
    int fps;
    
    int cSpeed;
    int cStealth;
    int cStamina;
    int cSightRange;
    
    Launcher launcher; 
    ArrayList<PowerUp> powerUps; 
    
    //constructor(s) ----------------------------------------
    public Player()
    {
        //Use this to initialize variables.  
    }
    
    //accessors and modifiers (get and set methods) ---------
    //=======================================================
    public void setEnergyLevel(int mp) {if (mp <= maxHealth) health = mp; else health = maxHealth;}
    public int getEnergyLevel() {return energyLevel;}
    public void setHealth(int hp) {if (hp <= maxHealth) health = hp; else health = maxHealth;}
    public int getHealth() {return health;}
    public void setFPS (int f) {fps = f;}
    
    
    
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
        //CODE THIS FIRST, EVEN A SIMPLE VERSION!!!
    }
    public int getStealth()
    {
        return maxStealth;
    }
    /**
     * X
     * Y
     * V Velocity
     * C TeamColor 0=Red 1=Blue
     * N Name 
     * H Health 
     * J Jailed?   0=NotJailed 1=Jailed
     * F HasFlag?  0=NoFlag 1=HasFlag
     * @return
     */
    public String pack() {
        int J = 0, F = 0, C = 0; //J, F and C are integers representing the boolean values of inJail, hasFlag and teamColor
        if (inJail) {
            J = 1;
        }
        if (hasFlag) {
            F = 1;
        }
        if (teamColor == Color.BLUE) {
            C = 1;
        }

        String s = "PLAY" + "X" + x + "Y" + y + "V" + velocity + "C" + C + "N" + name + "H" + health + "J" + J + "F" + F;
        return s;

    }

    public void unpack(String s) {
        String temp;
        int J, F, C;
        for (int z = 0; z < s.length(); z++) {
            temp = s.substring(s.indexOf("X") + 1, s.indexOf("Y") - 1);
            x = Integer.parseInt(temp);
            
            temp = s.substring(s.indexOf("Y") + 1, s.indexOf("V") - 1);
            y =Integer.parseInt(temp);
            
            temp = s.substring(s.indexOf("V") + 1, s.indexOf("C") - 1);
            velocity =Integer.parseInt(temp);
            
            temp = s.substring(s.indexOf("C") + 1, s.indexOf("N") - 1);
            C =Integer.parseInt(temp);
            
            name = s.substring(s.indexOf("N") + 1, s.indexOf("H") - 1);
            
            temp = s.substring(s.indexOf("H") + 1, s.indexOf("J") - 1);
            health =Integer.parseInt(temp);
            
            temp = s.substring(s.indexOf("J") + 1, s.indexOf("F") - 1);
            J =Integer.parseInt(temp);
            
            temp = s.substring(s.indexOf("F") + 1, s.length());
            F =Integer.parseInt(temp);
        }
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
