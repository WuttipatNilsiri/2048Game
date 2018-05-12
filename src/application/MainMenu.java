package application;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {

	@FXML
	Button play;
	@FXML
	Button leaderBoard;
	@FXML
	Button exit;
	
	@FXML
	public void initialize() {
		System.out.println("Running initialize");
	}
	
	public void handlePlay(ActionEvent event) {
		Stage stage = new Stage();
		try {
			new GameController(stage).startGame();
			((Node)(event.getSource())).getScene().getWindow().hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleLeaderBoard(ActionEvent event) {
        try {
        	Parent root = (Parent) FXMLLoader.load(getClass().getResource("LeaderBoardUI.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
			((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch(Exception e) {
			System.out.println("Exception creating scene: " + e.getMessage());
		}
	}

	public void handleExit(ActionEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
	}

}
