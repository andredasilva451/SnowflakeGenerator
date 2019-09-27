import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class CropPoint {

    private int posX;
    private int posY;
    private final int CROP_POINT_SIZE = 8;
    
    public CropPoint(int posX,int posY){
  
         this.posX = posX - CROP_POINT_SIZE/2;
         this.posY = posY - CROP_POINT_SIZE/2;
        
    }   
    
    public int getX(){
    
        return this.posX + CROP_POINT_SIZE/2;
    }
    
    public int getY(){
    
        return this.posY + CROP_POINT_SIZE/2;
    }
    
    public boolean contains(int x, int y){
        
        Point p = new Point(x,y);
        Rectangle r = new Rectangle(this.posX,this.posY,CROP_POINT_SIZE,CROP_POINT_SIZE);    
        return r.contains(p);
    }
    
    
    public void paint(Graphics g){
         
        g.setColor(Color.red);
        g.fillOval(this.posX, this.posY,CROP_POINT_SIZE,CROP_POINT_SIZE);
    }
    
}
