
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andre
 */
public class PreviewPanel extends JPanel {
    
    private List<CropPolygon> cp;
    private Triangolo t;
    private MatrixModel m;
    private SnowFlake sf;
    private boolean isFlakeGenerated = false;
    
    
    public PreviewPanel() {
        
        this.setBackground(Color.blue);
        this.cp = new ArrayList<CropPolygon>();
        this.sf = new SnowFlake();
    }
    
    public void setCropPolygon(List<CropPolygon> cp){
        
        this.cp.clear();
        this.cp.addAll(cp);
        repaint();
    }
  
    public void SnowFlakeCreated(boolean flag){
    
        this.isFlakeGenerated = true;
        this.repaint();
    }
    
    public void resetCropPolygons(){
    
        this.cp.clear();
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if(!this.isFlakeGenerated){
            this.m = new MatrixModel(1,1,25,this.getHeight(),this.getWidth(),1,1.73);
            this.t = new Triangolo((int)m.getDXYSize()[0],(int)m.getDXYSize()[1]+25,(int)m.getCellSize()[0],(int)m.getCellSize()[1]);
            this.t.setColor(Color.blue);
            this.t.paint(g);
            for(int i = 0; i < this.cp.size();i++){
                this.cp.get(i).RefreshPositions(this.getWidth(),this.getHeight());
            }
            if(this.cp.size() > 0){

               SnowFlake s = new SnowFlake(this.t,this.cp,this.getWidth(),this.getHeight());
                s.paint(g);
            }
        }  
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
