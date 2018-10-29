package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import algorithms.NeedelemanWunsch;
import algorithms.SostitutionMatrix;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private ToggleGroup Alignment_type;

    @FXML
    private TextField gepTextField;

    @FXML
    private TextField gopTextField;

    @FXML
    private ChoiceBox<String> sostMatChoiceBox;

    @FXML
    private Button alignButton;

    @FXML
    private Button resetButton;

    @FXML
    private TextArea outputTextArea;
    
    @FXML
    void initialize() {
    	
		try {
			String resFolderPath = getClass().getResource("/sm/").getFile();
			File resFolder = new File(URLDecoder.decode(resFolderPath, "UTF-8"));

			for(File f : resFolder.listFiles()) 
				sostMatChoiceBox.getItems().add(f.getName());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println(Alignment_type.selectedToggleProperty().get());
		
	}
    
    @FXML
    void align(MouseEvent event) {
    	
    	String a = sequenceATextArea.getText().toLowerCase();
    	String b = sequenceBTextArea.getText().toLowerCase();
    	
    	if(a.length() == 0 || b.length() == 0) {
    		showSimpleErrorMessage("No void sequence are allowed!");
    		return;
    	}
    	
    	int gop = Integer.parseInt(gopTextField.getText());
    	float gep = Float.parseFloat(gepTextField.getText());
    	
    	try {
			SostitutionMatrix sMatrix = SostitutionMatrix.load(
					getClass().getResource("/sm/"+sostMatChoiceBox.getValue())
				);
			
	    	NeedelemanWunsch nw = new NeedelemanWunsch(a, b, gop, gep, sMatrix);
	    	
	    	StringBuilder resultBuilder = new StringBuilder("Result:");
	    	int i = 0;
	    	for(String alignment : nw.getAlignments())
	    		resultBuilder.append("\n\nAlignment: " + (i++) + ": \n" + alignment);
	    	
	    	outputTextArea.setText(resultBuilder.toString());
		} catch (IllegalArgumentException e) {
			showSimpleErrorMessage("Invalid format for the sostitution matrix choiced!");
		} catch (IOException e) {
			showSimpleErrorMessage("Disk error while loading the matrix!");
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
			if(seqFile != null) {
				BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream(seqFile)));
				if( ((ImageView)event.getSource()).equals(uploadAButton))
					sequenceATextArea.setText(reader.readLine());
				else if( ((ImageView)event.getSource()).equals(uploadBButton))
					sequenceBTextArea.setText(reader.readLine());
				
				reader.close();
			}
		} catch (IOException e) {
			showSimpleErrorMessage("Disk error while loading file " + seqFile.getName());
		}
    	
    }

    @FXML
    void reset(MouseEvent event) {
    	sequenceATextArea.clear();
    	sequenceBTextArea.clear();
    }
    
}
