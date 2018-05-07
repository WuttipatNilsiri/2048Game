package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gui.GuiScreen;
import gui.LeaderBoardPanel;
import gui.MainMenuPanel;
import gui.PlayPanel;

public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = GameBoard.BOARD_WIDTH + 40;
	public static final int HEIGHT = 630;
	public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//	private GameBoard gb;
		
	//GUI
	private GuiScreen screen;
	
	public Game() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
//		gb = new GameBoard((WIDTH/2) - (GameBoard.BOARD_WIDTH/2), HEIGHT - (GameBoard.BOARD_HEIGHT - 10));
		
		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel());
		screen.add("Leaderboards", new LeaderBoardPanel());
		screen.setCurrentPanel("Menu");
	}

	public void update() {
//		gb.update();
		screen.update();
		Keyboard.update();
	}
	
	public void render() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
//		gb.render(g);
		screen.render(g);
		g.dispose();
		
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}
	
	@Override
	public void run() {
		int fps = 0, update = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0/60; 
		double then = System.nanoTime();
		double unprocessed = 0;
		
		while (running) {
			boolean shouldRender = false;
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			
			while (unprocessed >= 1) {
				update++;
				update();
				unprocessed--;
				shouldRender = true;
			}
			
			if (shouldRender) {
				fps++;
				render();
				shouldRender = false;
			}
			else {
				try {
					Thread.sleep(1);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (System.currentTimeMillis() - fpsTimer > 1000) {
				System.out.printf("%d fps %d update", fps, update);
				System.out.println();
				fps = 0;
				update = 0;
				fpsTimer += 1000;
			}
		}
		
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	}
	
	public synchronized void stop() {
		if (running) return;
		running = false;
		System.exit(0);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		Keyboard.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Keyboard.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
	}
	
}
