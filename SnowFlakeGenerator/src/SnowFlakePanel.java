import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import jankovicsandras.imagetracer.ImageTracer;
import java.awt.Polygon;


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
    private ArrayList<CropPolygon> polys;
    
    /**
     * Lista di tutti i punti ritaglio creati.
     */
    private ArrayList<CropPoint> cropPoints;
    
    /**
     * Lista di liste con tutti i punti creati.
     */
    //private ArrayList<ArrayList<CropPoint>> allCropPoints;
    
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
    
    /**
     * Boolean per effettuare l'ultimo refresh delle posizioni
     * dei punti dei poligoni prima della generazione del fiocco.
     */
    private boolean firstTime = true;
    
    /**
     * Lista di SnowFlakePanelListeners.
     */
    private List<SnowFlakePanelListener> listeners;
    
    /**
     * Costruttore per l'instanziazione del pannello.
     */
    public SnowFlakePanel(){
        
        this.setBackground(Color.BLUE);
        this.cropPoints = new ArrayList<CropPoint>();
        this.polys = new ArrayList<CropPolygon>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.listeners = new ArrayList<SnowFlakePanelListener>();

    }
    
    /**
     * Aggiunge un nuovo SnowFlakeListener.
     * @param newListener nuovo SnowFlakeListener.
     */
    public void addSnowFlakePanelListener(SnowFlakePanelListener newListener){
        this.listeners.add(newListener);
    }
    
    
    /**
     * Si occupa di gestire la creazione/eliminazione di ogni
     * punto di ritaglio.
     * @param e Evento del mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
        //aggiunta punti tramite tasto SX
        if(e.getButton() == MouseEvent.BUTTON1){
            
            //verifica se l'utente può aggiungere/rimuovere punti di ritaglio.
            if(this.definePoly){
                
               
                double percentageX = (e.getX()*100)/this.getWidth();
                double percentageY = (e.getY()*100)/this.getHeight();
                CropPoint point = new CropPoint(e.getX(),e.getY(),percentageX,percentageY);
                
                //verifica se il numero minimo di punti è 3 al fine di creare un poligono, e verifica
		//anche se l'ultimo punto che si vuole creare è contenuto nel primo al fine di chiudere il tutto.
                if(this.pCounter >= 3){
                    if(cropPoints.get(0).contains(e.getX(),e.getY())){
                        this.definePoly = false;   
                    }
                }
                
                //verifica se è possibile aggiungere il punto tramite definePoly.
                if(this.definePoly){
                    this.cropPoints.add(point);
                }
                
                //se il numero di punti creati è 0, setta il colore ciano per questo punto tramite setFirstPoint.
                if(this.pCounter == 0){
                    this.cropPoints.get(0).setFirstPoint(true);
                }
                
                //setta questo punto come ultimo, impostando il colore verde tramite setLastPoint(true) e setta tutti gli altri
                //punti creati false al fine di renderli rossi o ciano per il primo punto.
                cropPoints.get(cropPoints.size()-1).setLastPoint(true);
                for(int i = 0; i<cropPoints.size()-1; i++) {
                        this.cropPoints.get(i).setLastPoint(false);
                }
                
                //se la definizione dei punti del poligono è finita, colora di arancione tutti i punti.
                if(this.definePoly == false){
                    for(int i = 0; i<cropPoints.size(); i++) {
                        this.cropPoints.get(i).poligonDefined(true);
                    }
                }
                
                this.pCounter++;
            }else if(this.definePoly == false){
                
                //definizione del poligono.
                this.defineCropPolygon();
                this.cropPoints.clear();
            }
        }
        
        //rimozione punti tramite tasto DX
        if(e.getButton() == MouseEvent.BUTTON3){
           
            //Verifica se l'utente può aggiungere/rimuovere punti.
            if(this.definePoly){
                
                //verifica se il cursore si trova sopra ad almeno 1 punto.
                for(int i = 0; i<cropPoints.size()-1; i++) {
                    if(this.cropPoints.get(i).contains(e.getX(),e.getY())){                    
                        this.cropPoints.remove(this.cropPoints.get(i));
                        this.cropPoints.get(this.cropPoints.size()-1).setLastPoint(true);
                        this.cropPoints.get(0).setFirstPoint(true);
                    }
                }
            }
        }
        repaint();
    }

    /**
     * Verifica se il mouse viene premuto per poter iniziare
     * ad effettuare un dragging di un punto.
     * @param e Evento del mouse.
     */
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

    /**
     * Verifica se il mouse viene rilasciato per smettere
     * di eseguire il dragging.
     * @param e Evento del mouse.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.drag = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    /**
     * In caso di dragging del mouse:
     * Per ogni punto della lista di Crop Point,
     * verifica se il cursore si trovi sopra per poter effettuare lo spostamento.
     * Funziona sia prima che dopo (solo in caso non venga già disegnato) 
     * la generazione del poligono.
     * @param e Evento del mouse.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
                  
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
   
    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
    
    /**
     * Si occupa di disegnare ogni elemento grafico necessario:
     * - Il triangolo, che viene reso proprozionale al frame grazie all'oggetto
     * MatrixModel
     * - I vari crop Points con rispettiva linea.
     * - I vari poligoni generati dai crop Points.
     * - Il fiocco di neve.
     * @param g Componente grafico.
     */
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        if(this.flakeGenerated == false){
            
            this.m = new MatrixModel(1,1,25,this.getHeight(),this.getWidth(),1,1.73);
            this.a = new Triangolo((int)m.getDXYSize()[0],(int)m.getDXYSize()[1],(int)m.getCellSize()[0],(int)m.getCellSize()[1]);
            this.a.paint(g);
            int i = 0;
            
            for(CropPolygon cp : this.polys) {   
                    
                cp.RefreshPositions(this.getWidth(),this.getHeight());
                cp.paint(g);
            }
            if(this.polys.size() > 0){
               
                for(SnowFlakePanelListener l : this.listeners){
                    l.polygonCreated(this.polys);
                }
            }
            for(CropPoint p : this.cropPoints){
                
                p.refreshPosition(this.getWidth(),this.getHeight());  
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
            
        }else if(this.flakeGenerated){
            
            if(this.firstTime){
                for(int i =0 ; i < this.polys.size();i++){
                    this.polys.get(i).RefreshPositions(this.getWidth(),this.getHeight());
                }
                this.firstTime = false;
            }
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
        //this.allCropPoints = new ArrayList<ArrayList<CropPoint>>();
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
     * @return  File PNG/SVG.
     */
    public File saveSnowFlake(String type,String path) throws Exception {
    
        if(type.equals("png")){
            
            BufferedImage img = new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);
            this.paint(img.getGraphics());
            File out = new File(path);
            try {
                ImageIO.write(img,"png", out);   
            } catch (IOException ex) {
                Logger.getLogger(SnowFlakePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return out;
            
        }else if(type.equals("svg")){
            
           BufferedImage img = new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);
           this.paint(img.getGraphics());
           ImageTracer.saveString(path,ImageTracer.imageToSVG(img,null,null));
               
        }
        return null;
    }
}
