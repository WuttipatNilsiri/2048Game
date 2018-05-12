package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.AudioHandler;
import game.DrawUtil;
import game.Game;

public class GuiButton {

	private State currentState = State.RELEASED;
	private Rectangle clickBox;
	private ArrayList<ActionListener> actionListeners;
	private String text = "";
	
	private Color released;
	private Color hover;
	private Color pressed;
	private Font font = Game.main.deriveFont(22f);
	private AudioHandler audio;
	
	public GuiButton(int x, int y, int width, int height) {
		clickBox = new Rectangle(x, y, width, height);
		actionListeners = new ArrayList<ActionListener>();
		released = new Color(173, 177, 179);
		hover = new Color(150, 156, 158);
		pressed = new Color(111, 116, 117);
		
		audio = AudioHandler.getInstance();
		audio.loadSound("select.wav", "select");
	}
	
	public void update() { 	}
	
	public void render(Graphics2D g) {
		if (currentState == State.RELEASED) {
			g.setColor(released);
			g.fill(clickBox);
		}
		else if (currentState == State.HOVER) {
			g.setColor(hover);
			g.fill(clickBox);
		}
		else {
			g.setColor(pressed);
			g.fill(clickBox);
		}
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(text, clickBox.x + clickBox.width/2 - DrawUtil.getMessageWidth(text, font, g)/2, 
							clickBox.y + clickBox.height/2 + DrawUtil.getMessageHeight(text, font, g)/2);
	}
	
	public void addActionListion(ActionListener listener) {
		actionListeners.add(listener);
	}
	
	public void mousePressed(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) currentState = State.PRESSED;
	}
	
	public void mouseReleased(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			for (ActionListener al: actionListeners) al.actionPerformed(null);
			audio.play("select", 0);
		}
		currentState = State.RELEASED;
	}
	
	public void mouseDragged(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) currentState = State.PRESSED;
		else currentState = State.RELEASED;
	}
	
	public void mouseMoved(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) currentState = State.HOVER;
		else currentState = State.RELEASED;
	}
	
	public int getX() { return clickBox.x; } 
	
	public int getY() { return clickBox.y; }
	
	public int getWidth() { return clickBox.width; }
	
	public int getHeight() { return clickBox.height; }
	
	public void setText(String text) { this.text = text; } 
	
	private enum State {
		RELEASED,
		HOVER,
		PRESSED
	}
	
}
