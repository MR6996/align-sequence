package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import algorithms.Alignment;
import algorithms.AlignmentAlgorithm;
import algorithms.NeedelemanWunsch;
import algorithms.SmithWaterman;
import algorithms.SubstitutionMatrix;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private TextArea sequenceATextArea;

	@FXML
	private TextArea sequenceBTextArea;

	@FXML
	private ImageView uploadAButton;

	@FXML
	private ImageView uploadBButton;

	@FXML
	private Button alignButton;

	@FXML
	private Button resetButton;

	@FXML
	private TextArea outputTextArea;

	@FXML
	private ImageView showMatrixButton;

	@FXML
	private ImageView copyResultButton;

	@FXML
	private RadioButton globalAlignmentToggle;

	@FXML
	private ToggleGroup Alignment_type;

	@FXML
	private RadioButton localAlignmentToggle;

	@FXML
	private TextField gepTextField;

	@FXML
	private TextField gopTextField;

	@FXML
	private ChoiceBox<String> sostMatChoiceBox;

	@FXML
	private CheckBox thresholdCheck;

	@FXML
	private Label thresholdLabel;

	@FXML
	private TextField thresholdValue;

	@FXML
	private TextField maxAlignmentValue;

	@FXML
	private Label maxScoresLabel;

	@FXML
	private TextField maxScoresValue;

	private AlignmentAlgorithm algorithm;

	@FXML
	void initialize() {

		algorithm = null;

		try {
			String resFolderPath = getClass().getResource("/sm/").getFile();
			File resFolder = new File(URLDecoder.decode(resFolderPath, "UTF-8"));

			for (File f : resFolder.listFiles())
				sostMatChoiceBox.getItems().add(f.getName());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		thresholdCheck.disableProperty().bind(globalAlignmentToggle.selectedProperty());
		thresholdLabel.disableProperty()
				.bind(Bindings.or(globalAlignmentToggle.selectedProperty(), thresholdCheck.selectedProperty().not()));
		thresholdValue.disableProperty()
				.bind(Bindings.or(globalAlignmentToggle.selectedProperty(), thresholdCheck.selectedProperty().not()));
		maxScoresLabel.disableProperty()
				.bind(Bindings.or(globalAlignmentToggle.selectedProperty(), thresholdCheck.selectedProperty()));
		maxScoresValue.disableProperty()
				.bind(Bindings.or(globalAlignmentToggle.selectedProperty(), thresholdCheck.selectedProperty()));

	}

	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void align(MouseEvent event) {

		String a = sequenceATextArea.getText().toLowerCase();
		String b = sequenceBTextArea.getText().toLowerCase();

		if (a.length() == 0 || b.length() == 0) {
			showSimpleErrorMessage("No void sequence are allowed!");
			return;
		}

		int gop = Integer.parseInt(gopTextField.getText());
		float gep = Float.parseFloat(gepTextField.getText());
		int maxAlignment = Integer.parseInt(maxAlignmentValue.getText());
		
		try {
			SubstitutionMatrix sMatrix = SubstitutionMatrix
					.load(getClass().getResource("/sm/" + sostMatChoiceBox.getValue()));

			if (Alignment_type.getSelectedToggle().equals(globalAlignmentToggle))
				algorithm = new NeedelemanWunsch(a, b, gop, gep, sMatrix);
			else if (thresholdCheck.isSelected()) {
				float threshold = Float.parseFloat(thresholdValue.getText());
				algorithm = new SmithWaterman(a, b, gop, gep, sMatrix, threshold);
			} else {
				int k = Integer.parseInt(maxScoresValue.getText());
				algorithm = new SmithWaterman(a, b, gop, gep, sMatrix, k);
			}
			
			
			List<Alignment> resultAligment = algorithm.getAlignments(maxAlignment);
			StringBuilder resultBuilder = new StringBuilder("Found " + resultAligment.size() + " alignments.");
			int i = 0;
			for (Alignment am : resultAligment)
				resultBuilder.append("\n\n[" + (i++) + "]: \n" + am);

			outputTextArea.setText(resultBuilder.toString());

		} catch (IllegalArgumentException e) {
			showSimpleErrorMessage("Invalid format for the sostitution matrix choiced!");
		} catch (IOException e) {
			showSimpleErrorMessage("Disk error while loading the sostitution matrix!");
		}
	}

	private void showSimpleErrorMessage(String msg) {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(msg);
		a.show();
	}

	@FXML
	void loadSequence(MouseEvent event) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select sequence file...");
		chooser.setSelectedExtensionFilter(new ExtensionFilter("Text file", ".txt"));
		File seqFile = chooser.showOpenDialog(uploadAButton.getScene().getWindow());

		try {
			if (seqFile != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(seqFile)));
				if (((ImageView) event.getSource()).equals(uploadAButton))
					sequenceATextArea.setText(reader.readLine());
				else if (((ImageView) event.getSource()).equals(uploadBButton))
					sequenceBTextArea.setText(reader.readLine());

				reader.close();
			}
		} catch (IOException e) {
			showSimpleErrorMessage("Disk error while loading file " + seqFile.getName());
		}

	}

	@FXML
	void showMatrix(MouseEvent event) {
		if (algorithm != null) {
			try {
				Stage matrixStage = new Stage();

				FXMLLoader loader = new FXMLLoader(getClass().getResource("MatrixApplication.fxml"));
				loader.setController(
						new MatrixController(algorithm.getPairingMatrix(), algorithm.getA(), algorithm.getB()));
				Parent root = loader.load();

				matrixStage.setTitle("Alignment matrix");
				matrixStage.setScene(new Scene(root));
				matrixStage.show();
			} catch (IOException e) {
				showSimpleErrorMessage("Can't load resource file for creating new window!");
			}
		} else
			showSimpleErrorMessage("No sequences have been aligned yet!");
	}

	@FXML
	void reset(MouseEvent event) {
		sequenceATextArea.clear();
		sequenceBTextArea.clear();
	}

}
