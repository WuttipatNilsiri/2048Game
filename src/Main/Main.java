package Main;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SERVER.ScoreServer;

public class Main {
	public static void main(String[] a) {
		
		try {
			ScoreServer _sv = new ScoreServer();
			_sv.start(54334);
		} catch (IOException e) {
			JFrame error = new JFrame("ERROR");
			JPanel text = new JPanel();
			JLabel textlable = new JLabel();
			textlable.setText(e.getMessage());
			textlable.setPreferredSize(new Dimension(250, 50));
			text.add(textlable);
			error.add(text);
			error.setVisible(true);
			error.pack();
		}
		
		Controller ctrl = new Controller();
		
	}
}
