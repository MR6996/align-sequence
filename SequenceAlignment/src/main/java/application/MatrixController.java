package application;

import algorithms.Cell;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MatrixController {

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
	private Slider scaleSlider;

	@FXML
	void initialize() {
		drawMatrix();

		matrixCanvas.scaleXProperty().bind(scaleSlider.valueProperty().divide(100));
		matrixCanvas.scaleYProperty().bind(scaleSlider.valueProperty().divide(100));

	}

	private void drawMatrix() {
		double width = SQUARE_SIZE * m, height = SQUARE_SIZE * n;

		matrixCanvas.setWidth(width);
		matrixCanvas.setHeight(height);

		GraphicsContext gfCtx = matrixCanvas.getGraphicsContext2D();
		gfCtx.setTextAlign(TextAlignment.CENTER);
		gfCtx.setTextBaseline(VPos.CENTER);

		for (int i = 0; i < n; i++) {
			gfCtx.setStroke(Color.BLACK);
			gfCtx.strokeLine(0, SQUARE_SIZE * i, width, SQUARE_SIZE * i);

			for (int j = 0; j < m; j++) {
				double value = 0.6 * pMatrix[i][j].getScore() / maxScore + 0.4;

				gfCtx.setFill(Color.hsb(196, 0.5, value));
				gfCtx.fillRect(SQUARE_SIZE * j, SQUARE_SIZE * i, SQUARE_SIZE, SQUARE_SIZE);
				gfCtx.setFill(Color.BLACK);
				gfCtx.fillText(Float.toString(pMatrix[i][j].getScore()), SQUARE_SIZE * (j + 0.5),
						SQUARE_SIZE * (i + 0.5));
				
				gfCtx.setStroke(Color.RED);
				if(pMatrix[i][j].getDiagonal() != null) 
					gfCtx.strokeLine(SQUARE_SIZE * j-7.5, SQUARE_SIZE * i-7.5, SQUARE_SIZE * j+7.5, SQUARE_SIZE * i+7.5);

				if(pMatrix[i][j].getLeft() != null) 
					gfCtx.strokeLine(SQUARE_SIZE * j-7.5, SQUARE_SIZE * i+20, SQUARE_SIZE * j+7.5, SQUARE_SIZE * i+20);
				

				if(pMatrix[i][j].getUp() != null) 
					gfCtx.strokeLine(SQUARE_SIZE * j+20, SQUARE_SIZE * i-7.5, SQUARE_SIZE * j+20, SQUARE_SIZE * i+7.5);
			}
		}


		gfCtx.setStroke(Color.BLACK);
		gfCtx.strokeLine(0, SQUARE_SIZE * n, width, SQUARE_SIZE * n);
		for (int i = 0; i <= m; i++) 
			gfCtx.strokeLine(SQUARE_SIZE * i, 0, SQUARE_SIZE * i, height);
		
	}

}
