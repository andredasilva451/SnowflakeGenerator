import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.List;

/**
 * Classe che rappresenta un modello astratto di un fiocco di neve.
 * @author André Da Silva
 */
public class SnowFlake {
    
    private Triangolo a;
    private List<CropPolygon> polys;
    private int wContainer;
    private int hContainer;
    
    public SnowFlake(Triangolo a,List<CropPolygon> polys, int wContainer, int hContainer){
        
        this.a = a;
        this.polys = polys;
        this.wContainer = wContainer;
        this.hContainer = hContainer;
        
    }
    
    public void paint(Graphics g){
        
        Area cropArea = new Area();
        Area croppedTriangle = new Area(this.a.toPolygon());
            
        for(int i = 0; i < this.polys.size(); i++){
           
                Area curPolyArea = new Area(this.polys.get(i).toPolygon());
                cropArea.add(curPolyArea);
                
        }
        croppedTriangle.subtract(cropArea);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.white);    
        g2d.scale(0.4,0.4);
        g2d.translate(this.wContainer-this.a.getWidth(),this.hContainer-this.a.getHeight()/1.25);
        
        g2d.rotate(Math.toRadians(-30),croppedTriangle.getBounds2D().getMaxX(),croppedTriangle.getBounds2D().getMaxY());
        g2d.fill(croppedTriangle);
        Shape r2 = mirrorAlongX(croppedTriangle.getBounds2D().getMaxX(),croppedTriangle);
        g2d.fill(r2);
        Shape r5 = mirrorAlongX(croppedTriangle.getBounds2D().getMaxX(),croppedTriangle);
        g2d.rotate(Math.toRadians(60),r5.getBounds2D().getMinX(),r5.getBounds2D().getMaxY());
        g2d.fill(r5);     
        Shape r6 = mirrorAlongX(r5.getBounds2D().getMinX(),r5);
        g2d.fill(r6);
        Shape r7 = mirrorAlongY(r6.getBounds2D().getMaxY(),r6);
        g2d.fill(r7);
        Shape r8 = mirrorAlongX(r7.getBounds2D().getMaxX(),r7);
        g2d.fill(r8);         
        Shape r9 = mirrorAlongX(r2.getBounds2D().getMaxX(),r8);
        g2d.rotate(Math.toRadians(-60),r9.getBounds2D().getMinX(),r9.getBounds2D().getMaxY());
        g2d.fill(r9);
        Shape r10 = mirrorAlongX(r9.getBounds2D().getMaxX(),r9);
        g2d.fill(r10);
        Shape r11 = mirrorAlongX(r10.getBounds2D().getMaxX(),r10);
        g2d.rotate(Math.toRadians(-60),r11.getBounds2D().getMinX(),r11.getBounds2D().getMaxY());
        g2d.fill(r11);
        Shape r12 = mirrorAlongX(r11.getBounds2D().getMaxX(),r11);
        g2d.fill(r12);
        Shape r13 = mirrorAlongY(r12.getBounds2D().getMinY(),r12);
        g2d.fill(r13);
        Shape r14 = mirrorAlongX(r13.getBounds2D().getMinX(),r13);
        g2d.fill(r14);
    }
    
    /**
     * Specchia nell'asse X una forma (Shape)
     * @param x Punto in cui bisogna traslare la nuova forma specchiata.
     * @param shape La forma da specchiare.
     * @return 
     */
    private static Shape mirrorAlongX(double x, Shape shape)
    {
        AffineTransform at = new AffineTransform();
        at.translate(x, 0);
        at.scale(-1, 1);
        at.translate(-x, 0);
        return at.createTransformedShape(shape);
    }
    
    /**
     * Specchia nell'asse Y una forma (Shape)
     * @param y Punto in cui bisogna traslare la nuova forma specchiata.
     * @param shape La forma da specchiare.
     * @return 
     */
    private static Shape mirrorAlongY(double y, Shape shape)
    {
        AffineTransform at = new AffineTransform();
        at.translate(0, y);
        at.scale(1,-1);
        at.translate(0,-y);
        return at.createTransformedShape(shape);
    }
    
}
