package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import MODEL.GameBoard;
import MODEL.State;
import MODEL.Tile;
import Main.Controller;
import SERVER.GameClient;
import application.Config;
import application.GameController;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameBoardUI extends JComponent{

	TileUI[][] tiles = new TileUI[4][4];
	
	Controller ctrl;
	private final Pane boardP = new Pane();
	private boolean sawEndScreen = false;
	GameBoard board;
	GameClient gc = new GameClient();

	Color[] color = {
			new Color(204, 192, 179),
			new Color(0xEEE4DA),
			new Color(0xede0c8),
			new Color(0xf2b179),
			new Color(0xf59563)
	};

	static final Color FONT_COLOR = new Color(0x806E85);
	
	public void addCollroller(Controller c) {
		ctrl = c;
	}

	public GameBoardUI(GameBoard b)
	{
		board = b;

		for(int x = 0; x < 4; x++)
		{
			for(int y = 0; y < 4; y++)
			{
				//locallity
				tiles[y][x] = new TileUI(new Tile(0));
				tiles[y][x].tilePos(x, y);
				add(tiles[y][x]);
			}
		}

	}

	public Dimension getPreferredSize(){
		return new Dimension(400, 400);
	}

	public void paintComponent(Graphics g){
		if (board.getState() == State.running){
			
			for(int x = 0; x < 4; x++){
				for(int y = 0; y < 4; y++){
					tiles[y][x].tile.pow = board.getTile(x, y);
				}
			}
			
			g.setColor(new Color(0xbbada0));
			g.fillRect(0, 0, 400, 400);
		}
		if (board.getState() == State.over) {
			JFrame frame = new JFrame("GAME OVER :)");

			frame.setPreferredSize(new Dimension(200, 75));
			final JButton retry = new JButton("Try Again");
		
			final JButton quit = new JButton("quit");
			quit.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(1);

				}
			});	
			retry.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						board.startGame();
						ctrl.isSend = false;
						repaint();
						ctrl.repaint();
						frame.setVisible(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			frame.add(quit, BorderLayout.EAST);
			frame.add(retry, BorderLayout.WEST);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setResizable(false);

			frame.setVisible(true);
		}
	}
}
