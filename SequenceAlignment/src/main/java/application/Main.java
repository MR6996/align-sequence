package application;

import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AlignSequence.fxml"));
		
		stage.setTitle("Sequence alignment");
		stage.setScene(new Scene(root));
		stage.show();
	}

}
