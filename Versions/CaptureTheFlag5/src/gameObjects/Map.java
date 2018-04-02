package gameObjects;
//TODO: Fix makeMap to add Barriers and the Jails (the consistent part of the Map)
//TODO: Fix the main Client display window to show objects in range (Flag, Projectiles, etc...)
//TODO: Fix drawMainDisplay to shade the sides and show central line
//TODO: Add stealth to Radar display
//TODO: Add other objects to radar display
//TODO: Interactions between objects!!!
//TODO: call animate methods of objects (instead of doing the animation here)
//TODO: 
//TODO: 


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import util.GenericComm;

public class Map 
{
    Random randy = new Random();

    public ArrayList<Barrier> barriers;
    public ArrayList<PowerUp> powerups;
    public ArrayList<Projectile> lasers;
    public HashMap<Integer, Player> playersMap;    
    
    private Flag redFlag; 
    private Flag blueFlag;
    private Jail redJail;
    private Jail blueJail;
    
    //NEEDS A CONSTRUCTOR TO INITIALIZE VARIABLES!!!
    public Map()
    {
        barriers = new ArrayList<Barrier>();
        playersMap = new HashMap<Integer, Player>();
        powerups = new ArrayList<PowerUp>();
        lasers = new ArrayList<Projectile>();
        redFlag = new Flag(300,300,Color.RED);
        blueFlag = new Flag(2900,1500,Color.BLUE);
        redJail = new Jail(100,1000);
        blueJail = new Jail(2900,1000);
        
        makeMap();
    }
    public void makeMap()
    {
        //The full map is 3000x2000
        //Add in the barriers
        //Eventually there will be more Barriers
        barriers.add(new Barrier(400,50,50,700));
    }
    public void addPowerUps()
    {
        for(int z=0; z<10; z++)
            powerups.add(new PowerUp(randy.nextInt(4)));        
    }
    
    public void drawMainDisplay(Graphics g, int playerID, int xOffset, int yOffset)
    {
        //This will be 700x700
        Player thePlayer = this.getPlayerByID(playerID);
        //Draw a box around the display
        g.drawRect(xOffset, yOffset, 700, 700);
               
        //Calculate offset to center display on player.
        int xdiff=350-thePlayer.x;
        int ydiff=350-thePlayer.y;
        //The actual offset to display on the screen.
        int xOff = xdiff+xOffset; 
        int yOff = ydiff+yOffset;
        
        //Calculate the shading of the sides... 
        Rectangle myView = new Rectangle(thePlayer.x-350, thePlayer.y-350, 700,700);
        Rectangle redSide = new Rectangle(0,0,1500,2000);
        Rectangle blueSide = new Rectangle(1500,0,1500,2000);
        Rectangle drawBlue = myView.intersection(blueSide);
        Rectangle drawRed = myView.intersection(redSide);
        Color blue = new Color(0, 0, 255, 100);
        Color red = new Color(255, 0, 0, 100);

        //Shade the side(s) of the playing field
        //======================================
        if(!drawRed.isEmpty())
        {
            g.setColor(red);
            g.fillRect(drawRed.x+xOff, drawRed.y+yOff, 
                drawRed.width, drawRed.height);
        }
        if(!drawBlue.isEmpty())
        {
            g.setColor(blue);
            g.fillRect(drawBlue.x+xOff, drawBlue.y+yOff,
                drawBlue.width, drawBlue.height);
        }
        
        //Draw myself to the screen
        thePlayer.draw(g,1,xOff,yOff);
        
        //Draw the objects.
        for (Barrier i: barriers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        
        for (Player i: this.getPlayerList())
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        for (PowerUp i: powerups)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }
        for (Projectile i: lasers)
        {
            if (i.x+xdiff>=0 && i.y>=0 && i.x+xdiff<=700 && i.y+ydiff<=700)
                i.draw(g, 1, xOff, yOff);
        }

        //These aren't working yet...
        if (redFlag.x-xdiff>=0 && redFlag.x-xdiff<=700 && redFlag.y-ydiff>=0 && redFlag.y-ydiff<=700)
            redFlag.draw(g,1,xOff,yOff);
        if (blueFlag.x-xdiff>=0 && blueFlag.x-xdiff<=700 && blueFlag.y-ydiff>=0 && blueFlag.y-ydiff<=700)
            blueFlag.draw(g,1,xOff,yOff);
        if (redJail.getX()-xdiff>=0 && redJail.getX()-xdiff<=700 && redJail.getY()-ydiff>=0 && redJail.getY()-ydiff<=700)
            redJail.draw(g,1,xOff,yOff);
        if (blueJail.getX()-xdiff>=0 && blueJail.getX()-xdiff<=700 && blueJail.getY()-ydiff>=0 && blueJail.getY()-ydiff<=700)
            blueJail.draw(g,1,xOff,yOff);       
    }
    
    
    
    public void drawRadarDisplay(Graphics g, int playerID, int xOffset, int yOffset)
    {        
        g.drawRect(xOffset, yOffset, 300, 200);
        Color blue = new Color(0, 0, 255, 100);
        g.setColor(blue);
        g.fillRect(xOffset,yOffset,150,200);  //maybe use fill and a lighter shade?
        Color red = new Color(255, 0, 0, 100);
        g.setColor(red);
        g.fillRect(xOffset+150, yOffset, 150, 200);
        g.setColor(Color.BLACK);
        for (Barrier i: barriers)
        {
            i.draw(g, 10, xOffset, yOffset);
        }
        for (Player i: playersMap.values())
        {
            if (!i.hasStealth)
                i.draw(g, 10, xOffset, yOffset);
        }
        redFlag.draw(g,10,xOffset, yOffset);
        blueFlag.draw(g,10,xOffset, yOffset);
        redJail.draw(g,10,xOffset, yOffset);
        blueJail.draw(g,10,xOffset, yOffset);
        
        //You can see all tanks, unless stealthy (Yes, just tanks!)
        //Whatever your teammates can see, you can see (SAVE THIS FOR LATER - OR ELIMINATE)
        //Idea: make red dots appear where there are projectiles being shot
        //This will be 300x200
        
    }
    /**
     * This is for the server side!!!
     * @param g
     * @param xOffset
     * @param yOffset 
     */
    public void drawServerFullDisplay(Graphics g, int xOffset, int yOffset)
    {
        //This will be for the server-side view and be 600x400
        g.drawRect(xOffset, yOffset, 750, 500);
        redFlag.draw(g, 4, xOffset, yOffset);
        blueFlag.draw(g, 4, xOffset, yOffset);
        redJail.draw(g, 4, xOffset, yOffset);
        blueJail.draw(g, 4, xOffset, yOffset);
        for (Player i: playersMap.values())
            i.draw(g, 4, xOffset, yOffset);
        for (Projectile p : lasers)
            p.draw(g, 4, xOffset, yOffset);
        for (PowerUp p : powerups)
            p.draw(g, 4, xOffset, yOffset);
        //list the names on a scoreboard
        int count=0;
        for (Player p: playersMap.values())
        {
            g.setColor(p.getColor());
            g.drawString(p.getName(),xOffset+800, yOffset+20+20*count);
            count++;
        }
    }
    
    /**
     * This method updates (animates) all of the GameObjects
     * Players, Projectiles, etc based on their known status.  
     * This will need to occur both on the Client and Server sides.
     */
    public void updateGameObjects()
    {
        for(Player p : this.getPlayerList())
        {
            if(!p.inJail && p.canMove()) 
            {
                int oldX = p.x;
                int oldY = p.y;
                p.animate();
//                p.x += (int)(p.getVelocity()*Math.cos(p.getDirectionRadians())); 
//                p.y += (int)(p.getVelocity()*Math.sin(p.getDirectionRadians())); 
                for(Barrier barrier : barriers) {
                    if(p.getBounds().intersects(barrier.getBounds())) { //if i'm trying to move onto a barier
                        //move back, you can't do that, but only move back once
                        p.x = oldX;
                        p.y = oldY; 
                
                    }
                }
            }
        }
        for(Projectile p : lasers)
        {
            p.x += (int)(p.getXVel());  //if there are separate x and y velocities, you do not need to multiply by the direction
            p.y += (int)(p.getYVel()); 
        }
        
        for(int i=0; i<lasers.size();i++)
        {
            Projectile projectile=lasers.get(i);
            for (Barrier b: barriers)
            {
                if (b.getBounds().contains(projectile.getBounds()))
                {
                    //Removes laser when it hits a barrier
                    lasers.remove(i);
                    i-=1;
                }
            }
        }
    }
     
    public void checkInteractions()
    {
        for(Player p : this.getPlayerList())
        {
            if(!p.inJail && p.canMove()) 
            {   //if the player is on the flag, tell it to pick up the flag
                if(p.getBounds().intersects(redFlag.getBounds()) && !p.teamColor.equals(redFlag.myColor)) 
                    p.pickUpFlag(redFlag);
                if(p.getBounds().intersects(blueFlag.getBounds()) && !p.teamColor.equals(blueFlag.myColor)) 
                    p.pickUpFlag(blueFlag);

                //Lasers hitting players==============
                for(int i=0; i<lasers.size();i++)
                {
                    Projectile projectile=lasers.get(i);
                    if (p.getBounds().intersects(projectile.getBounds()))
                    {
                        p.setHealth(p.getHealth()-projectile.getDamage());
                        lasers.remove(i);
                        i-=1;
                    }                            
                }
                
                //if the player is bumping into another player, find who is in home territory, the other reacts to the tag
                for(Player otherPlayer : this.getPlayerList()) 
                {
                    //if this isn't me, if they are on the opposing team, and if i'm on my side
                    if(p.getID() != otherPlayer.getID() && p.getColor() != otherPlayer.getColor() && p.isOnOwnSide()) 
                    {
                        if(p.getBounds().intersects(otherPlayer.getBounds())) 
                        {
                            otherPlayer.reactToTag();
                        }                        
                    }
                }
            }
            //Player and PowerUp interactions======================
            for (int i=0; i<powerups.size();i++)
            {
                PowerUp powerup = powerups.get(i);
                //if I am touching a powerup, give me its benefit
                if (p.getBounds().intersects(powerup.getBounds()))
                {
                    //sets stealth
                    if (powerups.get(i).stealthBoost())
                        p.hasStealth=true;
                    //sets health
                    if (powerups.get(i).healthBoost())
                        p.health=p.maxHealth;
                    if (powerups.get(i).fireSpeedBoost())
                        p.launcher.projectileSpeed+=2;
                    //sets speed
                    if (powerups.get(i).staminaBoost())
                    {
                        p.maxSpeed+=2;
                        p.setSpeed(p.getSpeed()+2);
                    }
                    //removes the powerup from the arena and removes it from the list
                    //Currently there is no end time to the powerup
                    powerups.remove(powerups.get(i));
                    i-=1;
                }                
            }
            //Player to barrier collisions
            for(Barrier b : barriers) {
                //Figure out which side the player is hitting the barrier from
                if(p.getBounds().intersects(b.getBounds())) {
                    //Defining position variables
                    int xCenter = b.x + (b.width/2);
                    int yCenter = b.y + (b.height/2);
                    int xDiff = p.x - xCenter;
                    int yDiff = p.y - yCenter;
                    
                    //Left side collision
                    if(p.x < b.x  &&  (b.x-p.x) > (b.y-p.y)  && (b.x-p.x) > (p.y-(b.y+b.height))) {
                        //Facing up
                        if(Math.sin(p.direction) < 0) {
                            p.direction = 270; //Facing up
                            p.velocity *= -0.9*Math.sin(p.direction);
                        }
                        //Facing down
                        if(Math.sin(p.direction) > 0) {
                            p.direction = 90; //Facing down
                            p.velocity *= 0.9*Math.sin(p.direction);
                        }
                        //Knock left
                        p.x -= 5;
                    } 
                    //Right side collision
                    else if(xDiff > b.width/2  &&  p.x - (b.x+b.width) > b.y-p.y  &&   p.x - (b.x+b.width) > p.y - (b.y+b.height)) {
                        //Facing up
                        if(Math.sin(p.direction) < 0) {
                            p.direction = 270; //Facing up
                            p.velocity *= -0.9*Math.sin(p.direction);
                        }
                        //Facing down
                        if(Math.sin(p.direction) > 0) {
                            p.direction = 90; //Facing down
                            p.velocity *= 0.9*Math.sin(p.direction);
                        }
                        //Knock right
                        p.x -= 5;
                    }
                    //Top collision
                    else if(p.y < b.y) {
                        //Facing right
                        if(Math.cos(p.direction) > 0) {
                            p.direction = 0; //Facing right
                            p.velocity *= 0.9*Math.cos(p.direction);
                        }
                        //Facing left
                        if(Math.cos(p.direction) < 0) {
                            p.direction = 180; //Facing left
                            p.velocity *= -0.9*Math.cos(p.direction);
                        }
                        //Knock up
                        p.y -= 5;
                    }
                    //Bottom collision
                    else if(b.y + b.height < p.y) {
                        //Facing right
                        if(Math.cos(p.direction) > 0) {
                            p.direction = 0; //Facing right
                            p.velocity *= 0.9*Math.cos(p.direction);
                        }
                        //Facing left
                        if(Math.cos(p.direction) < 0) {
                            p.direction = 180; //Facing left
                            p.velocity *= -0.9*Math.cos(p.direction);
                        }
                        //Knock down
                        p.y += 5;
                    }
                    //Inside barrier
                    else {
                        if(xDiff < 0) p.x -= 25;
                        if(xDiff > 0) p.x += 25;
                        if(yDiff < 0) p.y -= 25;
                        if(yDiff > 0) p.y += 25;
                    }
                }
            }            
        } //--end of looping through Players
        
            /**
     * Some unfinished code for determining if a flag has reached th opposite
     * side of the map
     */
//    private boolean hasGameBeenWon() {
//        if (redFlag.getX() > 1500) {
//            blueWin = true;
//            return true;
//        }
//        if (blueFlag.getX() < 1500) {
//            redWin = true;
//            return true;
//        }
//
//        return false;
//    }

    }//-end of checkInteractions()----------
    
    
    public Collection<Player> getPlayerList()
    {
        return playersMap.values();
    }
    
    public Player getPlayerByID(int id)
    {
        if(playersMap.containsKey(id))
            return playersMap.get(id);
        else
            return new Player(id,0,0,Color.YELLOW,"ERROR");
    }
    
    //For network communication ==================================
    public String pack()
    {
        String result = "MAP";
        for(Player p : this.getPlayerList())
        {
            result += ","+p.pack();
        }
        for(Projectile p : this.lasers)
        {
            result += ","+p.pack();
        }
        for(PowerUp p : this.powerups)
        {
            result += ","+p.pack();
        }
        result += ","+redFlag.pack();
        result += ","+blueFlag.pack();
        
        return result;
    }
    /**
     * This method unpacks all of a game's data and returns the 
     * packed String for the particular player unpacking the Map.
     * @param s
     * @param playerNum
     * @return 
     */
    public String unpack(String full, int playerNum)
    {
        debugMsg("For: " + playerNum + "  Map unpacking: "+full);
        String playerString = "NotFound";
        
        //clear the lists to refill them. 
        Player myself = playersMap.get(playerNum);
        playersMap.clear();
        powerups.clear();
        lasers.clear();
        
        //Unpack the info (separate by commas)
        String[] parts = full.split(",");
        for(String s : parts)
        {
            if(s.startsWith("PLA"))
            {
                Player p = new Player(s); //unpack and construct!
                int num = p.id;
                if(p.id == playerNum && myself != null)
                {
                    //keep my old direction and velocity
                    p.direction = myself.direction;
                    p.velocity = myself.velocity;
                    playerString = s; //probably don't need this anymoer
                }
                playersMap.put(num, p);
                    
            }
            else if(s.startsWith("FLA"))
            {
            }
            else if(s.startsWith("PRO"))
            {
                Projectile p = new Projectile(s);
                lasers.add(p);
            }
            else if(s.startsWith("POW"))
            {
                PowerUp p = new PowerUp(s);
                powerups.add(p);
            }
        }

        return playerString; 
    } //end of unpack method.  
    
    private void debugMsg(String m)
    {
        if(GenericComm.debugMode)
            System.out.println(m);
    }
    
}
