package gameObjects;
//TODO: check/improve the implementation of speed/velocity/fps
//TODO: make an animate method (to be used in Map class)
//TODO: if inJail, you can't move out.
//TODO: you can't move off the Map
//TODO: implement reactToHit(Projectile p)
//TODO: implement reactToTag()
//TODO: pack/unpack are missing elements including Launcher and PowerUps
//TODO: look through Powerups to improve performance
//TODO: change getStealth to return boolean and use in Map class
//TODO: Player Status Display! 


import client.CaptureTheFlag;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import util.GenericComm;

public class Player implements GameObject
{
    final int width = 25;
    int fps = CaptureTheFlag.FPS;
    Random randy = new Random();

    //instance variables ------------------------------------
    int id;         //The id number assigned by the server (important!)
    int x, y;       //Location of this Player
    Color teamColor;    
    String name;    

    int direction;  //in degrees  
    boolean inJail;
    boolean hasFlag;
    Launcher launcher; 
    ArrayList<PowerUp> powerUps;
    
    int remainingSkillTokens; //For store
    /*
    The stats below can vary depending on PowerUps and can be changed at the Store between games
    */
    
    int maxStamina;
    int energyLevel; //max at maxStamina

    int maxHealth;
    int health;     //when your health hits zero - go to Jail!
    
    int maxSpeed; // at speed pix/sec   
    int velocity; // (pixels/sec) / frame/second = pixels/frame

    boolean hasStealth;

    int sightRange;

    int shieldStrength;
    
    //constructor(s) ----------------------------------------
    public Player (int idNum, int xx, int yy, Color team, String n)
    {
        id = idNum;
        x=xx; y=yy; 
        teamColor = team;
        name = n;
        init();
    }
//    public Player(int drewsNum)
//    {
//        id = drewsNum;
//        init();    
//    }
    /**
     * The constructor to make a new Player based on packed data.
     * @param packed 
     */
    public Player(String packed)
    {
        //error check this!!!!!
        unpack(packed);
    }
//    public Player()
//    {
//        x = randy.nextInt(1000) + 100;//Use this to initialize variables.  
//        y = randy.nextInt(500) + 100;
//        teamColor = new Color(randy.nextInt(255),randy.nextInt(255),randy.nextInt(255));
//    }
    
    private void init()
    {
        //This initializes the variables for a generic starting player. 
        //x,y, name, id, and teamColor should be set in the constructor.  
        direction = 0; //in degrees    
        inJail = false;
        hasFlag = false;
        launcher = new Launcher(); 
        powerUps = new ArrayList<PowerUp>();
    
        maxStamina = 150;
        energyLevel = maxStamina; 
        maxHealth = 150;
        health = maxHealth;
        maxSpeed = 200; // (pixels/sec) / frame/second = pixels/frame
        velocity = 0; //Start not moving!!!
        hasStealth = false;
        sightRange = 350; //map display is max 700p x 700p
        shieldStrength = 22;
        
        remainingSkillTokens = 40;
    }
    
    //accessors and modifiers (get and set methods) ---------
    public void setColor(Color tc) {teamColor = tc;}
    public Color getColor() {return teamColor;}
    
    public int getID() {return id;}
    
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
        int J = 0, F = 0, C = 0, S=0; //J, F and C are integers representing the boolean values of inJail, hasFlag and teamColor
        if (inJail) { J = 1; }
        if (hasFlag) {F = 1; }
        if (teamColor == Color.BLUE) { C = 1; }
        if (this.hasStealth) { S=1; }
        
        String s = "PLA" + "=" + id + "=" + x + "=" + y + 
                "=" + velocity + "=" + C + "=" + name + "=" + health + 
                "=" + J + "=" + F + "=" + energyLevel + "=" + S + 
                "=" + direction;
        return s;

    }

    public void unpack(String s) 
    {
        int J, F, C, S;
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
        energyLevel = Integer.parseInt(parts[10]);
        S = Integer.parseInt(parts[11]);
        direction = Integer.parseInt(parts[12]);

        inJail = J>0;
        hasFlag = F>0;
        hasStealth = S>0;
        if (C == 0) 
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
    //Because the y-axis is reversed compared to the 'unti circle'
    //The rotate right and left may also seem opposite of expected.  
    public void rotateRight() { direction += 5; } //TODO: FIX THIS!@
    public void rotateLeft() { direction -= 5; } //TODO: FIX THIS!@
    public void moveForward()
    {
        velocity = 8;//this.maxSpeed/fps;  FIX THIS!!!!!
    }
    public void moveBackward()
    {
        velocity = 0;
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
    public Projectile fireWeapon()
    {
        //TODO: this needs to be improved to incorporate Launcher
        //Also need to incorporate the direction of the launch.
        return new Projectile(x,y,-1,-1,20,20);
    }
//    public void animate()
//    {
//        x += (int)(getVelocity()*Math.cos(getDirectionRadians())); 
//        y += (int)(getVelocity()*Math.sin(getDirectionRadians())); 
//        velocity-=SLOW_DOWN_AMOUNT;
//    }
    
    
    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }

}
