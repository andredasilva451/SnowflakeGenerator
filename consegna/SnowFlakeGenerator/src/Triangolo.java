import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * Classe che rappresenta un modello astratto 
 * di un triangolo.
 * @author André Da Silva
 * @version 04.10.19
 */
public class Triangolo  {
    
    /**
     * Coordinata X dell'angolo in alto a sinistra del triangolo.
     */
    private int posX;
    
    /**
     * Coordinata Y dell'angolo in alto a sinistra del triangolo.
     */
    private int posY;
    
    /**
     * Larghezza del triangolo.
     */
    private int width;
    
    /**
     * Altezza del triangolo.
     */
    private int height;
    
    /**
     * Array contenente le 3 coordinate X del triangolo.
     */
    private int[] pointX;
    
    /**
     * Array contenente le 3 coordinate Y del triangolo.
     */
    private int[] pointY;
    
    /**
     * Colore del triangolo (Default = lightGray).
     */
    private Color color = Color.lightGray;
    
    public Triangolo(int posX,int posY, int width, int height){
    
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.pointX = new int[3];
        this.pointY = new int[3];
    }
    
    /**
     * Permette di definire le 3 coordinate X dei punti del triangolo.
     * @return Coordinate X.
     */
    private int[] pointsXdefinition(){
       
        this.pointX[0] = this.posX;
        this.pointX[1] = this.posX + this.width;
        this.pointX[2] = this.posX + this.width;
        return pointX;
    
    }
    
    /**
     * Permette di definire le 3 coordinate Y dei punti del triangolo.
     * @return Coordinate Y.
     */
    private int[] pointsYdefinition(){
        
        this.pointY[0] = this.posY;
        this.pointY[1] = this.posY;
        this.pointY[2] = this.posY + this.height;  
        return pointY;
    }
    
    /**
     * Ritorna il triangolo convertito in Polygon.
     * @return Triangolo convertito in Polygon.
     */
    public Polygon toPolygon(){
    
        return new Polygon(this.pointX,pointY,3);
    
    }
    
    /**
     * Ritorna la larghezza del Triangolo.
     * @return 
     */
    public int getWidth(){
        
        return this.width;
    }
    
    /**
     * Ritorna la l'altezza del Triangolo.
     * @return 
     */
    public int getHeight(){
    
        return this.height;
    }
    
    /**
     * Permette di settare il colore del triangolo tramite Color.
     * @param color Colore da impostare.
     */
    public void setColor(Color color){
        this.color = color;
    }
   
    /**
     * Disegna il triangolo.
     * @param g Componente grafico.
     */
    public void paint(Graphics g){
        
        int[] pointsX = pointsXdefinition();
        int[] pointsY = pointsYdefinition();
        g.setColor(this.color);
        g.fillPolygon(pointsX, pointsY,3);
        
    }
}