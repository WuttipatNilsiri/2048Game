package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.DrawUtil;
import game.Game;

public class MainMenuPanel extends GuiPanel {

	private Font titleFont = Game.main.deriveFont(100f);
	private String title = "2048";
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int spacing = 90;
	
	public MainMenuPanel() {
		super();
		GuiButton playButton = new GuiButton(Game.WIDTH/2 - buttonWidth/2, 220, buttonWidth, buttonHeight);
		playButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Play");
			}
		});
		playButton.setText("Play");
		add(playButton);
		
		GuiButton scoreButton = new GuiButton(Game.WIDTH/2 - buttonWidth/2, playButton.getY() + spacing, buttonWidth, buttonHeight);
		scoreButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Leaderboards");
			}
		});
		scoreButton.setText("Score");
		add(scoreButton);
		
		GuiButton quitButton = new GuiButton(Game.WIDTH/2 - buttonWidth/2, scoreButton.getY() + spacing, buttonWidth, buttonHeight);		
		quitButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setText("Quit");
		add(quitButton);
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString(title, Game.WIDTH/2 - DrawUtil.getMessageWidth(title, titleFont, g)/2, 150);
	}
	
}
