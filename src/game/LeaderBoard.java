package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LeaderBoard {

	private static LeaderBoard lBoard;
	private String filePath;
	private String highScore;
	
	//LeaderBoard of all time
	private ArrayList<Integer> topScore;
	private ArrayList<Integer> topTiles;
	private ArrayList<Long> topTime;
	
	private LeaderBoard() {
		filePath = new File("").getAbsolutePath();
		highScore = "Score";
		
		topScore = new ArrayList<Integer>();
		topTiles = new ArrayList<Integer>();
		topTime = new ArrayList<Long>();
	} 
	
	public static LeaderBoard getInstance() {
		if (lBoard == null) lBoard = new LeaderBoard();
		return lBoard;
	} 
	
	public void addScore(int score) {
		for (int i = 0; i < topScore.size(); i++) {
			if (score >= topScore.get(i)) {
				topScore.add(i, score);
				topScore.remove(topScore.size() - 1);
				return;
			}
		}
	}
	
	public void addTile(int tileValue) {
		for (int i = 0; i < topTiles.size(); i++) {
			if (tileValue >= topTiles.get(i)) {
				topTiles.add(i, tileValue);
				topTiles.remove(topTiles.size() - 1);
				return;
			}
		}
	}

	public void addTime(long millis) {
		for (int i = 0; i < topTime.size(); i++) {
			if (millis <= topTime.get(i)) {
				topTime.add(i, millis);
				topTime.remove(topTime.size() - 1);
				return;
			}
		}
	}
	
	public void loadScore() {
		try {
			File f = new File(filePath, highScore);
			if (!f.isFile()) createSaveData();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			
			topScore.clear();
			topTiles.clear();
			topTime.clear();
			
			String[] score = reader.readLine().split("-");
			String[] tile = reader.readLine().split("-");
			String[] time = reader.readLine().split("-");
			
			for (int i = 0; i < score.length; i++) {
				topScore.add(Integer.parseInt(score[i]));
			}
			for (int i = 0; i < score.length; i++) {
				topTiles.add(Integer.parseInt(tile[i]));
			}
			for (int i = 0; i < score.length; i++) {
				topTime.add(Long.parseLong(time[i]));
			}
			reader.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void saveScore() {
		FileWriter output = null;
		
		try {
			File f = new File(filePath, highScore);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(topScore.get(0) + "-" + topScore.get(1) + "-" + topScore.get(2) + "-" + topScore.get(3) + "-" + topScore.get(4));
			writer.newLine();
			writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4));
			writer.newLine();
			writer.write(topTime.get(0) + "-" + topTime.get(1) + "-" + topTime.get(2) + "-" + topTime.get(3) + "-" + topTime.get(4));
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private void createSaveData() {
		FileWriter output = null;

		try {
			File f = new File(filePath, highScore);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("0-0-0-0-0");
			writer.newLine();
			writer.write("0-0-0-0-0");
			writer.newLine();
			writer.write(Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE);
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public int getHighScore() {
		return  topScore.get(0);
	}
	
	public long getFastestTime() {
		return  topTime.get(0);
	}
	
	public int getHighTile() {
		return  topTiles.get(0);
	}

	public ArrayList<Integer> getTopScore() {
		return topScore;
	}

	public ArrayList<Integer> getTopTiles() {
		return topTiles;
	}

	public ArrayList<Long> getTopTime() {
		return topTime;
	}
}
