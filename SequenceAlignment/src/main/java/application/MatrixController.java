package application;

import java.util.ArrayList;
import java.util.List;

import algorithms.Cell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MatrixController {

	private int n;
	private int m;
	private String a;
	private String b;
	private Cell[][] pMatrix;

	private Node selectedNode;
	private Node aHeaderSelctedNode;
	private Node bHeaderSelctedNode;
	private List<Node> traceSelectedNodes;

	private EventHandler<MouseEvent> selectionHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			Node target = (Node) event.getTarget();

			if (target != matrixPane) {
				Node parent;
				while ((parent = target.getParent()) != matrixPane)
					target = parent;
			}

			Integer i = GridPane.getColumnIndex(target), j = GridPane.getRowIndex(target);
			if (i != null && j != null)
				updateSelection(target, i, j);

		}

		private void updateSelection(Node target, int i, int j) {
			if (selectedNode != null) {
				selectedNode.setId("matrix-element-label");
				aHeaderSelctedNode.setId("matrix-header-label");
				bHeaderSelctedNode.setId("matrix-header-label");

				for (Node n : traceSelectedNodes)
					n.setId("matrix-element-label");

				traceSelectedNodes.clear();
			}

			selectedNode = target;
			aHeaderSelctedNode = sequenceAPane.getChildren().get(j);
			bHeaderSelctedNode = sequenceBPane.getChildren().get(i);

			if (pMatrix[j][i].getLeft() != null) 
				traceSelectedNodes.add(matrixPane.getChildren().get(j + n * (i - 1)));

			if (pMatrix[j][i].getUp() != null) 
				traceSelectedNodes.add(matrixPane.getChildren().get(j - 1 + n * i));

			if (pMatrix[j][i].getDiagonal() != null) 
				traceSelectedNodes.add(matrixPane.getChildren().get(j - 1 + n * (i - 1)));

			selectedNode.setId("matrix-element-label-selected");
			aHeaderSelctedNode.setId("matrix-header-label-selected");
			bHeaderSelctedNode.setId("matrix-header-label-selected");

			for (Node n : traceSelectedNodes)
				n.setId("matrix-element-label-trace");
		}

	};

	public MatrixController(Cell[][] pMatrix, String a, String b) {
		this.pMatrix = pMatrix;
		this.a = a;
		this.b = b;
		this.n = a.length();
		this.m = b.length();

		this.selectedNode = null;
		this.aHeaderSelctedNode = null;
		this.bHeaderSelctedNode = null;
		this.traceSelectedNodes = new ArrayList<>();
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

		matrixPane.setOnMouseClicked(selectionHandler);
	}
}
