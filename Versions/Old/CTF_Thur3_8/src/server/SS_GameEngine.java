package server;

import gameObjects.Flag;
import gameObjects.Map;
import gameObjects.Player;
import gameObjects.PowerUp;
import gameObjects.Projectile;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import util.AnimationPanel;

public class SS_GameEngine extends AnimationPanel
{
    Map map;
    ArrayList<Player> characters; 
    ArrayList<Flag> flag;
    ArrayList<PowerUp> powerups;
    ArrayList<Projectile> projectile;
    
    //Constructor
    public SS_GameEngine()
    {
        super("CaptureTheFlag - SERVER", 1200, 700);
        //initialize variables
    }
    
    /**
     * This method receives a message from a client and updates 
     * the game accordingly.  
     * @param theInput
     * @param myConnNumber 
     */
    public void processInput(String theInput, int myConnNumber) 
    {

    }
    
    /**
     * This is the method that returns all of the game data to a
     * client who requests it.  
     * @param connectionNum
     * @return 
     */
    public ArrayList<String> getStatusUpdate(int connectionNum)
    {
        ArrayList<String> gameData = new ArrayList<String>();
        
        gameData.add("Hello."); //Just for testing.
        
        return gameData;
    }
    /*
    public int assignTeam()
    public void assignStartingLocation()
    
    */    
    
    protected Graphics renderFrame(Graphics g) 
    {
        //Draw the entire display.  
        
        //Draw a circle that follows the mouse.
        g.setColor(Color.BLACK);
        g.fillOval(mouseX-10, mouseY-10, 20,20);
        
        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.BLACK);
        g.drawString("SERVER WINDOW", 10, 12);
        g.drawString("frame="+frameNumber,200,12);
        
        return g;
    }//--end of renderFrame method--

}

