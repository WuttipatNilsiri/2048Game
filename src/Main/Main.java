package Main;

import java.io.IOException;

import SERVER.ScoreServer;

public class Main {
	public static void main(String[] a) {
		
		try {
			ScoreServer _sv = new ScoreServer();
			_sv.start(54334);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Controller ctrl = new Controller();
		
	}
}
