package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile implements GameObject {

    //instance variables ------------------------------------

    int x, y;
    int width, height;
    int damage;
    int xVel, yVel;
    boolean frozen;
           
    //constructor(s) ----------------------------------------
    public Projectile(int xGiven, int yGiven, int widthGiven, int heightGiven) {
        frozen = true;
        x = xGiven;
        y = yGiven;
        width = widthGiven;
        height = heightGiven;
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

        int screenx = x * scaleDownFactor;
        int screeny = y * scaleDownFactor;
        int screenwidth = width * scaleDownFactor;
        int screenheight = height* scaleDownFactor;
        screenx += offsetX;
        screeny += offsetY;
        g.fillRect(screenx, screeny, screenwidth, screenheight);
    }
    public void animate()
    {
        //We are testing here the 100 isnt final
        if (!frozen){
            x+=xVel;
            if (x > 100-width || x < 0) 
                frozen=true;
            y+=yVel;
            if (y > 100-height || y < 0) 
                frozen=true;
        }
    }

    public int getStealth() {
        return 0;
    }

    public String pack() {
        String p = "PROJ" + "X" + x + "Y" + y + "W" + width + "H" + height;
        return p;
    }

    public void unpack(String s) {
        String temp;
        int J, F, C;
        temp = s.substring(s.indexOf("X") + 1, s.indexOf("Y") - 1);
        x = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("Y") + 1, s.indexOf("V") - 1);
        y = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("W") + 1, s.indexOf("H") - 1);
        width = Integer.parseInt(temp);

        temp = s.substring(s.indexOf("H") + 1, s.length());
        height = Integer.parseInt(temp);
    }


    //other methods -----------------------------------------
}
