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
    Flag heldFlag;
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

    //==============POWER UP ADDITIONS=================
//    boolean hasStealth = false;
    boolean hasFiringSpeed = false;
    boolean hasStamina = false;
    boolean hasHealth = false;
    //sent from interaction people (if player runs over it, turns true)
    int fireSpeedBoost = 2; //change
    int staminaBoost = 2; //chagne
    int healthBoost = 2; //change
    //=================================================

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
    /**
     * The constructor to make a new Player based on packed data.
     * @param packed 
     */
    public Player(String packed)
    {
        //error check this!!!!!
        launcher = new Launcher(); 
        powerUps = new ArrayList<PowerUp>();
        unpack(packed);
    }
    
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
    
    //Updates the player's stats to match those in the store
    public void updateStats(Store s) {
        ArrayList<Integer> stats = s.getStats();
        maxHealth = 100 + 10*stats.get(0);
        maxSpeed = 150 + (int)(7.5*stats.get(1));
        maxStamina = 100 + 20*stats.get(2);
        sightRange = 200 + 15*stats.get(3);
        //Launcher stats?
//        System.out.println("Updating stats in updateStats");
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

    public Launcher getLauncher () {return launcher;}

    public boolean getStealth()
    {
        return hasStealth == true;
    }
    
    public boolean canMove() { //this needs to be made
        return true;
    }
    
    
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
        int displayX = x/scaleDownFactor+offsetX;
        int displayY = y/scaleDownFactor+offsetY;
        int size = width/scaleDownFactor;
        int centerX = displayX+size/2;
        int centerY = displayY+size/2;
        int endX = centerX+(int)(size*Math.cos(this.getDirectionRadians()));
        int endY = centerY+(int)(size*Math.sin(this.getDirectionRadians()));
        if(scaleDownFactor==1) //Only on main client display
        {
            if(this.isOnOwnSide())
            {   //draw the circle of tagging zone
                g.setColor(Color.LIGHT_GRAY);
                int rad = size*3;
                g.drawOval(centerX-rad/2, centerY-rad/2,rad,rad);
            }
            g.setColor(Color.BLACK);
            g.drawString(name, displayX, displayY);
        }
        g.setColor(teamColor);
        g.fillRect(displayX,displayY,size,size);
        g.setColor(Color.BLACK);
        g.drawLine(centerX, centerY, endX, endY);
        if(hasFlag) {
            heldFlag.draw(g, scaleDownFactor, offsetX, offsetY);
        }
    }
    
    public void drawPlayerDisplayWindow(Graphics g, int xOff, int yOff)
    {
        g.setColor(teamColor);
        g.fillRect(xOff, yOff, 300,350);
        g.setColor(Color.BLACK);
        //Display name
        g.drawString(name, xOff+10, yOff+20);
        //Display Jail status
        if(inJail)
            g.drawString("IN JAIL!", xOff+120, yOff+20);
        //Display Flag status
        if(hasFlag)
            heldFlag.draw(g,1, xOff+300, yOff+10);
        //Display HEALTH / max
        drawStatusRectangle(g,250,maxHealth,health,xOff,yOff+80,"Health");
        //Display ENERGY / stamina
        drawStatusRectangle(g,250,maxStamina,energyLevel,xOff,yOff+110,"Energy");
        //Display SPEED / max
        //Display STEALTH
        //Display Sheild 
        //Display Sight Range
        //Display Launcher stats=======
        //Display PowerUps=============
        
/*      remainingSkillTokens = 40;
*/
        }
    /**
     * Draws a partially shaded rectangle to show the status of the parameter.  
     * @param g
     * @param top - the largest typical value (200 pixels wide)
     * @param max - this player's max
     * @param level - this player's level
     */
    private void drawStatusRectangle(Graphics g, int top, int max, int level, int xOff, int yOff, String type)
    {
//        System.out.println(type+" "+top+","+max+","+level);
        if(top == 0 || max == 0) return;
        int width = (200*max)/top;
        int fillWidth = (width*level)/max;
        g.drawString(type+" "+level, xOff+10, yOff+15);
        g.drawRect(xOff+100, yOff, width, 20);
        g.fillRect(xOff+100, yOff, fillWidth, 20);
    }

    /**
     * This method packs all of the instance variables for this Player
     * for sending over the Internet
     * @return 
     */
    public String pack() 
    {
        int J = 0, F = 0, C = 0, S=0; //J, F and C are integers representing the boolean values of inJail, hasFlag and teamColor
        if (inJail) { J = 1; }
        if (hasFlag) {F = 1; }
        if (teamColor == Color.BLUE) { C = 1; }
        if (this.hasStealth) { S=1; }
        
        String s = "PLA" + "=" + id + "=" + x + "=" + y + "=" + C
                + "=" + name + "=" + direction + "=" + J + "=" + F
                + "=" + maxStamina + "=" + energyLevel
                + "=" + maxHealth  + "=" + health
                + "=" + maxSpeed  + "=" + velocity
                + "=" + S + "=" + sightRange + "=" + shieldStrength
                 + "=" + launcher.pack();
        for (int i = 0; i < powerUps.size(); i++) { //Packs all powerUps the player has picked up stored in ArrayList powerUps
            s += "=" + powerUps.get(i).pack();
        }
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
        C = Integer.parseInt(parts[4]);
        name = parts[5];
        direction = Integer.parseInt(parts[6]);
        J = Integer.parseInt(parts[7]);
        F = Integer.parseInt(parts[8]);
        maxStamina = Integer.parseInt(parts[9]);
        energyLevel = Integer.parseInt(parts[10]);
        maxHealth = Integer.parseInt(parts[11]);
        health = Integer.parseInt(parts[12]);
        maxSpeed = Integer.parseInt(parts[13]);
        velocity = Integer.parseInt(parts[14]);
        S = Integer.parseInt(parts[15]);
        sightRange = Integer.parseInt(parts[16]);
        shieldStrength = Integer.parseInt(parts[17]);
        launcher.unpack(parts[18]);
        for (int i = 19; i < parts.length; i++) { //Loops through all packed PowerUps adding them to the player ArrayList
            PowerUp p = new PowerUp(0);
            p.unpack(parts[i]);
            powerUps.add(p);
        }

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
//        System.out.println("UNpackLite "+getDirection()+" = "+p.getDirection());
        direction = p.getDirection();
    }
    
    //other methods -----------------------------------------
    //Because the y-axis is reversed compared to the 'unit circle'
    //The rotate right and left may also seem opposite of expected.  
    public void rotateRight() { direction += 5; } //TODO: FIX THIS!@
    public void rotateLeft() { direction -= 5; } //TODO: FIX THIS!@
    public void moveForward()
    {
        if(velocity < this.maxSpeed)
            velocity++;
        velocity = 8; //TEMP <-- fix this!!!!
    }
    public void moveBackward()
    {
        if(velocity > -this.maxSpeed/2)
            velocity--;
    }
    public void stopMovement()
    {
        if(velocity > 0) velocity--;
        if(velocity < 0) velocity++;
    }
    public void sprintForward()
    {
        
    }
    public void reactToHit(Projectile p)
    {
        health -= p.getDamage();
    }
    public void reactToTag()
    {
        inJail = true;
    }
    public Projectile fireWeapon()
    {
        int size = (int)launcher.effectRadius;
        int damage = (int)launcher.getDamage();
        int range = (int)launcher.getRange();
        int speed = (int)launcher.getPSpeed();
        
        return new Projectile(x,y,size,range,speed + this.getVelocity(),this.getDirectionRadians(),damage);
    }
    public void pickUpFlag(Flag pickedUpFlag) {
        heldFlag = pickedUpFlag;
        hasFlag = true;
    }
    public void animate()
    {
        if(energyLevel > 0 && getVelocity() != 0)
        {
            x += (int)(getVelocity()*Math.cos(getDirectionRadians())); 
            y += (int)(getVelocity()*Math.sin(getDirectionRadians())); 
            energyLevel -= 2;
        }
        else
        {
            energyLevel++;
            if(energyLevel > maxStamina) 
                energyLevel = maxStamina;
        }
    }
    
    public boolean isOnOwnSide()
    {
        if (getColor() == Color.RED)
        {
            if (x < 1500)
            {
                return true;
            }
        }
        else if(getColor() == Color.BLUE)
        {
            if (x+this.width > 1500) //NEED TO CONSIDER WIDTH OF PLAYER HERE ****
            {
                return true;
            }
        }
        return false;
    
    }
    
    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }

}
