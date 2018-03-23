package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import util.GenericComm;

public class Player implements GameObject
{
    //instance variables ------------------------------------
    // REALLY NEED TO SIMPLIFY THIS!!!! ---------------------
    int x, y; 
    final int width = 25;
    int direction; //in degrees
    
    Color teamColor;
    
    String name;
    
    int energyLevel; //max at maxStamina
    int health;
    
    boolean inJail;
    boolean hasFlag;
    
    //this is the speed stat, which can vary depending on powerups, or how the player allocates their stat points at the beginning of the game
    int velocity; // (pixels/sec) / frame/second = pixels/frame
    int maxSpeed = 200; // at speed pix/sec
    
    boolean hasStealth;
    int maxStamina = 150;
    int maxHealth = 150;
    int maxSightRange = 350; //map display is max 700p x 700p
    int shieldStrength;
    
    int fps;
    
    int cStealth;
    int cStamina;
    int cSightRange;
    
    Launcher launcher; 
    ArrayList<PowerUp> powerUps;
    
    int id;

    Random randy = new Random();
    
    //constructor(s) ----------------------------------------
    Player (int spd, int hp, int mp, Color team)
    {
        init();
        velocity = spd / fps;
        health = hp;
        cStamina = mp;
        teamColor = team;
        cStealth = 0;
        cSightRange = 280;
    }
    public Player(int drewsNum)
    {
        id = drewsNum;
        init();    
    }
    /**
     * The constructor to make a new Player based on packed data.
     * @param packed 
     */
    public Player(String packed)
    {
        //error check this!!!!!
        unpack(packed);
    }
    public Player()
    {
        x = randy.nextInt(1000) + 100;//Use this to initialize variables.  
        y = randy.nextInt(500) + 100;
        teamColor = new Color(randy.nextInt(255),randy.nextInt(255),randy.nextInt(255));
    }
    
    private void init()
    {
        x = randy.nextInt(500) + 100;//Use this to initialize variables.  
        y = randy.nextInt(500) + 100;
        if(randy.nextBoolean())
            teamColor = Color.BLUE;
        else
            teamColor = Color.RED;
        direction = 0; //in degrees
    
        name = "nobody";
    
        energyLevel = 100; //max at maxStamina
        health = 99;
    
        inJail = false;
        hasFlag = false;
    
    //this is the speed stat, which can vary depending on powerups, or how the player allocates their stat points at the beginning of the game
        velocity = 5; // (pixels/sec) / frame/second = pixels/frame
        maxSpeed = 200; // at speed pix/sec
    
        hasStealth = false;
        maxStamina = 150;
        maxHealth = 150;
        maxSightRange = 350; //map display is max 700p x 700p
        shieldStrength = 22;
        fps = 60;
        cStealth = 1;
        cStamina = 2;
        cSightRange = 3;
    
        launcher = new Launcher(); 
        powerUps = new ArrayList<PowerUp>();
        
    }
    
    //accessors and modifiers (get and set methods) ---------
    public void setColor(Color tc) {teamColor = tc;}
    public Color getColor() {return teamColor;}
    
    public int getID() {return id;}
    
    public void rotateRight() { direction += 5; } //TODO: FIX THIS!@
    public double getDirectionRadians() { return direction*Math.PI/180; }
    public int getDirection() { return direction; }
    
    public int getVelocity() { return velocity; } 
    
    public void setName (String n) {name = n;}
    public String getName () {return name;}
    
    public void setEnergyLevel(int mp) {if (mp <= maxHealth) health = mp; else health = maxHealth;}
    public int getEnergyLevel() {return energyLevel;}
    
    public void setHealth(int hp) {if (hp <= maxHealth) health = hp; else health = maxHealth;}
    public int getHealth() {return health;}
    
    public void setSpeed(int s) {if (s <= maxSpeed) velocity = s / fps; else velocity = maxSpeed;} //where s is in pix/sec
    public int getSpeed() {return velocity * fps;}
    
    public void setFPS (int f) {fps = f;} //sets the frames per second in case it changes

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
        g.setColor(teamColor);
        int displayX = x/scaleDownFactor+offsetX;
        int displayY = y/scaleDownFactor+offsetY;
        int size = width/scaleDownFactor;
        g.fillRect(displayX,displayY,size,size);
        g.setColor(Color.BLACK);
        int centerX = displayX+size/2;
        int centerY = displayY+size/2;
        int endX = centerX+(int)(size*Math.cos(this.getDirectionRadians()));
        int endY = centerY+(int)(size*Math.sin(this.getDirectionRadians()));
        g.drawLine(centerX, centerY, endX, endY);
    }
//    public void draw(Graphics g)
//    {
//        g.setColor(teamColor);
//        g.fillRect(x,y,width,width);
//        
//    }
    public int getStealth()
    {
        return 0;
    }
    /**
     * X
     * Y
     * V: Velocity C: TeamColor 0=Red 1=Blue N: Name H: Health J: Jailed?
     * 0=NotJailed 1=Jailed F: HasFlag? 0=NoFlag 1=HasFlag S: Current Stamina M:
     * Current Stealth
     *
     * @return
     */
    public String pack() 
    {
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

        String s = "PLA" + "=" + id + "=" + x + "=" + y + 
                "=" + velocity + "=" + C + "=" + name + "=" + health + 
                "=" + J + "=" + F + "=" + cStamina + "=" + cStealth + 
                "=" + direction;
        return s;

    }

    public void unpack(String s) 
    {
        int J, F, C;
        debugMsg("Player unpacking: "+s);
        String[] parts = s.split("=");

        id = Integer.parseInt(parts[1]);
        x = Integer.parseInt(parts[2]);        
        y = Integer.parseInt(parts[3]);
        velocity = Integer.parseInt(parts[4]);
        C = Integer.parseInt(parts[5]);
        name = parts[6];
        health = Integer.parseInt(parts[7]);
        J = Integer.parseInt(parts[8]);
        F = Integer.parseInt(parts[9]);
        cStamina = Integer.parseInt(parts[10]);
        cStealth = Integer.parseInt(parts[11]);
        direction = Integer.parseInt(parts[12]);

        if (J == 0) {
            inJail = false;
        }
        if (J == 1) {
            inJail = true;
        }
        if (F == 0) {
            hasFlag = false;
        }
        if (F == 1) {
            hasFlag = true;
        }
        if (C ==0) 
            setColor(Color.RED);
        else
            setColor(Color.BLUE);
    }
    /**
     * Just copies things that the user might have changed.  
     */
    public void unpackLite(Player p)
    {       
        velocity = p.getVelocity();
        direction = p.getDirection();
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
    
    
    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }

}
