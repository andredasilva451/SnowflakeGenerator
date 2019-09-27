
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
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
public class SnowFlakeFrame extends JFrame implements MouseListener {
    
    Triangolo a;
    List<CropPoint> cropPoints;
   
    
    public SnowFlakeFrame(){
        super("SnowFlake Generator");
        this.setSize(300,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cropPoints = new ArrayList<CropPoint>();
        this.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        CropPoint point = new CropPoint(e.getX(),e.getY());
        cropPoints.add(point);
        repaint();
        
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
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
    
    public void paint(Graphics g){
        
        super.paint(g);
        int coordX = this.getWidth()/4;
        int coordY = this.getHeight()/4;
        g.setColor(Color.LIGHT_GRAY);
        this.a = new Triangolo(coordX,coordY,this.getWidth()/2,this.getHeight()/2);
        this.a.paint(g);
        int i = 0;
        for(CropPoint p : cropPoints){
            p.paint(g);
            if(i >= 1){
                g.setColor(Color.black);
                g.drawLine(cropPoints.get(i).getX(),cropPoints.get(i).getY(),cropPoints.get(i-1).getX(),cropPoints.get(i-1).getY());
            }
            i++;
        }  
    }
    
    
    public static void main(String[] args){
        
        SnowFlakeFrame b = new SnowFlakeFrame();
        b.setVisible(true);
      
    }
    

}
