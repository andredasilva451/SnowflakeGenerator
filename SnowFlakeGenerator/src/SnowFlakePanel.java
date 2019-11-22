import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author André Da Silva
 * @version 18.10.19
 */
public class SnowFlakePanel extends JPanel implements MouseListener,MouseMotionListener {
    
    private Triangolo a;
    private List<CropPolygon> polys;
    private ArrayList<CropPoint> cropPoints;
    private ArrayList<ArrayList<CropPoint>> allCropPoints;
    private int pCounter = 0;
    private boolean definePoly = true;
    private Point lastPoint;
    private int lastScreenWidth;
    private int lastScreenHeight;
    private int polyCounter = 0;
    private int lastX;
    private int lastY;
    private boolean drag = false;
    private int offsetX;
    private int offsetY;
    private int pointIndex;
    private MatrixModel m;
    private boolean flakeGenerated = false;
    private Area croppedTriangle;
    
    public SnowFlakePanel(){
        
        this.setBackground(Color.BLUE);
        this.cropPoints = new ArrayList<CropPoint>();
        this.allCropPoints = new ArrayList<ArrayList<CropPoint>>();
        this.polys = new ArrayList<CropPolygon>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.lastPoint = new Point();
        this.lastScreenWidth = this.getWidth();
        this.lastScreenHeight = this.getHeight();
        
    }
    
    /**
     * In caso di click sx del mouse, si occupa di creare i cropPoints
     * per la creazione dei CropPolygon:
     *   - Verifica prima di tutto se l'attributo definePoly è true affinché
     *     ci si trovi in fase di creazione dei crop Points.
     *   - Se la condizione precedente è vera, Crea un oggetto di tipo CropPoint
     *     alle Coordinate del mouse.
     *   - Se il numero di punti creati, stabiliti dall'attributo pCounter, è maggiore di
     *     3, verifica se l'ultimo punto creato si trovi alla stessa coordinata del primo punto
     *     affinché si possa definire il poligono, se questa condizione è quindi vera, definePoly viene
     *     settato a false ed il cropPoint appena definito assumerà le stesse coordinate del primo cropPoint.
     *   - Viene aggiunto l'attuale cropPoint alla lista cropPoints.
     *   - All'ultimo Crop Point definito, viene settato come ultimo punto tramite il metodo setLastPoint
     *     che permetterà di disegnarlo di colore verde, mentre per tutti gli altri viene settato false.
     *   - Se la variabile definePoly è false, setta per ogni cropPoint della lista di 
     *     crop Points, tramite il metodo polygonDefined, affinchè essi possano essere di colore arancione. 
     *   - Se la variabile definePoly è false (da non confondere con la condizione precedente), richiama il metodo
     *   - definePolygon, aggiunge alla lista allCropPoints i valori dell'attuale lista di crop points (cropPoints)
     *   - e pulisce quest'ultima.
     * In caso di click del tasto dx del mouse invece:
     *   - Verifica sempre se definePoly è true.
     *   - Se la condizione precedente è soddisfatta, verifica se il cursore si trova sopra ad un elemento
     *   - della lista di cropPoints, se si, rimuove il punto.
     * @param e Evento del mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == MouseEvent.BUTTON1){
            
            if(this.definePoly){
                
               
                double percentageX = (e.getX()*100)/this.getWidth();
                double percentageY = (e.getY()*100)/this.getHeight();
                CropPoint point = new CropPoint(e.getX(),e.getY(),percentageX,percentageY);
                
                if(this.pCounter >= 3){
                    if(cropPoints.get(0).contains(e.getX(),e.getY())){
                        this.definePoly = false;
                        
                        
                    }
                }
                if(this.definePoly){
                    cropPoints.add(point);
                }
                cropPoints.get(cropPoints.size()-1).setLastPoint(true);
                for(int i = 0; i<cropPoints.size()-1; i++) {
                        cropPoints.get(i).setLastPoint(false);
                }
                if(this.definePoly == false){
                    for(int i = 0; i<cropPoints.size(); i++) {
                        cropPoints.get(i).poligonDefined(true);
                    }
                }
                this.pCounter++;
            }else if(this.definePoly == false){       
                defineCropPolygon();
                this.allCropPoints.add(this.cropPoints);
                this.cropPoints.clear();
            }
            repaint();
        }
        
        if(e.getButton() == MouseEvent.BUTTON3){
           
            if(this.definePoly){
                
                for(int i = 0; i<cropPoints.size()-1; i++) {
                    if(this.cropPoints.get(i).contains(e.getX(),e.getY())){                    
                        this.cropPoints.remove(this.cropPoints.get(i));
                        this.cropPoints.get(this.cropPoints.size()-1).setLastPoint(true);      
                    }
                }
            }
            repaint();
        }    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        this.lastX = e.getX();
        this.lastY = e.getY();
        
        for(int i = 0; i<cropPoints.size(); i++) {
            if(cropPoints.get(i).contains(this.lastX,this.lastY)){
                
                this.drag = true;
                this.offsetX = this.lastX - this.cropPoints.get(i).getX();
                this.offsetY = this.lastY - this.cropPoints.get(i).getY();
                this.pointIndex = i;
            }
        }     
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        this.drag = false;
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
    
    /**
     * In caso di dragging del mouse:
     * Verifica prima di tutto se è presente almeno 1 crop Point, 
     * dopodiché, per ogni punto della lista di Crop Point,
     * verifica se il cursore si trovi sopra per poter effettuare lo spostamento.
     * Funziona sia prima che dopo (solo in caso non venga già disegnato) 
     * la generazione del poligono.
     * @param e Evento del mouse.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        
        if(this.cropPoints.size() > 0){
           
            if(this.drag){
                int x = e.getX() - this.offsetX;
                int y = e.getY() - this.offsetY;
                double percentageX = (x*100)/this.getWidth();
                double percentageY = (y*100)/this.getHeight();
                Point newCoords = new Point(x,y);
                this.cropPoints.get(this.pointIndex).setPoint(newCoords);
                this.cropPoints.get(this.pointIndex).setPercentages(percentageX, percentageY);
                repaint();    
            }             
        }
    }
   
    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
    
    /**
     * Si occupa di disegnare ogni elemento grafico necessario:
     * - Il triangolo, che viene reso proprozionale al frame grazie all'oggetto
     * MatrixModel
     * - I vari crop Points con rispettiva linea.
     * - I vari poligoni generati dai crop Points.
     * @param g Componente grafico.
     */
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        if(this.flakeGenerated == false){
            this.m = new MatrixModel(1,1,100,this.getHeight(),this.getWidth(),9,16);


            this.a = new Triangolo((int)m.getDXYSize()[0],(int)m.getDXYSize()[1],(int)m.getCellSize()[0],(int)m.getCellSize()[1]);
            this.a.paint(g);
            int i = 0;
            for(CropPoint p : this.cropPoints){


                if(this.lastScreenHeight != this.getHeight() || this.lastScreenWidth != this.getWidth()){

                    p.refreshPosition(this.getWidth(),this.getHeight());

                }
                p.paint(g);

                if(i >= 1){
                    g.setColor(Color.black);
                    g.drawLine(this.cropPoints.get(i).getX(),this.cropPoints.get(i).getY(),this.cropPoints.get(i-1).getX(),this.cropPoints.get(i-1).getY());          
                }
                i++;
            }
            if(this.definePoly == false){

                int x1 = this.cropPoints.get(0).getX();
                int y1 = this.cropPoints.get(0).getY();
                int x2 = this.cropPoints.get(this.cropPoints.size()-1).getX();
                int y2 = this.cropPoints.get(this.cropPoints.size()-1).getY();
                g.drawLine(x1,y1,x2,y2); 

            }

            if(this.polys.size() > 0 ){
                for(int j = 0;j<this.polys.size();j++) {
                    if(this.lastScreenHeight != this.getHeight() || this.lastScreenWidth != this.getWidth()){    
                        this.polys.get(j).RefreshPositions(this.getWidth(),this.getHeight());
                    }
                    this.polys.get(j).paint(g);
                }
            }
            this.lastScreenHeight = this.getHeight();
            this.lastScreenWidth = this.getWidth();
        
        }else if(this.flakeGenerated){
            
            Area cropArea = new Area();
            this.croppedTriangle = new Area(this.a.toPolygon());
       
            for(int i = 0; i < this.polys.size(); i++){
           
                Area curPolyArea = new Area(this.polys.get(i).toPolygon());
                cropArea.add(curPolyArea);
            }
            this.croppedTriangle.subtract(cropArea);
            this.removeAll();
            repaint();
            System.out.println("Fiocco generato!");
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.white);    
            g2d.scale(0.5,0.5);
            g2d.fill(this.croppedTriangle);
            AffineTransform at = new AffineTransform();
            at.translate(this.croppedTriangle.getBounds().width-this.croppedTriangle.getBounds2D().getX(),this.croppedTriangle.getBounds2D().getY()-100);
            g2d.fill(mirrorAlongX(at.createTransformedShape(this.croppedTriangle).getBounds2D().getCenterX(),this.croppedTriangle));
            
           
        }
    }
    
    private static Shape mirrorAlongX(double x, Shape shape)
    {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
        at.translate(-x, 0);
        return at.createTransformedShape(shape);
    }
    
    /**
     * Se l'attributo definePoly è false,
     * crea un oggetto di tipo CropPolygon con i crop Points
     * definiti che viene in seguito aggiunto alla lista di
     * CropPolygons. Inoltre, alla fine, definePoly viene
     * settato a true e il contatore di Crop Points azzerato.
     */
    private void defineCropPolygon(){
    
        if(this.definePoly == false){
            
            int[] pointsX = new int[cropPoints.size()];
            int[] pointsY = new int[cropPoints.size()];
            double[] percentagesX = new double[cropPoints.size()];
            double[] percentagesY = new double[cropPoints.size()];
            
            for(int j = 0;j<cropPoints.size();j++) {
                  pointsX[j] = cropPoints.get(j).getX();
                  pointsY[j] = cropPoints.get(j).getY();
            }
            for(int j = 0;j<cropPoints.size();j++) {
                  percentagesX[j] = cropPoints.get(j).getPercentageX();
                  percentagesY[j] = cropPoints.get(j).getPercentageY();
            }
            
            CropPolygon p = new CropPolygon(pointsX,pointsY,this.cropPoints.size(),percentagesX,percentagesY);
            this.polys.add(p);
            this.definePoly = true;
            this.pCounter = 0;   
        }
    }
    
    /**
     * Permette di resettare tutti i punti e i poligoni generati.
     */
    public void pointReset(){
        
        this.cropPoints = new ArrayList<CropPoint>();
        this.allCropPoints = new ArrayList<ArrayList<CropPoint>>();
        this.polys = new ArrayList<CropPolygon>();
        this.pCounter = 0;
        this.polyCounter = 0;
        this.definePoly = true;
        this.flakeGenerated = false;
        repaint();
        
    }
    
    /**
     * Scrive i punti di tutti i poligoni in un file.
     * @param file File in cui salvare i punti
     * @return File con i punti scritti al suo interno
     */
    public File writePoints(File file) throws IOException{
          
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
       
        for(int i = 0; i < this.polys.size(); i++){
           printWriter.println(this.polys.get(i).toString());
        }
        printWriter.close();
        return file;
    }
    
    public void genSnowFlake(){
    
        this.flakeGenerated = true;
        
    }
      
    
    public void readPoints(File file){
        
        
    
    }
    
    
    public void saveSnowFlake(String type){
    
    
    }
}
