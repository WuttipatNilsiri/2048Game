package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class LeaderBoard {

	@FXML
	Button back;
	@FXML
	ListView listView;
	
	@FXML
	public void initialize() {
		
	}
	
	public void handleBack(ActionEvent event) {
		try {
			Stage stage = new Stage();
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
			((Node)(event.getSource())).getScene().getWindow().hide();
		} catch(Exception e) {
			System.out.println("Exception creating scene: " + e.getMessage());
		}
	}
	
	public void handleBoard(ActionEvent event) {
		System.out.println("leader initialize");
	}
}
