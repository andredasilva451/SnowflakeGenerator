
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class SnowFlakeFrame extends Frame implements MouseListener,MouseMotionListener {
    
    private Triangolo a;
    private List<CropPolygon> polys;
    private List<CropPoint> cropPoints;
    private List<CropPoint> allCropPoints;
    private int pCounter = 0;
    private boolean definePoly = true;
    private Point lastPoint;
    private Button genera;
    private int lastScreenWidth;
    private int lastScreenHeight;
    
    public SnowFlakeFrame(){
        super("SnowFlake Generator");
        this.setSize(1024,768);
        this.setMinimumSize(new Dimension(1024,768));
        this.setBackground(Color.BLUE);
        this.cropPoints = new ArrayList<CropPoint>();
        this.allCropPoints = new ArrayList<CropPoint>();
        this.polys = new ArrayList<CropPolygon>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.lastPoint = new Point();
        this.genera = new Button("Genera");
        this.genera.setBounds(100,100,100,100);
        this.genera.setActionCommand("Genera");
        
        this.lastScreenWidth = this.getWidth();
        this.lastScreenHeight = this.getHeight();
        
        this.genera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton() == MouseEvent.BUTTON1){
            
            if(this.definePoly){
                
                CropPoint point = new CropPoint(e.getX(),e.getY());
                
                if(this.pCounter >= 3){
                    if(cropPoints.get(0).contains(e.getX(),e.getY())){
                        this.definePoly = false;
                        point = new CropPoint(cropPoints.get(0).getX(),cropPoints.get(0).getY());
                        
                    }
                }
                cropPoints.add(point);
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
                this.cropPoints.clear();
                this.allCropPoints.addAll(this.cropPoints);
            }
            repaint();
        }
        
        if(e.getButton() == MouseEvent.BUTTON3){
           
            if(this.definePoly){
                for(int i = 0; i<cropPoints.size(); i++) {
                    if(cropPoints.get(i).contains(e.getX(),e.getY())){
                        
                        this.cropPoints.remove(this.cropPoints.get(i));
                        cropPoints.get(this.cropPoints.size()-1).setLastPoint(true);
                    }
                }
            }
            repaint();
        }    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
        if(this.cropPoints.size() > 0){
            this.lastPoint = e.getPoint();
            for(int i = 0; i<cropPoints.size(); i++) {
                if(cropPoints.get(i).contains(e.getX(),e.getY())){
                    cropPoints.get(i).setPoint(this.lastPoint);           
                }
            }
            if(this.polys.size() > 0 && this.definePoly == false){
                this.definePoly = false;
                this.polys.remove(this.polys.get(this.polys.size()-1));
                defineCropPolygon();
            }
            repaint();   
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
    
    public void paint(Graphics g){
  
        
        MatrixModel m = new MatrixModel(1,1,150,this.getHeight(),this.getWidth(),1,1);

        this.a = new Triangolo((int)m.getDXYSize()[0],(int)m.getDXYSize()[1],(int)m.getCellSize()[0],(int)m.getCellSize()[1]);
        this.a.paint(g);
        int i = 0;
        for(CropPoint p : this.cropPoints){

            p.paint(g);
            if(i >= 1){
                g.setColor(Color.black);
                g.drawLine(this.cropPoints.get(i).getX(),this.cropPoints.get(i).getY(),this.cropPoints.get(i-1).getX(),this.cropPoints.get(i-1).getY());          
            }
            i++;
        }
        if(this.polys.size() > 0 ){
            for(int j = 0;j<this.polys.size();j++) {
                this.polys.get(j).paint(g);
            }
        }     
    }
    
    private void defineCropPolygon(){
    
        if(this.definePoly == false){
            
            int[] pointsX = new int[cropPoints.size()];
            int[] pointsY = new int[cropPoints.size()];
            
            for(int j = 0;j<cropPoints.size();j++) {
                  pointsX[j] = cropPoints.get(j).getX();
                  pointsY[j] = cropPoints.get(j).getY();
            }
            
            CropPolygon p = new CropPolygon(pointsX,pointsY,this.cropPoints.size());
            this.polys.add(p);
            this.definePoly = true;
            this.pCounter = 0;      
        }
    }
    
    public static void main(String[] args){
        
        SnowFlakeFrame b = new SnowFlakeFrame();
        b.setVisible(true); 
        b.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
    
    
}
