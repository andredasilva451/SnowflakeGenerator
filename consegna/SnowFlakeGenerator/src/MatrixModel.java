/**
 * Questa classe rappresenta un modello astratto
 * di una matrice proporzionale.
 * @author Andr Da Silva
 * @version 13.04.19
*/

public class MatrixModel {
	
	
	/**
	 * Numero di righe della matrice. 
	*/
	private int rows;
	
	/**
	 * Valore della colonna piu grande della matrice. 
	*/
	private int cols;
	
	/**
	 * Margine interno. 
	*/
	private int margin;
	
	/**
	 * Altezza del contenitore. 
	*/
	private int hContainer;
	
	/**
	 * Larghezza del contenitore. 
	*/
	private int wContainer;
	
	/**
	 * Proporzione X della matrice.
	*/
	private double ratioX;
	
	/**
	 * Proporzione Y della matrice.
	*/
	private double ratioY;
	
	/**
	 * Costruttore per instanziare un oggetto di tipo MatrixModel.
	 * @param rows Numero di righe della matrice.
	 * @param cols Valore della colonna piu grande della matrice.
	 * @param margin Margine interno.
	 * @param hContainer Altezza contenitore.
	 * @param wContainer Larghezza contenitore.
	 * @param ratioX Proporzione X della matrice.
	 * @param ratioY Proporzione Y della matrice.
	*/
	public MatrixModel(int rows,int cols,int margin, int hContainer, int wContainer, double ratioX, double ratioY){
		
		this.rows = rows;
		this.cols = cols;
		this.margin = margin;
		this.hContainer = hContainer;
		this.wContainer = wContainer;
		this.ratioX = ratioX;
		this.ratioY = ratioY;
		this.hContainer -= (this.margin*2);
		this.wContainer -= (this.margin*2);
		
	}
	
	/**
	 * Ritorna un array contenente la larghezza e l'altezza di una cella della matrice (0 = larghezza, 1 = altezza). 
	 * @return cs array contenente larghezza e altezza.
	*/
	public double[] getCellSize(){
		
            double[] cs = new double[2];
            
            if (this.wContainer / this.hContainer  > (this.cols * this.ratioX) / (this.rows * this.ratioY)) {
                    
                cs[1] = this.hContainer / this.rows;
                cs[0] = cs[1] * this.ratioX / this.ratioY;
                      
            }else{
                
                cs[0] = this.wContainer / this.rows;
                cs[1] = cs[0] * this.ratioY / this.ratioX;
                
            }
            return cs;
	}
	
	/**
	 * Ritorna un array contenente le coordinate X e Y affinche la matrice
	 * sia centrata (0 = x, 1 = y). 
	 * @return dxy array contenente le coordinate X e Y per il centramento.
	*/
	public double[] getDXYSize(){
		
		double[] dxy = new double[2]; //0 = x, 1 = y
		
                if (this.wContainer / this.hContainer  > (this.cols * this.ratioX) / (this.rows * this.ratioY)) {
                    
                    dxy[1] = this.margin;
                    int width = (int)this.getCellSize()[0] * this.cols;
                    dxy[0] = (this.wContainer - width) / 2;
                    
                }else{
                
                    dxy[0] = this.margin;
                    int height = (int)this.getCellSize()[1] * this.rows;
                    dxy[1] = (this.hContainer - height) / 2;
                    
                }                
		return dxy;
	}
}