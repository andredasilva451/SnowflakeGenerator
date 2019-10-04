import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * @author André Da Silva
 * @version 04.10.19
 */
public class CropPoint {

    private int posX;
    private int posY;
    private final int CROP_POINT_SIZE = 8;
    private boolean isLastPoint;
    private boolean polyDefined;
    
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
    
    public void setX(int newX){
        this.posX = newX;
    }
    
    public void setY(int newY){
    
        this.posY = newY;
    }
    
    public boolean contains(int x, int y){
        
        Point p = new Point(x,y);
        Point center = new Point(this.posX+CROP_POINT_SIZE/2,this.posY+CROP_POINT_SIZE/2);  
        return center.distance(p) <= CROP_POINT_SIZE/2;
    }
    
    public void setLastPoint(boolean s){
        this.isLastPoint = s;
    }
    public void poligonDefined(boolean s){
        this.polyDefined = s;
    }
    
    
    public void paint(Graphics g){
         
        if(this.isLastPoint){
            g.setColor(Color.green);
        }else{
            g.setColor(Color.red);
        }
        if(this.polyDefined){
            g.setColor(Color.orange);
        }
        g.fillOval(this.posX, this.posY,CROP_POINT_SIZE,CROP_POINT_SIZE);
    }
    
}
