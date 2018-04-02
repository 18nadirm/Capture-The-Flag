package server;
//TODO: Improve Assign fair teams and starting locations
//TODO: processInput for PowerUps
//TODO: Fix glitchyness
//TODO: interactions between objects
//TODO: determine if the game is won
//TODO: release people from jail after a certain amount of time

import gameObjects.Map;
import gameObjects.Player;
import gameObjects.Projectile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
        map.addPowerUps();
    }
    
    /**
     * When a new player joins on the server, this method is called
     * to add the player to the game.  
     * @param p 
     */
    public void addNewPlayer(int id)
    {       
        //Choose a team for this Player
        Color teamColor = assignTeam();
        //Choose a location for this Player
        Point startLoc = assignStartingLocation(teamColor);

        Player p = new Player(id,startLoc.x,startLoc.y,teamColor,"noname");
        map.playersMap.put(id, p);
    }
    
    public Color assignTeam()
    {
        Color teamColor = Color.RED;
        if(numPlayersOnTeam(Color.RED)>numPlayersOnTeam(Color.BLUE))
        {
            teamColor=Color.BLUE;
        }
        
        return teamColor;
    }
    // method to get the number of players on team c
    public int numPlayersOnTeam(Color c)
    {
        int numOfPlayers=0;
        for(Player p : map.playersMap.values())
        {          
            if(p.getColor().equals(c))
            {
                numOfPlayers++;
            }
        }
        return numOfPlayers;
    }
    public Point assignStartingLocation(Color teamColor)
    {
        int y = randy.nextInt(1000)+500; //The middle half vertically.
        int x = randy.nextInt(500)+100; //Towards the back of the zone. 
        if(teamColor == Color.BLUE)
            x = 3000-x; //flip sides for the blue team
        return new Point(x,y);
    }
    
     
    /**
     * This method receives a message from a client and updates the game
     * accordingly.
     *
     * @param theInput
     * @param myConnNumber
     */
    public String processInput(String theInput, int myConnNumber) 
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
        debugMsg("@ processInput, theInput:" + theInput + " connNum: " + myConnNumber);
            //Do something similar for Projectiles
            Projectile p = new Projectile(theInput);
            map.lasers.add(p);
        }
        else if(theInput.startsWith("NAM"))
        {
        debugMsg("@ processInput(NAME), theInput:" + theInput + " connNum: " + myConnNumber);
            String name = theInput.substring(4);
            map.getPlayerByID(myConnNumber).setName(name);
        }
        else if(theInput.startsWith("STO"))
        {
        debugMsg("@ processInput(STORE), theInput:" + theInput + " connNum: " + myConnNumber);
            //Update this player's info
            Player p = new Player(theInput);
            if(map.playersMap.containsKey(p.getID()))
            {
                map.playersMap.put(p.getID(), p);
            }
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
        else
        {
            System.out.println("Dropped msg: "+theInput);
        }
        return map.pack();
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

    protected Graphics renderFrame(Graphics g) {
        //Draw the entire display.  

        map.updateGameObjects();
        
        map.checkInteractions(); //only happens on Server-side
        
        map.drawServerFullDisplay(g, 50, 50);
        
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
