package gameObjects;
//TODO: Remove the 'frozen' property from this class
//TODO: The constructor needs to set the damage value!
//TODO: Improve the animate method, call it from the Map class

import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile implements GameObject {

    //instance variables ------------------------------------

    int x, y;
    int width, height;
    int damage;
    int xVel, yVel;
           
    //constructor(s) ----------------------------------------
    public Projectile(int xGiven, int yGiven,
                        int xVelGiven, int yVelGiven,
                        int widthGiven, int heightGiven) {
        damage = 10;
        x = xGiven;
        y = yGiven;
        xVel = xVelGiven;
        yVel = yVelGiven;
        width = widthGiven;
        height = heightGiven;
    }
    public Projectile(String s)
    {
        unpack(s);
    }

    //accessors and modifiers (get and set methods) ---------

    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getDamage() {return damage;}
    public int getXVel() {return xVel;}
    public int getYVel() {return yVel;}
    public void setX(int xG) {x = xG;}
    public void setY(int yG) {y = yG;}
    public void setWidth(int widthG) {width = widthG;}
    public void setHeight(int heightG) {height = heightG;}
    public void setDamage(int damageG) {damage = damageG;}
    public void setXVel(int xVelG) {xVel = xVelG;}
    public void setYVel(int yVelG) {yVel = yVelG;}

    //implemented methods -----------------------------------

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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

        int screenx = x / scaleDownFactor;
        int screeny = y / scaleDownFactor;
        int screenwidth = width / scaleDownFactor;
        int screenheight = height / scaleDownFactor;
        screenx += offsetX;
        screeny += offsetY;
        g.fillRect(screenx, screeny, screenwidth, screenheight);
    }
    public void animate()
    {
        //WE changed the animate method in the map class, but did not want to submit it because it might get confusing with submissions
            x+=xVel;
            y+=yVel;
    }

//    public int getStealth() {
//        return 0;
//    }

    public String pack() 
    {
        //Let's comment these better!  
        /*
        StoringPacking in this order: 
        x,y,xVel,yVel,width,height,damage
        */
        String p = "PRO" + 
                "X" + x + "Y" + y + 
                "A" + xVel + "B" + yVel + 
                "W" + width + "H" + height +
                "D" + damage;
        return p;
    }

    public void unpack(String s) {
        String temp;
        temp = s.substring(s.indexOf("X") + 1, s.indexOf("Y"));
        x = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("Y") + 1, s.indexOf("A"));
        y = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("A") + 1, s.indexOf("B"));
        xVel = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("B") + 1, s.indexOf("W"));
        yVel = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("W") + 1, s.indexOf("H"));
        width = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("H") + 1, s.indexOf("D"));
        height = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("D") + 1, s.length());
        damage = Integer.parseInt(temp);
    }

//    public void fireProjectile(int startX, int startY){
//        
//        //Initialize this projectile based on starting location to move upwards.
//        x=startX;
//        y=startY;
//        xVel= 0;
//        yVel= 8;
//        frozen=false;
//    }

    // we are going to set up getSpeed() and setSpeed() methods because everyone is going to 
    //use int values, and we will decide what the size of the projectile is
    //we are going to make a final method and test it to draw the projectilein the client side 
    //

    //other methods -----------------------------------------
}
