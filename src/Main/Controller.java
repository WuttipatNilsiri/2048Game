package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;




import AI.Agent;
import AI.MonteCarloAI;

import MODEL.GameBoard;
import MODEL.State;
import SERVER.GameClient;
import UI.GameBoardUI;


public class Controller extends JFrame{
	

	GameBoard board = new GameBoard();
	boolean stop = true;
	JTextField scoreView;
	
	List<String> scoreLog = new ArrayList<String>();
	
	public boolean isSend = false;
	
	public Controller() throws IOException {
		
		GameBoardUI gameBoard = new GameBoardUI(board);
		gameBoard.addCollroller(this);
		
		GameClient gc = new GameClient();
		
		gc.connect("127.0.0.1", 54334);
		
		final Agent agent =	 new MonteCarloAI();
		setFocusable(true);
		setResizable(false);
		JPanel topPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		scoreView = new JTextField();
		JTextField scoreLabel = new JTextField();
		scoreLabel.setText("Score");
		scoreLabel.setEditable(false);
		scoreView.setHorizontalAlignment(JTextField.RIGHT);
		scoreView.setPreferredSize( new Dimension( 100, 24 ) );
		scoreView.setEditable(false);
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreView);
		JButton scoreSendButton = new JButton("Send");
		JTextField _name = new JTextField();
		_name.setPreferredSize( new Dimension( 100, 24 ) );
		
		_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					_name.setText("");
					requestFocus();
				}	
			}
		});
		
		scoreSendButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isSend) {
					String name = _name.getText();
					gc.sendMessage(name+" "+board.score);
					if(board.getState().equals(State.over)) {
						isSend = true;
					}
				}
				requestFocus();
			}
		});
		scorePanel.add(_name);
		scorePanel.add(scoreSendButton);
		final JButton hint = new JButton("Need Help?");
		final JButton scoreBoard = new JButton("Score Board");
		
		scoreBoard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				 gc.sendMessage("reqscore");
				 requestFocus();
			}
		});		
		
		

		hint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int action = agent.makeMove(board);
				hint.setText("Hint: "+GameBoard.NAMES[action]);
				requestFocus();
			}
		});
		
		final JButton ai = new JButton("AI SOLVE");
		
		ai.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stop){
					stop = false;
					new Thread(new Runnable(){
						@Override
						public void run() {
							while(!stop){
								try {
									int r = agent.makeMove(board);
									board.makeMove(r);
									board.generateRandomly();
								} catch (Exception e) {
									Thread.currentThread().interrupt();
									stop = true;
									requestFocus();
									hint.setEnabled(true);
									board.setState(State.over);
								} finally {
									repaint();
								}
							}
						}
					}).start();
					
					hint.setEnabled(false);
				}
				else {
					stop = true;
					requestFocus();
					hint.setEnabled(true);
				}
			}
		});
		
		topPanel.add(hint);
		topPanel.add(ai);
		topPanel.add(scoreBoard);
		
		
		gameBoard.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
					requestFocus();
			    }
		});
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (board.getState() == State.running) {
					if(board.getPossibleMoves().isEmpty()) {
						board.setState(State.over);
					}
					else {
						int action = -1;
						switch(e.getKeyCode()){
						case KeyEvent.VK_LEFT:
							action = GameBoard.LEFT;
							break;
						case KeyEvent.VK_RIGHT:
							action = GameBoard.RIGHT;
							break;
						case KeyEvent.VK_UP:
							action = GameBoard.UP;
							break;
						case KeyEvent.VK_DOWN:
							action = GameBoard.DOWN;
							break;
						default:
							return;
						}

						if(board.canDo(action)){
							board.makeMove(action);
							board.generateRandomly();
							hint.setText("HINT");
						}
					}
				}
				repaint();

			}
		});
		
		add(topPanel, BorderLayout.NORTH);
		add(scorePanel,BorderLayout.SOUTH);
		add(gameBoard);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

	}
	
	
	public void paint(Graphics g){
		setTitle("2048 - Score "+board.score);
		scoreView.setText(""+board.score);
		super.paint(g);
	}

	public static void main(String[] args){
	
		try {
			Controller ctrl = new Controller();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}