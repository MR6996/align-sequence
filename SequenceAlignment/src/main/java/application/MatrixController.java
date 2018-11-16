package application;

import algorithms.Cell;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class MatrixController {

	private int n;
	private int m;
	private String a;
	private String b;
	private Cell[][] pMatrix;

	public MatrixController(Cell[][] pMatrix, String a, String b) {
		this.pMatrix = pMatrix;
		this.a = a;
		this.b = b;
		this.n = a.length();
		this.m = b.length();
	}

	@FXML
	private ScrollPane matrixScollPane;

	@FXML
	private GridPane matrixPane;

	@FXML
	private ScrollPane aScrollPane;

	@FXML
	private GridPane sequenceAPane;

	@FXML
	private ScrollPane bScrollPane;

	@FXML
	private GridPane sequenceBPane;

	@FXML
	void initialize() {
		bScrollPane.hvalueProperty().bindBidirectional(matrixScollPane.hvalueProperty());
		aScrollPane.vvalueProperty().bindBidirectional(matrixScollPane.vvalueProperty());

		for (int i = 0; i < m; i++) {
			Label bHeaderLabel = new Label(Character.toString(b.charAt(i)).toUpperCase());
			bHeaderLabel.setId("matrix-header-label");
			sequenceBPane.add(bHeaderLabel, i, 0);

			for (int j = 0; j < n; j++) {
				Label scoreLabel = new Label(Float.toString(pMatrix[j][i].getScore()));
				scoreLabel.setId("matrix-element-label");
				matrixPane.add(scoreLabel, i, j);

			}
		}

		for (int j = 0; j < n; j++) {
			Label aHeaderLabel = new Label(Character.toString(a.charAt(j)).toUpperCase());
			aHeaderLabel.setId("matrix-header-label");
			sequenceAPane.add(aHeaderLabel, 0, j);
		}
	}

}
