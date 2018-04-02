/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObjects;

import client.ArcadeDemo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A store is an object which remembers which stats a player has upgraded
 * Each client has a store called myStore in the ArcadeDemo class
 * Contains a store display to upgrade stats
 * @authors 20kanedas, 20hebbarv
 */
public class Store {        
    private int remainingSkillTokens;
    //Stores the number of skill points invested in each stat
    private ArrayList<Integer> stats = new ArrayList<Integer>();
    public ArrayList<Integer> getStats() {return stats;}
    //Health can be accessed as stats.get(HEALTH), speed as stats.get(SPEED), etc.
    final int HEALTH = 0;
    final int SPEED = 1;
    final int STAMINA = 2;
    final int SIGHT = 3;
    final int B_DAMAGE = 4;
    final int B_SPEED = 5;
    final int RANGE = 6;
    final int RELOAD = 7;
    
    private boolean stealth;
    public boolean hasStealth() {return stealth;}
    
    private int xOffset = 0;
    private int yOffset = 0;
    
    public Store() {
        stats = new ArrayList<Integer>();
        for(int i = 0; i < 8; i++) {
            stats.add(0);
        }
        stealth = false;
        remainingSkillTokens = 60;
    }
    
    //Draws the store display. Store will have buttons to increase/decrease different stats.
    public void drawStore(Graphics g, Player myself, int xOff, int yOff) {
        //Set the offsets
        xOffset = xOff;
        yOffset = yOff;
        
        //Changing font to bold
        Font myFont = new Font("Arial", Font.BOLD, 24);
        g.setFont(myFont);
        
        //Displaying the number of remaining skill tokens
        g.drawString(remainingSkillTokens + " skill tokens remaining.", 10 + xOffset, 20 + yOffset);
        g.drawString("Health" + ": " + (100 + 10*stats.get(HEALTH)), 10 + xOffset, 60 + yOffset);
        g.drawString("Speed" + ": " + (150 + (int)(7.5*stats.get(SPEED))), 10 + xOffset, 100 + yOffset);
        g.drawString("Stamina" + ": " + (100 + 20*stats.get(STAMINA)), 10 + xOffset, 140 + yOffset);
        g.drawString("Sight Range" + ": " + (200 + 15*stats.get(SIGHT)), 10 + xOffset, 180 + yOffset);
        g.drawString("Bullet Damage" + ": " + (20 + 4*stats.get(B_DAMAGE)), 10 + xOffset, 220 + yOffset);
        g.drawString("Bullet Speed" + ": " + (300 + 120*stats.get(B_SPEED)), 10 + xOffset, 260 + yOffset);
        g.drawString("Bullet Range" + ": " + (200 + 80*stats.get(RANGE)), 10 + xOffset, 300 + yOffset);
        double reloadNum = 0.5 + 0.1*stats.get(RELOAD);
        reloadNum *= 10;
        g.drawString("Bullet Reload" + ": " + (((double)((int)(reloadNum)))/10), 10 + xOffset, 340 + yOffset);
        g.drawString("Stealth" + ": " + stealth, 10 + xOffset, 380 + yOffset);
        
        //Buttons to increase or decrease stats
        for(int j = 0; j < 12; j++) {
            for(int i = 0; i < 8; i++) {
                g.drawRect(250 + xOffset + 30*j, 40 + yOffset + 40*i, 20, 20);
                if(j==0) {
                    g.setColor(Color.RED);
                    g.fillRect(250 + xOffset + 30*j, 40 + yOffset + 40*i, 20, 20);
                    g.setColor(Color.BLACK);
                }
                if(0<j && j<11 && j<=stats.get(i)) {
                    g.setColor(new Color(150,255,100));
                    g.fillRect(251 + xOffset + 30*j, 41 + yOffset + 40*i, 19, 19);
                    g.setColor(Color.BLACK);
                }
                if(j==11) {
                    g.setColor(Color.GREEN);
                    g.fillRect(250 + xOffset + 30*j, 40 + yOffset + 40*i, 20, 20);
                    g.setColor(Color.BLACK);
                }
            }
        }
        for(int i = 0; i < 8; i++) {
            g.drawString("-", 256 + xOffset, 58 + yOffset + 40*i);
            g.drawString("+", 584 + xOffset, 58 + yOffset + 40*i);
            g.setFont(new Font("Arial",Font.BOLD,14));
            if(i<4) {
                g.drawString("1 token per level", xOffset + 610, 55 + yOffset + 40*i);
            } else g.drawString("2 tokens per level", xOffset + 610, 55 + yOffset + 40*i);
            g.setFont(new Font("Arial",Font.BOLD,24));
        }
        
        //Exit button
        g.drawRect(xOffset + 10, yOffset + 400, 150, 30);
        g.setColor(new Color(150,255,100));
        g.fillRect(xOffset + 10, yOffset + 400, 150, 30);
        g.setColor(Color.BLACK);
        g.drawString("Play Game", xOffset + 25, yOffset + 423);
        
        //Stealth button
        g.drawRect(xOffset + 180, yOffset + 358, 210, 30);
        if(stealth) {
            g.setColor(new Color(255,150,150));
        } else g.setColor(new Color(150,255,100));
        g.fillRect(xOffset + 180, yOffset + 358, 210, 30);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.drawString("Toggle Stealth (10 Tokens)", xOffset + 190, yOffset + 378);
        
        //Reset font
        g.setFont(new Font("Arial",Font.PLAIN,12));
    }
    
    //Called from arcadeDemo when mouse is clicked during store display mode. Detects button press, then changes stats
    public void buttonPressed(double mouseX, double mouseY, Player p, ArcadeDemo arcade) {
        if(250+xOffset < mouseX && mouseX < 270+xOffset) {
            for(int i = 0; i < 8; i++) {
                if(40+yOffset+40*i < mouseY && 60+yOffset+40*i > mouseY) {
                    if(stats.get(i) > 0) {
                        stats.set(i, stats.get(i) - 1);
                        if(i>3) {
                            remainingSkillTokens += 2; //Bullet stats cost 2
                        } else remainingSkillTokens++; //Others cost 1
                    }
                } 
            }
        }
        if(580+xOffset < mouseX && mouseX < 600+xOffset) {
            for(int i = 0; i < 8; i++) {
                if(40+yOffset+40*i < mouseY && 60+yOffset+40*i > mouseY) {
                    if(stats.get(i) < 10 && ((remainingSkillTokens > 0 && i<4) || remainingSkillTokens > 1)) {
                        stats.set(i, stats.get(i) + 1);
                        if(i>3) {
                            remainingSkillTokens -= 2; //Bullet stats cost 2
                        } else remainingSkillTokens--; //Others cost 1
                    }
                } 
            }
        }
        
        //Exit Button
        if(10+xOffset < mouseX && mouseX < 160+xOffset && 400+yOffset < mouseY && mouseY < 430+yOffset) 
        {
            //Must send the updated player data to the server when leaving store. 
            System.out.println("sending: STO"+p.pack());
            arcade.sendMessage("STO"+p.pack());
            arcade.setMode("Game");
        }
        
        //Stealth Button
        if(xOffset + 180 < mouseX && mouseX < xOffset + 390 && yOffset + 358 < mouseY && mouseY < yOffset + 388) {
            if(!stealth && remainingSkillTokens >= 10) {
                stealth = true;
                remainingSkillTokens -= 10;
            } else if(stealth) {
                stealth = false;
                remainingSkillTokens += 10;
            }
        }
        
        //Update player stats
        p.updateStats(this);
        arcade.sendMessage("STO"+p.pack());
    }
}

