package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Player implements GameObject {

    //instance variables ------------------------------------
    int x, y;
    final int width = 10;
    int velocity; // (pixels/sec) / frame/second = pixels/frame
    double direction; //in radians
    Color teamColor;
    String name;
    int energyLevel; //max at maxStamina
    int health;

    boolean inJail;
    boolean hasFlag;

    //this is the speed stat, which can vary depending on powerups, or how the player allocates their stat points at the beginning of the game
    int maxSpeed = 200; // at speed pix/sec

    int maxStealth;
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

    int myPlayerNumber;
    Random randy = new Random();
    int id;
    int cSpeed;
    int localPort;

    //constructor(s) ----------------------------------------
    Player(int spd, int hp, int mp, Color team) {
        velocity = spd / fps;
        health = hp;
        cStamina = mp;
        teamColor = team;
        cStealth = 0;
        cSightRange = 280;
    }

    //constructor(s) ----------------------------------------
    public Player(int drewsNum) {
        id = drewsNum;
        x = randy.nextInt(500) + 100;//Use this to initialize variables.  
        y = randy.nextInt(500) + 100;
        if (randy.nextBoolean()) {
            teamColor = Color.BLUE;
        } else {
            teamColor = Color.RED;
        }
    }

    public Player() {
        x = randy.nextInt(1000) + 100;//Use this to initialize variables.  
        y = randy.nextInt(500) + 100;
        teamColor = new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255));
    }

    //accessors and modifiers (get and set methods) ---------
    //=======================================================
    public void setEnergyLevel(int mp) {
        if (mp <= maxHealth) {
            health = mp;
        } else {
            health = maxHealth;
        }
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setHealth(int hp) {
        if (hp <= maxHealth) {
            health = hp;
        } else {
            health = maxHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setSpeed(int s) {
        if (s <= maxSpeed) {
            velocity = s / fps;
        } else {
            velocity = maxSpeed;
        }
    } //where s is in pix/sec

    public int getSpeed() {
        return velocity * fps;
    }

    public void setFPS(int f) {
        fps = f;
    }

    public Color getColor() {
        return teamColor;
    }

    public int getID() {
        return id;
    }

    //implemented methods -----------------------------------
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, width);
    }

    /**
     * Draw the GameObject onto the Graphics page with the appropriate offset
     * and scale factor.
     *
     * @param g - the Graphics object to be drawn onto.
     * @param scaleDownFactor - the amount to divide your x,y by before drawing.
     * @param offsetX - the amount to add to your x coordinate before drawing.
     * @param offsetY - the amount to add to your y coordinate before drawing.
     */
    public void draw(Graphics g, int scaleDownFactor, int offsetX, int offsetY) {
        g.setColor(teamColor);
        int displayX = x / scaleDownFactor + offsetX;
        int displayY = y / scaleDownFactor + offsetY;
        int size = 25 / scaleDownFactor;
        g.fillRect(displayX, displayY, size, size);
    }

    public int getStealth() {
        return maxStealth;
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

        String s = "PLAY" + "X" + x + "Y" + y + "V" + velocity + "C" + C + "N" + name + "H" + health + "J" + J + "F" + F + "S" + cStamina + "M" + cStealth;
        return s;

    }

    public void unpack(String s) {
        String temp;
        int J, F, C;
        temp = s.substring(s.indexOf("X") + 1, s.indexOf("Y") - 1);
        x = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("Y") + 1, s.indexOf("V") - 1);
        y = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("V") + 1, s.indexOf("C") - 1);
        velocity = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("C") + 1, s.indexOf("N") - 1);
        C = Integer.parseInt(temp);

        name = s.substring(s.indexOf("N") + 1, s.indexOf("H") - 1);

        temp = s.substring(s.indexOf("H") + 1, s.indexOf("J") - 1);
        health = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("J") + 1, s.indexOf("F") - 1);
        J = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("F") + 1, s.indexOf("S") - 1);
        F = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("S") + 1, s.indexOf("M") - 1);
        cStamina = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("M") + 1, s.length());
        cStealth = Integer.parseInt(temp);

        if (J == 0) {
            inJail = false;

            if (J == 1) {
                inJail = true;
            }
            if (F == 0) {
                hasFlag = false;
            }
            if (F == 1) {
                hasFlag = true;
            }
        }
    }
        //other methods -----------------------------------------
    public void turn(boolean dir) {

    }

    public void moveForward() {

    }

    public void moveBackward() {

    }

    public void sprintForward() {

    }

    public void reactToHit(Projectile p) {

    }

    public void reactToTag() {

    }
}
