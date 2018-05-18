package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AI.AI;
import AI.MonteCarlo;


import MODEL.GameBoard;
import MODEL.State;
import SERVER.GameClient;
import UI.GameBoardUI;

import UI.LeaderBoardUI;


public class Controller extends JFrame{
	

	private GameBoard board = new GameBoard();
	private boolean stop = true;
	private JTextField scoreView;

	
//	List<String> scoreLog = new ArrayList<String>();
	private LeaderBoardUI ld;
	
	private GameClient  gc;
	
	public boolean isSend = false;
	/**
	 * initial Controller 
	 */
	public Controller() {
		
		GameBoardUI gameBoard = new GameBoardUI(board);
		gameBoard.addCollroller(this);
		
		ld = new LeaderBoardUI();
		gc = new GameClient();
		ld.init();
		
		final AI ai = new AI(new MonteCarlo());
		
		setFocusable(true);
		setResizable(false);
		
		JPanel topPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		scoreView = new JTextField();
		JLabel scoreLabel = new JLabel();
		scoreLabel.setText("Score");

		scoreView.setHorizontalAlignment(JTextField.RIGHT);
		scoreView.setPreferredSize( new Dimension( 100, 24 ) );
		scoreView.setEditable(false);
		
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreView);
		
		JButton scoreSendButton = new JButton("Send");
		scoreSendButton.setEnabled(false);
		
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
				gc.sendMessage("reqscorelist");
				try {
					Thread.sleep(500);
					//Wait for Score Arrive
				} catch (InterruptedException e1) {
					JFrame error = new JFrame("ERROR");
					JPanel text = new JPanel();
					JLabel textlable = new JLabel();
					textlable.setText(e1.getMessage());
					textlable.setPreferredSize(new Dimension(250, 50));
					text.add(textlable);
					error.add(text);
					error.setVisible(true);
					error.pack();
				}
				List<String> list = gc.getScoreList();	
				ld.addAll(list);
				requestFocus();
				
			}
		});
		
		scorePanel.add(_name);
		scorePanel.add(scoreSendButton);
		
		final JButton hint = new JButton("Need Help?");
		
		final JButton scoreBoard = new JButton("Score Board");		
		scoreBoard.setEnabled(false);
		scoreBoard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				ld.setVisible(true);

			}
		});		
		
		hint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int action = ai.makeMove(board);
				hint.setText("Hint: "+GameBoard.NAMES[action]);
				requestFocus();
			}
		});
		
		final JButton airun = new JButton("AI SOLVE");
		
		airun.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stop){
					stop = false;
					new Thread(new Runnable(){
						@Override
						public void run() {
							while(!stop){
								try {
									int r = ai.makeMove(board);
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
		topPanel.add(airun);
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
		JPanel ippanel = new JPanel();
		JTextField iptext = new JTextField();
		JTextField connectResult = new JTextField();
		connectResult.setText("");
		connectResult.setPreferredSize( new Dimension( 100, 24 ) );
		connectResult.setEditable(false);
		iptext.setPreferredSize( new Dimension( 100, 24 ) );
		
		JButton connect = new JButton("Connect");
		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					gc.connect(iptext.getText(), 54334);
					connectResult.setText("OK");
					scoreBoard.setEnabled(true);
					scoreSendButton.setEnabled(true);
					gc.sendMessage("reqscorelist");
					try {
						Thread.sleep(500);
						//Wait for Score Arrive
					} catch (InterruptedException e1) {
						JFrame error = new JFrame("ERROR");
						JPanel text = new JPanel();
						JLabel textlable = new JLabel();
						textlable.setText(e1.getMessage());
						textlable.setPreferredSize(new Dimension(250, 50));
						text.add(textlable);
						error.add(text);
						error.setVisible(true);
						error.pack();
					}
					List<String> list = gc.getScoreList();	
					ld.addAll(list);
				}
				catch (IOException ex)  {
					JFrame error = new JFrame("ERROR");
					JPanel text = new JPanel();
					JLabel textlable = new JLabel();
					textlable.setText(ex.getMessage());
					textlable.setPreferredSize(new Dimension(250, 50));
					text.add(textlable);
					error.add(text);
					error.setVisible(true);
					error.pack();
					scoreBoard.setEnabled(false);
					scoreSendButton.setEnabled(false);
				}
				requestFocus();
			}
			
		});
		
		ippanel.add(iptext);
		ippanel.add(connect);
		ippanel.add(connectResult,BorderLayout.SOUTH);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(100, 100));
		south.add(scorePanel,BorderLayout.NORTH);
		south.add(ippanel, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		add(gameBoard);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

	}
	
	/**
	 * repaint Controller
	 */
	public void paint(Graphics g){
		setTitle("2048 - Score "+board.score);
		scoreView.setText(""+board.score);
		super.paint(g);
	}
	
	

	public static void main(String[] args){
		Controller ctrl = new Controller();
	}
	
}