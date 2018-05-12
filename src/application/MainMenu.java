package application;

import java.io.IOException;

import Main.Controller;
import SERVER.ScoreServer;
import javafx.event.ActionEvent;

import java.io.IOException;

import SERVER.GameClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {
	
	Stage stage = new Stage();
	GameController gameController = new GameController(stage);
	LeaderBoard ld = new LeaderBoard();
	
	static GameClient gc;

	@FXML
	Button play;
	@FXML
	Button leaderBoard;
	@FXML
	Button exit;
	
	
	
	@FXML
	public void initialize() {
		System.out.println("Running initialize");
		try {
			if (gc == null) {
				gc = new GameClient();
				gc.connect("127.0.0.1", 54334);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(gc.toString());
		gameController.addGameClient(gc);
//		ld.addGameClient(gc);
		
	}
	
	public void handlePlay(ActionEvent event) {
		
		try {
			ScoreServer _sv = new ScoreServer();
			_sv.start(54334);
			new GameController(stage).startGame();
			((Node)(event.getSource())).getScene().getWindow().hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleLeaderBoard(ActionEvent event) {
		gc.sendMessage("REQSCORELIST");
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
	
	public static GameClient getClient() {
		return gc;
	}

}