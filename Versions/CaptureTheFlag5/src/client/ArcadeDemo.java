package client;

/**
 * Class ArcadeDemo
 * This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 * 
 * Adapted from the AppletAE demo from years past. 
 */


import gameObjects.Map;
import gameObjects.Projectile;
import gameObjects.Store;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.applet.AudioClip;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import util.AnimationPanel;
import util.GenericComm;


public class ArcadeDemo extends AnimationPanel implements ActionListener
{

    //Constants
    //-------------------------------------------------------

    //Instance Variables
    //-------------------------------------------------------
    private int myID;
    private Map map;
    private String mode = "store"; //This mode can be "game" or "store" to determine which screen is displayed
    private Store myStore = new Store(); //Each player has a store which stores their stats and allows them to chnage their stats
    
    //Motion/keyboard variables
    //-------------------------------------------------------
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean upPressed = false;
    boolean downPressed = false;
    
    //Network variables
    //-------------------------------------------------------
    private Timer timer;
    private final int DELAY = 100; //delay in mSec
    private GenericComm comm;
    String response = "No response.";
    
    //Constructor
    //-------------------------------------------------------
    public ArcadeDemo()
    {   //Enter the name and width and height.  
        super("CaptureTheFlag", 1200, 740);
        map = new Map();
        comm = new GenericComm();
        comm.setDebugValues("C_COMM",0); 
        //This is how a new instance gets it's id...
        myID = comm.getSocket().getLocalPort();
        initTimer();
    }
    public void setMode(String s) {mode = s;}

       
    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected Graphics renderFrame(Graphics g) 
    {
        if(mode.equals("store")) {
            myStore.drawStore(g, map.getPlayerByID(myID), 0, 70);
        }
        else {
            
            processMotionRequests();
            map.updateGameObjects();
            //Draw main display
            map.drawMainDisplay(g, myID, 5, 20);
            //Draw Radar display
            map.drawRadarDisplay(g, myID, 740, 20);
            //Draw character status display
            map.getPlayerByID(myID).drawPlayerDisplayWindow(g, 740, 300);
        }
            
        //General Text (Draw this last to make sure it's on top.)
        g.setColor(Color.BLACK);
        g.drawString("ArcadeEngine 2008", 10, 12);
        g.drawString("frame="+frameNumber,200,12);
        g.drawString("You are" + comm.getSocket().getLocalPort(), 10, 30);
        g.drawString(response,300,12);
        
        return g;
    }//--end of renderFrame method--
    
    
    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e)  
    { 
        if(e.getX() > 800) //A temporary way to close the store.
            mode = "game";
        else if(mode.equals("store")) 
            myStore.buttonPressed(mouseX, mouseY, map.getPlayerByID(myID), this);
    }
    
    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e) 
    {
        char c = e.getKeyChar(); 
        if(c == ' ')
        {
            Projectile proj = map.getPlayerByID(myID).fireWeapon();
            map.lasers.add(proj);
            //Move the projectile so it is off the player.  
            while(map.getPlayerByID(myID).getBounds().intersects(proj.getBounds()))
                proj.animate();
            
            comm.sendMessage(proj.pack());
        }
    }
    
    public void keyPressed(KeyEvent e)
    {
        int v = e.getKeyCode();
        if((v == KeyEvent.VK_A)||(v == KeyEvent.VK_LEFT))
            leftPressed = true;
        if((v == KeyEvent.VK_D)||(v == KeyEvent.VK_RIGHT))
            rightPressed = true;    
        if((v == KeyEvent.VK_W)||(v == KeyEvent.VK_UP))
            upPressed = true;
        if((v == KeyEvent.VK_D)||(v == KeyEvent.VK_DOWN))
            downPressed = true;    
        
    }

    public void keyReleased(KeyEvent e)
    {
        int v = e.getKeyCode();
        if((v == KeyEvent.VK_A)||(v == KeyEvent.VK_LEFT))
            leftPressed = false;
        if((v == KeyEvent.VK_D)||(v == KeyEvent.VK_RIGHT))
            rightPressed = false;    
        if((v == KeyEvent.VK_W)||(v == KeyEvent.VK_UP))
            upPressed = false;
        if((v == KeyEvent.VK_D)||(v == KeyEvent.VK_DOWN))
            downPressed = false;    
        
    }
    
    public void processMotionRequests()
    {
        if(leftPressed)
            map.getPlayerByID(myID).rotateLeft();
        if(rightPressed)
            map.getPlayerByID(myID).rotateRight();
        if(upPressed)
            map.getPlayerByID(myID).moveForward();
        if(downPressed)
            map.getPlayerByID(myID).moveBackward();
        if(!upPressed && !downPressed)
            map.getPlayerByID(myID).stopMovement();
    }
    //-------------------------------------------------------
    //Initialize Graphics
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Image section... 
 *  To add a new image to the program, do three things.
 *  1.  Make a declaration of the Image by name ...  Image imagename;
 *  2.  Actually make the image and store it in the same directory as the code.
 *  3.  Add a line into the initGraphics() function to load the file. 
//-----------------------------------------------------------------------*/
    Image ballImage;        
    Image starImage;
    
    public void initGraphics() 
    {      
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        ballImage = toolkit.getImage("ball.gif");
        starImage = toolkit.getImage("star.gif");
    } //--end of initGraphics()--
    
    //-------------------------------------------------------
    //Initialize Sounds
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Music section... 
 *  To add music clips to the program, do four things.
 *  1.  Make a declaration of the AudioClip by name ...  AudioClip clipname;
 *  2.  Actually make/get the .wav file and store it in the same directory as the code.
 *  3.  Add a line into the initMusic() function to load the clip. 
 *  4.  Use the play(), stop() and loop() functions as needed in your code.
//-----------------------------------------------------------------------*/
    AudioClip themeMusic;
    AudioClip bellSound;
    
    public void initMusic() 
    {
//        themeMusic = loadClip("under.wav");
//        bellSound = loadClip("ding.wav");
//                if(themeMusic != null)
//                    themeMusic.loop(); //This would make it play!
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //*********************************************************
    // NETWORK STUFF
    //*********************************************************
    private void initTimer()
    {   //Set up a timer that calls this object's action handler.
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(DELAY);
        timer.setCoalesce(true);
        timer.start();
    }
    public void startTimer()
    {
        comm.sendMessage("Starting Timer");
        timer.start();
    }
    public void stopTimer()
    {
        comm.sendMessage("Stopping Timer");
        timer.stop();  
    }
    public void sendMessage(String choice)
    {
        comm.sendMessage(choice); 
        do {
            response = comm.getMessage();
        }  while (comm.messageWaiting());
    }
    public void actionPerformed(ActionEvent e) 
    {   //--GET UPDATE FROM SERVER--
        //Called whenever timer goes off (every DELAY msec.) 
        String message = map.getPlayerByID(myID).pack();
        comm.sendMessage(message);
        
        response = comm.getMostRecentMessage();
        if(response == null)
        { response = "No response."; }
        else if(response.startsWith("MAP"))
        {
            //When unpacking a map, it returns the player unpacking it.  
            map.unpack(response, myID);
        }
        else
        {
            response = "UNRECOGNIZED: "+response;
            debugMsg(response);
        }
    }   //end of actionPerformed()

    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }

}//--end of ArcadeDemo class--

