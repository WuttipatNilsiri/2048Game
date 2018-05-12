package application;

import java.io.IOException;
import java.util.List;

import SERVER.GameClient;
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
	ListView<String> listView;
	
	
	
	GameClient gc = MainMenu.getClient(); 
	
	
	
	@FXML
	public void initialize() {
		System.out.println("leader initialize");
		List<String> ls = gc.getScoreList();
		System.out.println(ls.isEmpty());
		for (String s : ls) {
			System.out.println(s);
			listView.getItems().add(s);
		}
		System.out.println("---------------------------");
		
//		System.out.println("asdsdLeaderBoard gc address : "+gc.toString());
//		gc.sendMessage("REQSCORELIST");
//		for (String s : gc.getScoreList()) {
//			System.out.println(s);
//		}
//		gc.sendMessage("REQSCORE");
		
		
//		listView.getItems().addAll(gc.getScoreList());
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
	
//	public void addGameClient(GameClient gc) {
//    	this.gc = gc;
//    	System.out.println("LeaderBoard gc address : "+gc.toString());
//    }
}