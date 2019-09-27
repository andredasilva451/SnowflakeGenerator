
import java.awt.Color;
import java.awt.Graphics;
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
public class SnowFlakeFrame extends JFrame {
    
    Triangolo a;
    
    public SnowFlakeFrame(){
        super("SnowFlake Generator");
        this.setSize(200,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    public void paint(Graphics g){
        super.paint(g);
        int coordX = this.getWidth()/4;
        int coordY = this.getHeight()/4;
        g.setColor(Color.blue);
        this.a = new Triangolo(this.getWidth()/2,this.getHeight()/2,coordX,coordY);
        this.a.paint(g);
        
    }
    
    public static void main(String[] args){
        
        SnowFlakeFrame b = new SnowFlakeFrame();
        b.setVisible(true);
        
    
    }
}
