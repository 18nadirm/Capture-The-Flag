/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObjects;

import java.awt.Button;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author 20kanedas
 */
public class Store 
{

    
//Draws the store display. Store will have buttons to increase/decrease different stats.
    public static void drawStore(Graphics g, Player myself, int xOffset, int yOffset) {
        //Changing font to bold
        Font myFont = new Font("Arial", Font.BOLD, 24);
        g.setFont(myFont);
        //Displaying the number of remaining skill tokens
        g.drawString(myself.remainingSkillTokens + " skill tokens remaining.", 10 + xOffset, 30 + yOffset);
        g.drawString("Health", 10 + xOffset, 60 + yOffset);
        //Buttons to increse or decrease stats
        Button decreaseHealth = new Button("-");
        decreaseHealth.setLocation(xOffset + 70, yOffset + 40);
        decreaseHealth.setVisible(true);
        g.setFont(new Font("Arial",Font.PLAIN,12));
    }
}
