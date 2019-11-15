
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author andre
 */
public class CropPolygon {
    
    private int[] pointsX;
    private int[] pointsY;
    private int nPoints;
    
    public CropPolygon(int[] pointsX, int[] pointsY,int nPoints){
        
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        this.nPoints = nPoints;
    }
        
    public void paint(Graphics g){
    
          g.setColor(Color.BLUE);
          g.fillPolygon(pointsX, pointsY,this.nPoints);
    }
    
    public Polygon toPolygon(){
    
        return new Polygon(this.pointsX,this.pointsY,3);
    
    }
    
    @Override
    public String toString(){
    
        String s = "";
        s += "X: ";
        for(int i = 0; i < this.pointsX.length; i++){
            
            s += this.pointsX[i] + (i==this.pointsX.length-1? "":", ");
        
        }
        s += "  Y: ";
        
        for(int i = 0; i < this.pointsY.length; i++){
            
            s += this.pointsY[i] + (i==this.pointsY.length-1? "":", ");
        
        }
        return s;
    }
    
    
    

}
