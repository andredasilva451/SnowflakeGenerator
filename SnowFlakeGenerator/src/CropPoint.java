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
    private final int CROP_POINT_SIZE = 10;
    private boolean isLastPoint;
    private boolean isFirstPoint;
    private boolean polyDefined;
    private double percentageX;
    private double percentageY;
    
    //per punti statici.
    public CropPoint(int posX,int posY){
  
         this.posX = posX - CROP_POINT_SIZE/2;
         this.posY = posY - CROP_POINT_SIZE/2;
        
    }
    
    //per punti responsive.
    public CropPoint(int posX,int posY, double percentageX, double percentageY){
  
        this.posX = posX - CROP_POINT_SIZE/2;
        this.posY = posY - CROP_POINT_SIZE/2;
        this.percentageX = percentageX;
        this.percentageY = percentageY;
            
    }
    
    /**
     * Aggiorna la posizione del punto in base alla dimensione del contenitore
     * e la percentuale che occupa.
     * @param wContainer Larghezza del contenitore.
     * @param hContainer Altezza del contenitore.
     */
     public void refreshPosition(int wContainer, int hContainer){
        
        double posXd = ((double)this.percentageX/100)*wContainer;
        double posYd = ((double)this.percentageY/100)*hContainer;
        this.posX = (int)posXd;
        this.posY = (int)posYd;
         
    }
    
    
    public int getX(){
    
        return this.posX + CROP_POINT_SIZE/2;
    }
    
    public int getY(){
    
        return this.posY + CROP_POINT_SIZE/2;
    }
    
     public double getPercentageX(){
    
        return this.percentageX;
    }
    
    public double getPercentageY(){
    
        return this.percentageY;
    }
    
      
    public void setX(int newX){
        this.posX = newX;
    }
    
    public void setY(int newY){
    
        this.posY = newY;
    }
    
    public void setPercentages(double newPercentageX,double newPercentageY){
        this.percentageX = newPercentageX;
        this.percentageY = newPercentageY;
        
    }
    
    public void setPoint(Point p){
        
        this.posX = p.x;
        this.posY = p.y;
    
    }
    
    public String toStringPoints(){
    
        return this.posX + "," + this.posY;
    }
    
    public Point getPoint(){
    
        Point p = new Point();
        p.x = this.posX;
        p.y = this.posY;
        return p;
    
    }
    
    public int getSize(){
        
        return CROP_POINT_SIZE;
    
    }
    
    public boolean contains(int x, int y){
        
        Point p = new Point(x,y);
        Point center = new Point(this.posX+CROP_POINT_SIZE/2,this.posY+CROP_POINT_SIZE/2);  
        return center.distance(p) <= CROP_POINT_SIZE/2;
    }
    
    
    public void setLastPoint(boolean s){
        this.isLastPoint = s;
    }
    
    public void setFirstPoint(boolean s){
        this.isFirstPoint = s;
    }
    
    public void poligonDefined(boolean s){
        this.polyDefined = s;
    }
    
    public void paint(Graphics g){
         
        if(this.isLastPoint){
            g.setColor(Color.green);
        }else if(this.isFirstPoint){
            g.setColor(Color.cyan);
        }else{
            g.setColor(Color.red);
        }
        if(this.polyDefined){
            g.setColor(Color.orange);
        }
        g.fillOval(this.posX, this.posY,CROP_POINT_SIZE,CROP_POINT_SIZE);
    }
    
}
