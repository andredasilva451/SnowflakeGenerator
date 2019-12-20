import java.util.List;

/**
 * Listener per passare la lista di CropPolygon
 * al previewPanel
 * @author André Da Silva
 * @version 14.12.19
 */
public interface SnowFlakePanelListener {
    
    public void polygonCreated(List<CropPolygon> cp);
}
