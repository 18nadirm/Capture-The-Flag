package gameObjects;
//TODO: Improve the animate method, call it from the Map class

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile implements GameObject {

    //instance variables ------------------------------------

    int x, y;
    int radius;
    int startX, startY; //Where it started from 
    int maxRange;       //so that we can calculate if it exceeded maxRange.
    int damage;
    int xVel, yVel;
    
           
    //constructor(s) ----------------------------------------
    public Projectile(int xGiven, int yGiven,
                      int size, int range,
                      int speed, double directionRadians, 
                      int dmg) {
        x = xGiven; startX = x;
        y = yGiven; startY = y;
        radius = size;
        maxRange = range;
        damage = dmg;
        xVel = (int)(speed*Math.cos(directionRadians));
        yVel = (int)(speed*Math.sin(directionRadians));
    }
    public Projectile(String s)
    {
        unpack(s);
    }

    //accessors and modifiers (get and set methods) ---------

    public int getX() {return x;}
    public int getY() {return y;}
    public int getRadius() {return radius;}
    public int getDamage() {return damage;}
    public int getXVel() {return xVel;}
    public int getYVel() {return yVel;}
    public void setX(int xG) {x = xG;}
    public void setY(int yG) {y = yG;}
    public void setRadius(int radG) {radius = radG;}
    public void setDamage(int damageG) {damage = damageG;}
    public void setXVel(int xVelG) {xVel = xVelG;}
    public void setYVel(int yVelG) {yVel = yVelG;}

    //implemented methods -----------------------------------

    public Rectangle getBounds() {
        return new Rectangle(x, y, radius, radius);
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
    public void draw(Graphics g, int scaleDownFactor, int offsetX, int offsetY) 
    {
        g.setColor(Color.BLACK);
        int screenx = x / scaleDownFactor;
        int screeny = y / scaleDownFactor;
        int screenRadius = radius / scaleDownFactor;
        screenx += offsetX;
        screeny += offsetY;
        g.fillOval(screenx, screeny, screenRadius, screenRadius);
    }
    public void animate()
    {
        x+=xVel;
        y+=yVel;
    }

    public String pack() 
    {
        //Let's comment these better!  
        /*
        StoringPacking in this order: 
        x,y,xVel,yVel,width,height,damage
        */
        String p = "PRO" + 
                "X" + x + "Y" + y + 
                "A" + startX + "B" + startY + 
                "C" + xVel + "D" + yVel + 
                "E" + radius + 
                "F" + maxRange +
                "G" + damage;
        return p;
    }

    public void unpack(String s) {
        String temp;
        temp = s.substring(s.indexOf("X") + 1, s.indexOf("Y"));
        x = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("Y") + 1, s.indexOf("A"));
        y = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("A") + 1, s.indexOf("B"));
        startX = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("B") + 1, s.indexOf("C"));
        startY = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("C") + 1, s.indexOf("D"));
        xVel = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("D") + 1, s.indexOf("E"));
        yVel = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("E") + 1, s.indexOf("F"));
        radius = Integer.parseInt(temp);
        temp = s.substring(s.indexOf("F") + 1, s.indexOf("G"));
        maxRange = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("G") + 1, s.length());
        damage = Integer.parseInt(temp);
    }


    //other methods -----------------------------------------
    public boolean reachedMaxRange()
    {
        int dx = x-startX;
        int dy = y-startY;
        int hypSq = dx*dx+dy*dy;
        return hypSq >= maxRange*maxRange;
    }
}
