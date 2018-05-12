package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import game.DrawUtil;
import game.Game;
import game.LeaderBoard;

public class LeaderBoardPanel extends GuiPanel {

	private LeaderBoard lBoard;
	private int buttonWidth = 100;
	private int backButtonWidth = 220;
	private int buttonSpacing = 20;
	private int buttonY = 120;
	private int buttonHeight = 50;
	private int leaderboardsX = 130;
	private int leaderboardsY = buttonY + buttonHeight + 90;
	
	private String title = "Leaderboards";
	private Font titleFont = Game.main.deriveFont(48f);
	private Font scoreFont = Game.main.deriveFont(30f);
	private State currentState = State.SCORE;
	
	public LeaderBoardPanel(){
		super();
		lBoard = LeaderBoard.getInstance();
		lBoard.loadScore();

		GuiButton tileButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, buttonY, buttonWidth, buttonHeight);
		tileButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState = State.TILE;
			}
		});
		tileButton.setText("Tiles");
		add(tileButton);
		
		GuiButton scoreButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 - tileButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
		scoreButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState = State.SCORE;
			}
		});
		scoreButton.setText("Scores");
		add(scoreButton);
		
		GuiButton timeButton = new GuiButton(Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing, buttonY, buttonWidth, buttonHeight);
		timeButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState = State.TIME;
			}
		});
		timeButton.setText("Times");
		add(timeButton);
		
		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
		backButton.addActionListion(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Menu");
			}
		});
		backButton.setText("Back");
		add(backButton);
	}
	
	private void drawLeaderboards(Graphics2D g){
		ArrayList<String> strings = new ArrayList<String>();
		if(currentState == State.SCORE){
			strings = convertToStrings(lBoard.getTopScore());
		}
		else if(currentState == State.TILE){
			strings = convertToStrings(lBoard.getTopTiles());
		}
		else {
			for(Long l : lBoard.getTopTime()){
				strings.add(DrawUtil.formatTime(l));
			}
		}
		
		g.setColor(Color.black);
		g.setFont(scoreFont);
		
		for(int i = 0; i < strings.size(); i++){
			String s = (i + 1) + ". " + strings.get(i);
			g.drawString(s, leaderboardsX, leaderboardsY + i * 40);
		}
	}
	
	private ArrayList<String> convertToStrings(ArrayList<? extends Number> list){
		ArrayList<String> ret = new ArrayList<String>();
		for(Number n : list){
			ret.add(n.toString());
		}
		return ret;
	}
	
	public void update(){
		
	}
	
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(Color.black);
		g.drawString(title, Game.WIDTH / 2 - DrawUtil.getMessageWidth(title, titleFont, g) / 2, DrawUtil.getMessageHeight(title, titleFont, g) + 40);
		drawLeaderboards(g);
	}
	
	private enum State{
		SCORE,
		TILE,
		TIME
	}
}
