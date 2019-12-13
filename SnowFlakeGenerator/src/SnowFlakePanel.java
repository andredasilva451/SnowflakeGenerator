import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author André Da Silva
 * @version 18.10.19
 */
public class SnowFlakePanel extends JPanel implements MouseListener,MouseMotionListener {
    
    /**
     * Triangolo da da cui trarre il fiocco di neve.
     */
    private Triangolo a;
    
    /**
     * Lista di tutti i poligoni di ritaglio creati.
     */
    private List<CropPolygon> polys;
    
    /**
     * Lista di tutti i punti ritaglio creati.
     */
    private ArrayList<CropPoint> cropPoints;
    
    /**
     * Lista di liste con tutti i punti creati.
     */
    private ArrayList<ArrayList<CropPoint>> allCropPoints;
    
    /**
     * Numero di punti creati.
     */
    private int pCounter = 0;
    
    /**
     * Flag per identificare se l'utente sta creando un poligono.
     */
    private boolean definePoly = true;
    
    /**
     * Ultima larghezza salvata del Pannello.
     */
    private int lastScreenWidth;
    
    /**
     * Ultima altezza salvata del Pannello.
     */
    private int lastScreenHeight;
    
    /**
     * Ultima coordinata X della pressione del mouse.
     */
    private int lastX;
    
    /**
     * Ultima coordinata Y della pressione del mouse.
     */
    private int lastY;
    
    /**
     * Permette di capire se il mouse viene trascinato.
     */
    private boolean drag = false;
    
    /**
     * Coordinata X del dragging rispetto a quella della pressione del mouse.
     */
    private int offsetX;
    
    /**
     * Coordinata Y del dragging rispetto a quella della pressione del mouse.
     */
    private int offsetY;
    
    /**
     * Indica qual'è il punto da dover trascinare.
     */
    private int pointIndex;
    
    /**
     * Permette di definire la proporzione del Triangolo in base alla grandezza del pannello.
     */
    private MatrixModel m;
    
    /**
     * Flag che indica se il fiocco di neve viene generato.
     */
    private boolean flakeGenerated = false;
    
    /**
     * Istanza di Fiocco di neve.
     */
    private SnowFlake sf;
    
    public SnowFlakePanel(){
        
        this.setBackground(Color.BLUE);
        this.cropPoints = new ArrayList<CropPoint>();
        this.allCropPoints = new ArrayList<ArrayList<CropPoint>>();
        this.polys = new ArrayList<CropPolygon>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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
        
        //aggiunta punti
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
                    this.cropPoints.add(point);
                }
                if(this.pCounter == 0){
                    this.cropPoints.get(0).setFirstPoint(true);
                }
                cropPoints.get(cropPoints.size()-1).setLastPoint(true);
                for(int i = 0; i<cropPoints.size()-1; i++) {
                        this.cropPoints.get(i).setLastPoint(false);
                }
                if(this.definePoly == false){
                    for(int i = 0; i<cropPoints.size(); i++) {
                        this.cropPoints.get(i).poligonDefined(true);
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
        //rimozione punti
        if(e.getButton() == MouseEvent.BUTTON3){
           
            if(this.definePoly){
                
                for(int i = 0; i<cropPoints.size()-1; i++) {
                    if(this.cropPoints.get(i).contains(e.getX(),e.getY())){                    
                        this.cropPoints.remove(this.cropPoints.get(i));
                        this.cropPoints.get(this.cropPoints.size()-1).setLastPoint(true);
                        this.cropPoints.get(0).setFirstPoint(true);
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
            this.m = new MatrixModel(1,1,25,this.getHeight(),this.getWidth(),1,1.73);
            this.a = new Triangolo((int)m.getDXYSize()[0],(int)m.getDXYSize()[1],(int)m.getCellSize()[0],(int)m.getCellSize()[1]);
            this.a.paint(g);
            int i = 0;
            
            if(this.polys.size() > 0 ){
                for(int j = 0;j<this.polys.size();j++) {
                    if(this.lastScreenHeight != this.getHeight() || this.lastScreenWidth != this.getWidth()){    
                        this.polys.get(j).RefreshPositions(this.getWidth(),this.getHeight());
                    }
                    this.polys.get(j).paint(g);
                }
            }
            
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
            this.lastScreenHeight = this.getHeight();
            this.lastScreenWidth = this.getWidth();
            
        }else if(this.flakeGenerated){
            
            this.sf = new SnowFlake(this.a,this.polys,this.getWidth(),this.getHeight());
            this.sf.paint(g);
            
        }
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
    
    /**
     * Se il tasto Genera del Frame viene premuto, richiama questo metodo
     * che setta il flag "FlakeGenerated" a true affinchè si impedisca all'utente
     * di creare punti e poligoni in quanto il fiocco viene generato.
     * Inoltre, rimuove tutti gli elementi dal Panel ed esegue un repaint.
     */
    public void genSnowFlake(){
    
        this.flakeGenerated = true;
        this.removeAll();
        repaint();  
    }
     
    /**
     * Legge i punti da un file binario.
     * @param file File da leggere.
     * @throws java.io.FileNotFoundException
     */
    public void readPoints(File file) throws FileNotFoundException, IOException {
        
        BufferedReader in = new BufferedReader(new FileReader(file));    
        this.pointReset();
        String st;
        while ((st = in.readLine()) != null) {
            for(int i = 0; i < st.length()-1;i++){
                if(st.charAt(i) == '|'){   
                    String x = st.substring(0,i);
                    String y = st.substring(i+1,st.length()-1);
                    String[] splitX = x.split("-");
                    String[] splitY = y.split("-");
                    if(splitX.length == splitY.length){
                        for(int j = 0; j < splitX.length; j++){
                            CropPoint p = new CropPoint(0,0,(int)Double.parseDouble(splitX[j]),(int)Double.parseDouble(splitY[j]));
                            p.refreshPosition(this.getWidth(),this.getHeight());
                            this.cropPoints.add(p);
                        }
                        this.definePoly = false;
                        this.defineCropPolygon();
                        this.allCropPoints.add(this.cropPoints);
                        this.cropPoints.clear();
                    }else{
                        System.out.println("Impossibile!");
                    }
                }
            }
        }
        repaint();
    }
    
    /**
     * Esegue un render del fiocco di neve specificando il tipo
     * di salvataggio (png o svg).
     * @param type Il tipo di render da eseguire (raster o vettoriale). 
     */
    public void saveSnowFlake(String type){
    
        if(type.equals("png")){
        
            
            
        }else if(type.equals("svg")){
        
        }
    }
}
