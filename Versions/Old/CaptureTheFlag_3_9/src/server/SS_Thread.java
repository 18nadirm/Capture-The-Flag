package server;

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
//    private static ArrayList<String> messages = new ArrayList<String>();
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
//        messages.add("New member added to chat: (#"+myNumber+")"); //adds an element to the static messages array
        //***Initialize the GenericComm object.  
        comm = new GenericComm(socket);
        comm.setDebugValues("SS_Thread", myNumber);
    }

    //This method is automatically called once the Thread is running.  
    //---------------------------------------------------------------
    public void run() 
    {
        String inputLine, outputLine;
        int myStart = 0;
        comm.sendMessage("SS: Hello " + mine.toString() +", you are connection # " +myNumber); //Send a welcome. 
        System.out.println("SS: Hello " + mine.getPort() +", you are connection # " +myNumber);
        gameEngine.processInput("player", mine.getPort());
        //This loop constantly waits for input from Client and responds...
        //----------------------------------------------------------------
        while ((inputLine = comm.getMessage()) != null){ 
                
            if(inputLine.equals("UPDATE"))
            {
                ArrayList<String> gameData = gameEngine.getStatusUpdate(numConnections);
                for(String s : gameData)
                    comm.sendMessage(s);
                //comm.sendMessage("DONE");  
            }
            else
            {
                gameEngine.processInput(inputLine, mine.getPort());                                       
            }

        }
        System.out.println("END OF WHILE!!! - SS_Thread");
        gameEngine.processInput("terminated", mine.getPort());
        //Clean things up by closing streams and sockets.
        //-----------------------------------------------
        comm.closeNicely();
    } //--end of run() method--
    

} //--end of SS_Thread class--

