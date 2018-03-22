package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Player implements GameObject
{
    //instance variables ------------------------------------
    int x, y; 
    final int width = 10;
    double direction; //in radians
    
    Color teamColor;
    
    String name;
    
    int energyLevel; //max at maxStamina
    int health;
    
    boolean inJail;
    boolean hasFlag;
    
    //this is the speed stat, which can vary depending on powerups, or how the player allocates their stat points at the beginning of the game
    int velocity; // (pixels/sec) / frame/second = pixels/frame
    int maxSpeed = 200; // at speed pix/sec
    
    int maxStealth;
    int maxStamina = 150;
    int maxHealth = 150;
    int maxSightRange = 350; //map display is max 700p x 700p
    int shieldStrength;
    
    int fps;
    public void setFPS (int f) {fps = f;} //sets the frames per second in case it changes
    
    int cStealth;
    int cStamina;
    int cSightRange;
    
    Launcher launcher; 
    ArrayList<PowerUp> powerUps; 
    
    //constructor(s) ----------------------------------------
    Player (int spd, int hp, int mp, Color team)
    {
        velocity = spd / fps;
        health = hp;
        cStamina = mp;
        teamColor = team;
        cStealth = 0;
        cSightRange = 280;
        Random randy = new Random();
        x = randy.nextInt(1499); //setting random location within the side
        y = randy.nextInt(2000);
        if (teamColor == Color.red) {x+=1501;}
    }
    //accessors and modifiers (get and set methods) ---------
    public void setTeamColor(Color tc) {teamColor = tc;}
    public Color getTeamColor() {return teamColor;}
    
    public void setName (String n) {name = n;}
    public String getName () {return name;}
    
    public void setEnergyLevel(int mp) {if (mp <= maxHealth) health = mp; else health = maxHealth;}
    public int getEnergyLevel() {return energyLevel;}
    
    public void setHealth(int hp) {if (hp <= maxHealth) health = hp; else health = maxHealth;}
    public int getHealth() {return health;}
    
    public void setSpeed(int s) {if (s <= maxSpeed) velocity = s / fps; else velocity = maxSpeed;} //where s is in pix/sec
    public int getSpeed() {return velocity * fps;}
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,width,width);
    }
    public void draw(Graphics g)
    {
        g.fillRect(x,y,25,25);

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
        x += velocity;
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
