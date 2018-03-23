package server;

import gameObjects.Flag;
import gameObjects.Map;
import gameObjects.Player;
import gameObjects.PowerUp;
import gameObjects.Projectile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import util.AnimationPanel;

public class SS_GameEngine extends AnimationPanel {

    Map map;
//    ArrayList<Player> characters = new ArrayList<Player>();
//    ArrayList<Flag> flag;
//    ArrayList<PowerUp> powerups;
//    ArrayList<Projectile> projectile;
    Random randy = new Random();

    //Constructor

    public SS_GameEngine() {
        super("CaptureTheFlag - SERVER", 1200, 700);
        //initialize variables
        map = new Map();
    }

    /**
     * This method receives a message from a client and updates the game
     * accordingly.
     *
     * @param theInput
     * @param myConnNumber
     */
    public void processInput(String theInput, int myConnNumber) {
        System.out.println("@ processInput, theInput:" + theInput + " connNum: " + myConnNumber);
        if (theInput.equalsIgnoreCase("player")) {
            map.players.add(new Player(myConnNumber));
        } else if(theInput.equalsIgnoreCase("terminated")){
            for(int i = 0; i < map.players.size(); i++){
                if(map.players.get(i).getID() == myConnNumber){
                    map.players.remove(map.players.get(i));
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
    public ArrayList<String> getStatusUpdate(int connectionNum) {
        ArrayList<String> gameData = new ArrayList<String>();

        gameData.add("Hello."); //Just for testing.

        return gameData;
    }
    /*
     public int assignTeam()
     public void assignStartingLocation()
    
     */

    protected Graphics renderFrame(Graphics g) {
        //Draw the entire display.  

        //Draw a circle that follows the mouse.
        g.setColor(Color.BLACK);
        g.fillOval(mouseX - 10, mouseY - 10, 20, 20);

        map.drawFullDisplay(g, 50, 50);
        
        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.BLACK);
        g.drawString("SERVER WINDOW", 10, 12);
        g.drawString("frame=" + frameNumber, 200, 12);

//        if (map.players != null) {
//            g.drawString("size " + map.players.size(), 10, 30);
//            for (Player k : map.players) {
//                g.setColor(k.getColor());
//                Rectangle rect = k.getBounds();
//                g.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
//                g.drawString("" + k.getID(), (int)rect.getX(), (int)rect.getY());
//            }
//        }
//        g.drawString("test ", 10, 20);
        return g;
    }//--end of renderFrame method--

}
