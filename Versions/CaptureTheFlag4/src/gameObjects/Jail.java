package gameObjects;

import javafx.util.Pair;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Jail implements GameObject
{
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    private ArrayList< Pair< Player, Double > > blue;
    private ArrayList< Pair< Player, Double > > red;

    //instance variables ------------------------------------
    private int x, y;
    
    //constructor(s) ----------------------------------------
    public Jail( int xx, int yy )
    {
        x = xx;
        y = yy;
        blue = new ArrayList< Pair< Player, Double > >();
        red = new ArrayList< Pair< Player, Double > >();
    }
    //accessors and modifiers (get and set methods) ---------
    public int getX() { return x; }
    public int getY() { return y; }
    
    //implemented methods -----------------------------------
    public Rectangle getBounds() {
        return new Rectangle( x, y, WIDTH, HEIGHT );
    }

    /**
     * add players who should be in jail into the jail
     */
    public void add() {
        // retrieve current map
        Map map = new Map();

        // save current time
        double time = currentTime();

        // loop thru all players
        for( int i = 0; i < map.playersMap.size(); ++i ) {
            // player by index
            Player player = map.getPlayerByID( i );

            // is player in jail?
            if( player.inJail ) {
                // add to appropriate jail
                if( player.getColor() == Color.BLUE )
                    blue.add( new Pair< Player, Double >( player, time ) );
                else if( player.getColor() == Color.RED )
                    red.add( new Pair< Player, Double >( player, time ) );
            }
        }
    }

    /**
     * calculated server current time via System.nanoTime() converted to seconds ( most accurate timer ), performance heavy. don't call often
     * @return current server time in seconds
     */
    public double currentTime() {
        return ( double ) System.nanoTime() / 1000000000.d;
    }

    /**
     * loop through the jail array and free people who have been there longer than 15 seconds
     */
    public void free() {
        // current server time
        double time = currentTime();

        // loop through the blue array
        for( int i = 0; i < blue.size(); ++i ){
            // does time exceed 15 seconds?
            if( ( time - blue.get( i ).getValue() ) > 15.f )
                blue.remove( i );
        }

        // loop through the red array
        for( int i = 0; i < red.size(); ++i ){
            // does time exceed 15 seconds?
            if( ( time - red.get( i ).getValue() ) > 15.f )
                red.remove( i );
        }
    }

    /**
     * pack jail networking data
     * @return encoded network data
     */
    public String pack()
    {
        String data = "JAIL: ";

        // loop through the blue array
        for( int i = 0; i < blue.size(); ++i ) {
            // criminal = index of player + his time in jail
            // TODO; time may be too long as it is the highest precision double... maybe convert to int for network transfer?
            String criminal =  blue.get( i ).getKey().getID() + "=" + blue.get( i ).getValue() + ",";
            data += criminal;
        }

        // loop through the red array
        for( int i = 0; i < red.size(); ++i ) {
            // criminal = index of player + his time in jail
            // TODO; time may be too long as it is the highest precision double... maybe convert to int for network transfer?
            String criminal =  red.get( i ).getKey().getID() + "=" + red.get( i ).getValue() + ",";
            data += criminal;
        }

        return data;
    }

    /**
     * unpack jail networking data
     */
    public void unpack( String data )
    {
        // split data into differenet criminals
        String[] parts = data.split( "," );

        // loop thru all parts of the data
        for( int i = 0; i < parts.length; ++i ) {
            // current criminal
            String criminal = parts[ i ];
        }
    }

    /**
     * Draw the GameObject onto the Graphics page
     * with the appropriate offset and scale factor.  
     * @param g - the Graphics object to be drawn onto.
     * @param scaleDownFactor - the amount to divide your x,y by before drawing.
     * @param offsetX - the amount to add to your x coordinate before drawing. 
     * @param offsetY - the amount to add to your y coordinate before drawing.
     */
    public void draw( Graphics g, int scaleDownFactor, int offsetX, int offsetY )
    {
        g.setColor( Color.GRAY );
        g.fillRect( x/scaleDownFactor + offsetX,
                    y/scaleDownFactor + offsetY,
                    WIDTH/scaleDownFactor, HEIGHT/scaleDownFactor );
    }
    
    //other methods -----------------------------------------
    
}
