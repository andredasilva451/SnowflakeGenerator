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
    private int width;
    private int height;
    
    public Triangolo(int posX,int posY, int width, int height){
    
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public int[] pointsXdefinition(){
       
        int[] pointX = new int[3];
        pointX[0] = this.posX;
        pointX[1] = this.posX + this.width;
        pointX[2] = this.posX + (this.posX + height)/2;
        return pointX;
    
    }
    
    public int[] pointsYdefinition(){
        
        int[] pointY = new int[3];
        pointY[0] = this.posY;
        pointY[1] = this.posY+(this.width/5);
        pointY[2] = this.posY + this.width;
        
        return pointY;
    }
    
 
    
    public void paint(Graphics g){
        
        int[] pointsX = pointsXdefinition();
        int[] pointsY = pointsYdefinition();
        
        g.fillPolygon(pointsX, pointsY,3);
        
    }
}