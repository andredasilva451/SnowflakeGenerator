import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class Triangolo {
    
    private int posX;
    private int posY;
    private int height;
    private int width;
    
    
    public void paint(Graphics g){
        
        int[] pointX = new int[3];
        int[] pointY = new int[3];
        pointX[0] = this.posX;
        pointX[1] = this.posX + this.width;
        pointX[2] = this.posX + (this.posX + height)/2;
        
        pointY[0] = this.posY;
        pointY[1] = this.posY+(this.width/5);
        pointY[2] = this.posY + this.width;
        
        
    }
}