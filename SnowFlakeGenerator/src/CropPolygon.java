
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
    private double[] percentagesX;
    private double[] percentagesY;
    private int nPoints;
    
    
    public CropPolygon(int[] pointsX, int[] pointsY,int nPoints){
        
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        this.nPoints = nPoints;
    }
    
    public CropPolygon(int[] pointsX, int[] pointsY,int nPoints, double[] percentagesX, double[] percentagesY){
        
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        this.nPoints = nPoints;
        this.percentagesX = percentagesX;
        this.percentagesY = percentagesY;
    }
    
    public void RefreshPositions(int wContainer, int hContainer){
    
        for(int i = 0; i < this.pointsX.length; i++){
            
              double posXd = ((double)this.percentagesX[i]/100)*wContainer;
              this.pointsX[i] = (int)posXd;
        }
        for(int i = 0; i < this.pointsY.length; i++){
            
            double posYd = ((double)this.percentagesY[i]/100)*hContainer;
            this.pointsY[i] = (int)posYd;
        }
    }
    
            
    public void paint(Graphics g){
    
          g.setColor(Color.BLUE);
          g.fillPolygon(pointsX, pointsY,this.nPoints);
    }
    
    public Polygon toPolygon(){
     
        return new Polygon(this.pointsX,this.pointsY,this.nPoints);
    
    }
    
    
    /**
     * Crea una stringa contenente le percentuali X e Y
     * dei poligoni generati.
     * @return s Stringa con percentuali.
     */
    @Override
    public String toString(){
    
        String s = "";
        s += "X: ";
        for(int i = 0; i < this.pointsX.length; i++){
            
            s += this.percentagesX[i] + (i==this.percentagesX.length-1? "% ":"%, ");
        
        }
        s += "  Y: ";
        
        for(int i = 0; i < this.pointsY.length; i++){
            
            s += this.percentagesY[i] + (i==this.percentagesY.length-1? "% ":"%, ");
        
        }
        return s;
    }
    
    
    

}
