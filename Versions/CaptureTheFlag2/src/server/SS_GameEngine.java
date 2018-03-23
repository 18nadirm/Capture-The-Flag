package server;

import gameObjects.Map;
import gameObjects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Random;
import util.AnimationPanel; 
import util.GenericComm;

public class SS_GameEngine extends AnimationPanel {

    Map map;
    Random randy = new Random();
    
    //Constructor

    public SS_GameEngine() {
        super("CaptureTheFlag - SERVER", 1200, 700);
        //initialize variables
        map = new Map();
    }
    
    /**
     * When a new player joins on the server, this method is called
     * to add the player to the game.  
     * @param p 
     */
    public void addNewPlayer(Player p)
    {        
        map.playersMap.put(p.getID(), p);
    }

    /**
     * This method receives a message from a client and updates the game
     * accordingly.
     *
     * @param theInput
     * @param myConnNumber
     */
    public void processInput(String theInput, int myConnNumber) 
    {
        debugMsg("@ processInput, theInput:" + theInput + " connNum: " + myConnNumber);
        if(theInput.startsWith("PLA"))
        {
            //Update this player's info
            Player p = new Player(theInput);
            if(map.playersMap.containsKey(p.getID()))
            {
                Player q = map.playersMap.get(p.getID());
                q.unpackLite(p);
                map.playersMap.put(p.getID(), q);
            }
        }
        else if(theInput.startsWith("PRO"))
        {
            //Do something similar for Projectiles
        }
        else if(theInput.equalsIgnoreCase("terminated"))
        {   //A player has left the game...
            Collection<Player> players = map.getPlayerList();
            for(Player p : players){
                if(p.getID() == myConnNumber){
                    map.playersMap.remove(myConnNumber);
                    break;
                }
            }
        }
    }

    /**
     * This is the method that returns all of the game data to a client who
     * requests it.
     *
     * @param connectionNum
     * @return
     */
    public String getStatusUpdate(int connectionNum) {
        String gameData = map.pack();

        return gameData;
    }
    /*
     public int assignTeam()
     public void assignStartingLocation()
    
     */

    protected Graphics renderFrame(Graphics g) {
        //Draw the entire display.  

        map.updateGameObjects();
        
        //map.checkForInteractions(); - not written yet!
        
//        //Draw a circle that follows the mouse. (Temporary!)
//        g.setColor(Color.BLACK);
//        g.fillOval(mouseX - 10, mouseY - 10, 20, 20);

        map.drawFullDisplay(g, 50, 50);
        
        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.BLACK);
        g.drawString("SERVER WINDOW", 10, 12);
        g.drawString("frame=" + frameNumber, 200, 12);

        return g;
    }//--end of renderFrame method--

    private void debugMsg(String msg)
    {
        if(GenericComm.debugMode)
            System.out.println(msg);
    }
}
