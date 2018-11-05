package application;

import algorithms.Cell;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MatrixController  {
	
    private static final double SQUARE_SIZE = 40.0;
        
    private int n;
    private int m;
	private float maxScore;
    private Cell[][] pMatrix;

    
    public MatrixController(Cell[][] pMatrix, float maxScore) {
		this.pMatrix = pMatrix;
		this.maxScore = maxScore;
		this.n = pMatrix.length;
		this.m = pMatrix[0].length;
	}
    

	@FXML
    private Canvas matrixCanvas;
    
    private GraphicsContext gfCtx;

    @FXML
    void initialize() {
    	matrixCanvas.setWidth(SQUARE_SIZE*m);
    	matrixCanvas.setHeight(SQUARE_SIZE*n);
    	
    	gfCtx = matrixCanvas.getGraphicsContext2D();
    	
    	for(int i = 0; i < n; i++)
    		for(int j = 0; j < m; j++) {
    			double value = 0.6*pMatrix[i][j].getScore()/maxScore + 0.4;
    			gfCtx.setFill(Color.hsb(196, 0.5, value));
    			gfCtx.fillRect(SQUARE_SIZE*i, SQUARE_SIZE*j, SQUARE_SIZE, SQUARE_SIZE);
    		}
    	
   }

    
}
