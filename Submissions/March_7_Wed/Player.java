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
    double direction; //in radians
    Color teamColor;
    String name;
    int energyLevel; //max at maxStamina
    public void setEnergyLevel(int mp) {if (mp <= maxHealth) health = mp; else health = maxHealth;}
    public int getEnergyLevel() {return energyLevel;}
    int health;
    public void setHealth(int hp) {if (hp <= maxHealth) health = hp; else health = maxHealth;}
    public int getHealth() {return health;}
    
    boolean inJail;
    boolean hasFlag;
    
    int maxSpeed = 200; // at speed pix/sec
    int maxStealth;
    int maxStamina = 150;
    int maxHealth = 150;
    int maxSightRange;
    int shieldStrength;
    int fps;
    public void setFPS (int f) {fps = f;}
    
    int cSpeed;
    int cStealth;
    int cStamina;
    int cSightRange;
    
    Launcher launcher; 
    ArrayList<PowerUp> powerUps; 
    
    //constructor(s) ----------------------------------------
    
    //accessors and modifiers (get and set methods) ---------
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,width,width);
    }
    public void draw(Graphics g)
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
