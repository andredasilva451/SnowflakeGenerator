import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author André Da Silva
 * @version 04.10.19
 */
public class Triangolo  {
    
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int[] pointX;
    private int[] pointY;
    
    public Triangolo(int posX,int posY, int width, int height){
    
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.pointX = new int[3];
        this.pointY = new int[3];
    }
    
    private int[] pointsXdefinition(){
       
        this.pointX[0] = this.posX;
        this.pointX[1] = this.posX + this.width;
        this.pointX[2] = this.posX + this.width;
        return pointX;
    
    }
    
    private int[] pointsYdefinition(){
        
        this.pointY[0] = this.posY;
        this.pointY[1] = this.posY;
        this.pointY[2] = this.posY + this.height;  
        return pointY;
    }
    
    public Polygon toPolygon(){
    
        return new Polygon(this.pointX,pointY,3);
    
    }
   
    public void paint(Graphics g){
        
        int[] pointsX = pointsXdefinition();
        int[] pointsY = pointsYdefinition();
        g.setColor(Color.lightGray);
        g.fillPolygon(pointsX, pointsY,3);
        
    }
}