package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.sound.sampled.Clip;

public class GameBoard {

	public static final int ROWS = 4;
	public static final int COLS = 4;
	
	private final int startingTiles = 2;
	private Tile[][] board;
	private boolean dead;
	private boolean won;
	private BufferedImage gameBoard;
	private BufferedImage finalBoard;
	private int x;
	private int y;
	
//	private int score = 0;
//	private int highScore = 0;
//	private Font scoreFont;
	//Saving
	private String saveDataPath;
	private String fileName = "SaveData";
	private int saveCount;
	
	private static int SPACING = 10;
	public static int BOARD_WIDTH = (COLS+1) * SPACING + COLS * Tile.WIDTH;
	public static int BOARD_HEIGHT = (ROWS+1) * SPACING + ROWS * Tile.HEIGHT;
	
	//time
	private long elapsedMS;
//	private long fastestMS;
	private long startTime;
//	private String formattedTime = "00:00:000"; 
	
	//sounds
	private AudioHandler audio;
	//score and leaderBoard
	private ScoreManager scores;
	private LeaderBoard lboard;
	
	private boolean hasStarted;
	
	public GameBoard(int x, int y) {
		
		this.x = x;
		this.y = y;
		board = new Tile[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		createBoardImage();
		
		audio = AudioHandler.getInstance();
		audio.loadSound("MainSong.mp3", "BG");
		audio.loadSound("Click.mp3", "click");
		audio.adjustVolume("BG", -10);
		audio.play("BG", Clip.LOOP_CONTINUOUSLY);
		
		lboard = LeaderBoard.getInstance();
		lboard.loadScore();
		scores = new ScoreManager(this);
		scores.setBestTime(lboard.getFastestTime());
		scores.setCurrentTopScore(lboard.getHighScore());
		if (scores.newGame()) {
			start();
			scores.saveGame();
		}
		else {
			for (int i = 0; i < scores.getBoard().length; i++) {
				if (scores.getBoard()[i] == 0) continue;
				spawn(i/ROWS, i%COLS, scores.getBoard()[i]);
			}
			dead = checkDead();
			won = checkWon();
		}
	}
	
	public void reset() {
		board = new Tile[ROWS][COLS];
		start();
		scores.saveGame();
		dead = false;
		won = false;
		hasStarted = false;
		startTime = System.nanoTime();
		elapsedMS = 0;
		saveCount = 0;
	}
	private void createBoardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = SPACING + SPACING*col + Tile.WIDTH*col;
				int y = SPACING + SPACING*row + Tile.HEIGHT*row;
				g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
			}	
		}
	}	
	
	public void start() {
		for (int i = 0; i < startingTiles; i++) {
			spawnRandom();
		} 
	}
	
	private void spawn(int row, int col, int value) {
		board[row][col] = new Tile(value, getTileX(col), getTileY(row));
	}
	
	private void spawnRandom() {
		Random random = new Random();
		boolean notValid = true;
		
		while (notValid) {
			int location = random.nextInt(ROWS * COLS);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			if (current == null) {
				int value = random.nextInt(10) < 9 ? 2 : 4;
				Tile tile = new Tile(value, getTileX(col), getTileY(row));
				board[row][col] = tile;
				notValid = false;
			}
		}
	}
	
	public int getTileX(int col) {
		return SPACING + col * Tile.WIDTH + col * SPACING;
	}
	
	public int getTileY(int row) {
		return SPACING + row * Tile.HEIGHT + row * SPACING;
	}
	
	public void render(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
		g2d.drawImage(gameBoard, 0, 0, null);
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current ==  null) continue;
				current.render(g2d);
			} 
		} 
		
		g.drawImage(finalBoard, x, y, null);
		g2d.dispose();
		}
	
	public void update() {
		saveCount++;
		if (saveCount >= 120) {
			saveCount = 0;
			scores.saveGame();
		}
		if (!won && !dead) {
			if (hasStarted) {
				elapsedMS = (System.nanoTime() - startTime) / 1000000;
//				formatTime(elapsedMS);
				scores.setTime(elapsedMS);
			}
			else startTime = System.nanoTime();
		}
		
		checkKeys();
		
//		if (score >= highScore) highScore = score;
		if (scores.getCurrentScore() >= scores.getCurrentTopScore()) scores.setCurrentTopScore(scores.getCurrentScore());
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current ==  null) continue;
				current.update();
				resetPosition(current, row, col);
				if (current.getValue() == 2048) {
//					won = true;
					setWon(true);
				}
			} 
		} 
	}
	
//	private String formatTime(long millis) {
//		String formattedTime;
//		//hour
//		String hourFormat = "";
//		int hour = (int) (millis/3600000);
//		if (hour >= 1) {
//			millis -= hour*3600000;
//			if (hour < 10) hourFormat = "0" + hour;
//			else hourFormat = "" + hour;
//			hourFormat += ":";
//		}
//		
//		//minute
//		String minuteFormat;
//		int minute = (int) (millis/60000);
//		if (minute >= 1) {
//			millis -= minute*60000;
//			if (minute < 10) minuteFormat = "0" + minute;
//			else minuteFormat = "" + minute;
//		}
//		else minuteFormat = "00";
//		
//		//second
//		String secondFormat;
//		int second = (int) (millis/1000);
//		if (second >= 1) {
//			millis -= second*1000;
//			if (second < 10) secondFormat = "0" + second;
//			else secondFormat = "" + second;
//		}
//		else secondFormat = "00";
//		
//		//millisecond
//		String millisFormat;
//		if (millis > 99) millisFormat = "" + millis;
//		else if (millis > 9) millisFormat = "0" + millis;
//		else millisFormat = "00" + millis;
//		
//		formattedTime = hourFormat + minuteFormat + ":" + secondFormat + ":" + millisFormat;
//		return formattedTime;
//	}
	
	private void resetPosition(Tile current, int row, int col) {
		if (current == null) return ;
		
		int x = getTileX(col);
		int y = getTileY(row);
		
		int distX = current.getX() - x;
		int distY = current.getY() - y;
		
		if (Math.abs(distX) < Tile.SLIDE_SPEED)	current.setX(current.getX() - distX);
		if (Math.abs(distY) < Tile.SLIDE_SPEED)	current.setY(current.getY() - distY);
		if (distX < 0) current.setX(current.getX() + Tile.SLIDE_SPEED);
		if (distY < 0) current.setY(current.getY() + Tile.SLIDE_SPEED);
		if (distX > 0) current.setX(current.getX() - Tile.SLIDE_SPEED);
		if (distY > 0) current.setY(current.getY() - Tile.SLIDE_SPEED);
		
	}
	
	private boolean move(int row, int col, int hor, int ver, Direction dir) {
		boolean canMove = false;
		
		Tile current = board[row][col];
		if (current == null) return false;
		boolean move = true;
		int newCol = col;
		int newRow = row;
		while (move) {
			newCol += hor;
			newRow += ver;
			if (checkOutOfBounds(dir, newRow, newCol)) break;
			if (board[newRow][newCol] == null) {
				board[newRow][newCol] = current;
				board[newRow - ver][newCol - hor] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				canMove = true;
			}
			else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
				board[newRow][newCol].setCanCombine(false);
				board[newRow][newCol].setValue(board[newRow][newCol].getValue()*2);
				canMove = true;
				board[newRow - ver][newCol - hor] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				board[newRow][newCol].setCombineAnimation(true);
//				score += board[newRow][newCol].getValue();
				scores.setCurrentScore(scores.getCurrentScore() + board[newRow][newCol].getValue());
			}
			else move = false;
		}
		return canMove;
	}
	
	private boolean checkOutOfBounds(Direction dir, int row, int col) {
		if (dir == Direction.LEFT) return col < 0;
		else if (dir == Direction.RIGHT) return col > COLS - 1;
		else if (dir == Direction.UP) return row < 0;
		else if (dir == Direction.DOWN) return row > ROWS - 1;
		return false;
	}
	
	public void movingTiles(Direction dir) {
		boolean canMove = false;
		int hor = 0;
		int ver = 0;
		
		if (dir == Direction.LEFT) {
			hor = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, hor, ver, dir);
					}
					else move(row, col, hor, ver, dir);
				}
			}
		}
		
		else if (dir == Direction.RIGHT) {
			hor = 1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = COLS-1; col >= 0; col--) {
					if (!canMove) {
						canMove = move(row, col, hor, ver, dir);
					}
					else move(row, col, hor, ver, dir);
				}
			}
		}
		
		else if (dir == Direction.UP) {
			ver = -1 ;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, hor, ver, dir);
					}
					else move(row, col, hor, ver, dir);
				}
			}
		}
		
		else if (dir == Direction.DOWN) {
			ver = 1;
			for (int row = ROWS-1; row >= 0; row--) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, hor, ver, dir);
					}
					else move(row, col, hor, ver, dir);
				}
			}
		}
		else {
			System.out.println(dir + "is not a valid direction.");
		}
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null) continue;
				current.setCanCombine(true);
			}
		}
		
		if (canMove) {
			audio.play("click", 0);
			spawnRandom();
//			checkDead();
			setDead(checkDead());
		}
	}
	
	private boolean checkDead() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null) return false;
				boolean canCombine = checkSurroundingTiles(row, col, board[row][col]);
				if (canCombine) return false;
			}
		}
//		dead = true;
//		if (score >= highScore) highScore = score;
//		setHighScore();
		return true;
	}
	
	private boolean checkWon() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null) continue;
				if (board[row][col].getValue() >= 2048) return true;
			}
		}
		return false;
	}
	
	private boolean checkSurroundingTiles(int row, int col, Tile current) {
		if (row > 0) {
			Tile check = board[row -1][col];
			if (check == null) return true;
			if (current.getValue() == check.getValue()) return true;
		}
		if (row < ROWS - 1) {
			Tile check = board[row +1][col];
			if (check == null) return true;
			if (current.getValue() == check.getValue()) return true;
		}
		if (col > 0) {
			Tile check = board[row][col - 1];
			if (check == null) return true;
			if (current.getValue() == check.getValue()) return true;
		}
		if (col < COLS - 1) {
			Tile check = board[row][col + 1];
			if (check == null) return true;
			if (current.getValue() == check.getValue()) return true;
		}
		return false;
	}
	
	public void checkKeys() {
		if (Keyboard.typed(KeyEvent.VK_LEFT)) {
			movingTiles(Direction.LEFT);
			if (!hasStarted) hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
			movingTiles(Direction.RIGHT);
			if (!hasStarted) hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_UP)) {
			movingTiles(Direction.UP);
			if (!hasStarted) hasStarted = !dead;
		}
		if (Keyboard.typed(KeyEvent.VK_DOWN)) {
			movingTiles(Direction.DOWN);
			if (!hasStarted) hasStarted = !dead;
		}
	}
	
	public int getHighestTileValue() {
		int value = 2;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null) continue;
				if (board[row][col].getValue() > value) value = board[row][col].getValue();
			}	
		}
		return value;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		if (!this.dead && dead) {
			lboard.addTile(getHighestTileValue());
			lboard.addScore(scores.getCurrentScore());
			lboard.saveScore();
		}
		this.dead = dead;
	}
	
	public boolean isWon() {
		return won;
	}
	 
	public void setWon(boolean won) {
		if (!this.won && won) {
			lboard.addTime(scores.getTime());
			lboard.saveScore();
		}
		this.won = won;
	}
	
	public ScoreManager getScore() {
		return scores;
	}
	
	public Tile[][] getBoard() {
		return board;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
