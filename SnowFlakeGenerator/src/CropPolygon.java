
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * Classe che rappresenta un modello astratto di un poligono
 * per il ritaglio.
 * @author André Da Silva
 * @version 13.12.19
 */
public class CropPolygon {
    
    /**
     * Tutte le coordinate X dei vari punti del poligono.
     */
    private int[] pointsX;
    
    /**
     * Tutte le coordinate Y dei vari punti del poligono.
     */
    private int[] pointsY;
    
    /**
     * Percentuale X della posizione dei punti del poligono.
     */
    private double[] percentagesX;
    
    /**
     * Percentuale Y della posizione dei punti del poligono.
     */
    private double[] percentagesY;
    
    /**
     * Numero di punti che formano il poligono.
     */
    private int nPoints;
    
    
    /**
     * Costruttore che permette l'istanziazione dell'oggetto tramite 5 parametri:
     * @param pointsX Coordinate X dei punti di ogni poligono.
     * @param pointsY Coordinate Y dei punti di ogni poligono.
     * @param nPoints numero di punti che compongono il poligono.
     * @param percentagesX Percentuale X della posizione rispetto al contenitore di ogni coordinata.
     * @param percentagesY Percentuale Y della posizione rispetto al contenitore di ogni coordinata.
     */
    public CropPolygon(int[] pointsX, int[] pointsY,int nPoints, double[] percentagesX, double[] percentagesY){
        
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        this.nPoints = nPoints;
        this.percentagesX = percentagesX;
        this.percentagesY = percentagesY;
    }
    
    /**
     * Aggiorna la posizione del poligono.
     * @param wContainer Larghezza del contenitore.
     * @param hContainer  Altezza del contenitore.
     */
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
            
    /**
     * Disegna il poligono.
     * @param g Componente grafico.
     */
    public void paint(Graphics g){
    
          g.setColor(Color.blue);
          g.fillPolygon(pointsX, pointsY,this.nPoints);
    }
    
    /**
     * Ritorna il CropPolygon in oggetto di tipo Polygon.
     * @return CropPolygon convertito in Polygon.
     */
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
        for(int i = 0; i < this.pointsX.length; i++){
            
            s += this.percentagesX[i] + (i==this.percentagesX.length-1? "|":"-");    
        }
        for(int i = 0; i < this.pointsY.length; i++){
            
            s += this.percentagesY[i] + (i==this.percentagesY.length-1? "|":"-");
        }
        return s;
    }
    
    
    

}
