package gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface GameObject 
{
    public Rectangle getBounds();
    public void draw(Graphics g, int scaleDownFactor, int offsetX, int offsetY);
//    public int getStealth();
    
    //For network communication
    public String pack();
    public void unpack(String s);
    
}
