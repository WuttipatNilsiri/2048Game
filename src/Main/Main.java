package Main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{

	GameBoard board=new GameBoard();
	boolean stop=true;

	public Main() {
		final Agent agent = new MonteCarloAI();

		setFocusable(true);

		JPanel topPanel = new JPanel();
		final JButton hint = new JButton("Need Help?");
		
		hint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int action=agent.makeMove(board);
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
									board.setState(State.over);
								}
								repaint();
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

		addKeyListener(new KeyAdapter(){

			@Override
			public void keyPressed(KeyEvent e) {
				if (board.getState() == State.running) {
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
				
				repaint();
				
				if(board.getPossibleMoves().isEmpty()) {
					board.setState(State.over);
					repaint();
				}
			
			}
		});
		
		add(topPanel, BorderLayout.NORTH);
		add(new GameBoardUI(board));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

	}

	public void paint(Graphics g){
		setTitle("2048 - Score "+board.score);
		super.paint(g);
	}

	public static void main(String[] args){
		new Main();
	}
}
