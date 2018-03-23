package server;

import gameObjects.Player;
import util.GenericComm;
import java.net.*;
import java.util.ArrayList;

//=========================================================================
//THIS IS THE SERVER-SIDE 'LISTENER' THREAD...
// A seperate instance of this class is created for each user's connection.
// Static methods in this class can run and organize the game.  
//=========================================================================

public class SS_Thread extends Thread 
{
    //--static class variables--
    private static int numConnections = 0;  //Counts the total number of connections
    private SS_GameEngine gameEngine;
    //--private instance variables--
    private int myNumber = 0; //Which connection number am I?
    private GenericComm comm = null; //Generic communication object
    Socket mine;
    public SS_Thread(Socket socket, SS_GameEngine theEngine) //Constructor
    {        
        super("SS_Thread");
        mine = socket;
        gameEngine = theEngine;
        //***There is a new connection, and I'm it!
        numConnections++;
        myNumber = numConnections;
        //***Initialize the GenericComm object.  
        comm = new GenericComm(socket);
        comm.setDebugValues("SS_Thread", myNumber);
    }

    //This method is automatically called once the Thread is running.  
    //---------------------------------------------------------------
    public void run() 
    {
        String inputLine;
        Player myself = new Player(mine.getPort());
        gameEngine.addNewPlayer(myself);
        comm.sendMessage("WELCOME" + mine.getPort() ); //Send a welcome. 
        System.out.println("SS: Welcome" + mine.getPort() );
        //This loop constantly waits for input from Client and responds...
        //----------------------------------------------------------------
        while ((inputLine = comm.getMessage()) != null){ 
                
            if(inputLine.equals("UPDATE"))
            {
                String gameData = gameEngine.getStatusUpdate(myNumber);
                comm.sendMessage(gameData);
            }
            else
            {
                gameEngine.processInput(inputLine, mine.getPort());
                //Send the game data back anyway!!! 
                String gameData = gameEngine.getStatusUpdate(myNumber);
                comm.sendMessage(gameData);
            }

        }
        System.out.println("END OF WHILE!!! - SS_Thread");
        gameEngine.processInput("terminated", mine.getPort());
        //Clean things up by closing streams and sockets.
        //-----------------------------------------------
        comm.closeNicely();
    } //--end of run() method--
    

} //--end of SS_Thread class--

