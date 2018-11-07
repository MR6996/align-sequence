package application;

import algorithms.Cell;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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
    
    @FXML
    void initialize() {
    	drawMatrix();
    }
    
    private void drawMatrix() {
    	matrixCanvas.setWidth(SQUARE_SIZE*m);
    	matrixCanvas.setHeight(SQUARE_SIZE*n);
    	
    	GraphicsContext gfCtx = matrixCanvas.getGraphicsContext2D();
    	gfCtx.setTextAlign(TextAlignment.CENTER);
    	gfCtx.setTextBaseline(VPos.CENTER);
    	
    	for(int i = 0; i < n; i++)
    		for(int j = 0; j < m; j++) {
    			double value = 0.6*pMatrix[i][j].getScore()/maxScore + 0.4;
    			gfCtx.setFill(Color.hsb(196, 0.5, value));
    			gfCtx.fillRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
    			gfCtx.setFill(Color.BLACK);
    			gfCtx.fillText(Float.toString(pMatrix[i][j].getScore()), SQUARE_SIZE*(j+0.5), SQUARE_SIZE*(i+0.5));
    		}
    	
    }
    

    
}
