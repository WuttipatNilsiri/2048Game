package MODEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import LIB.ArraysTool;
import MODEL.State;

public class GameBoard {
	int[][] board;
	public int score = 0;

	final int SIZE = 4;
	
	
	State state = State.over;

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final String[] NAMES = {"up", "down", "left", "right"};

	ArrayList<Integer> xline = new ArrayList<>();
	ArrayList<Integer> yline = new ArrayList<>();
	ArrayList<Integer> possibleMove = new ArrayList<>();

	static final boolean[] PATTERN={
			false,	//0
			true,	//1
			true,	//2
			true,	//3
			true,	//4
			true,	//5
			true,	//6
			true,	//7
			false,	//8
			true,	//9
			true,	//10
			true,	//11
			false,	//12
			true,
			false,
			false
	};

	Random rand = new Random();

	public GameBoard(){
		startGame();
	}
	
	public void startGame() {
		if (state != State.running) {
			score = 0;
			state = State.running;
			board = new int[4][4];
			generateRandomly();
			generateRandomly();
		}
	}

	public GameBoard(GameBoard b){
		board = ArraysTool.dup(b.board);
	}

	public GameBoard(int[][] b){
		board = b;
	}

	public void copyFrom(GameBoard b){
		ArraysTool.destoryDup(b.board, board);
	}

	public void getSpareSpace(){
		xline.clear();
		yline.clear();
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				if(getTile(x, y) == 0){
					xline.add(x);
					yline.add(y);
				}
			}
		}
	}

	public void generateRandomly() {
		getSpareSpace();
		if(xline.isEmpty()){
			throw new RuntimeException("LOSE");
		}
		int index = (int)(rand.nextInt(xline.size()));
		int x = xline.get(index);
		int y = yline.get(index);
		putTile(x, y, rand.nextInt(10) == 0 ? 2 : 1);
	}

	

	
//LOGIC ZONE//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void left(){
		for(int y = 0; y < 4; y++){
			int free = 0;
			int prev = 0;
			for(int x = 0; x < 4; x++){
				int t = getTile(x, y);
				if(t != 0){
					putTile(x, y, 0);
					if(prev == t){
						score += 1 << (t+1);
						putTile(free - 1, y, t + 1);
						prev = 0;
					}else{
						putTile(free ++, y, t);
						prev = t;
					}
				}
			}
		}
	}

	public void up(){
		for(int x = 0; x < 4 ; x++){
			int free = 0;
			int prev = 0;
			for(int y = 0; y < 4; y++){
				int t = getTile(x, y);
				if(t != 0){
					putTile(x, y, 0);
					if(prev == t){
						score += 1 << (t + 1);
						putTile(x, free - 1, t + 1);
						prev = 0;
					}else{
						putTile(x, free++, t);
						prev = t;
					}
				}
			}
		}
	}

	public void right(){
		for(int y = 0; y < 4; y++){
			int free = 3;
			int prev = 0;
			for(int x = 3; x >= 0; x--){
				int t = getTile(x, y);
				if(t != 0){
					putTile(x, y, 0);
					if(prev == t){
						score += 1 << (t + 1);
						putTile(free + 1, y, t + 1);
						prev = 0;
					}else{
						putTile(free--, y, t);
						prev = t;
					}
				}
			}
		}
	}

	public void down(){
		for(int x = 0; x < 4; x++){
			int free = 3;
			int prev = 0;
			for(int y = 3; y >= 0; y--){
				int t = getTile(x, y);
				if(t != 0){
					putTile(x, y, 0);
					if(prev == t){
						score += 1 << (t + 1);
						putTile(x, free + 1, t + 1);
						prev = 0;
					}else{
						putTile(x, free--, t);
						prev = t;
					}
				}
			}
		}
	}

	public boolean canLeft(){
		for(int y = 0; y < 4; y++){
			int prev = getTile(0, y);
			for(int x = 1; x < 4; x++){
				int t = getTile(x, y);
				if(t != 0){
					if(t == prev){
						return true;
					}else{
						prev = t;
					}
				}
			}
			
			int c = getTile(0, y) == 0 ? 0 : 8;
			c += getTile(1, y) == 0 ? 0 : 4;
			c += getTile(2, y) == 0 ? 0 : 2;
			c += getTile(3, y) == 0 ? 0 : 1;
			if(PATTERN[c]){
				return true;
			}
		}

		return false;
	}

	public boolean canRight(){
		for(int y = 0; y < 4; y++){
			int prev = getTile(0, y);
			for(int x = 1; x < 4; x++){
				int t = getTile(x, y);
				if(t != 0){
					if(t == prev){
						return true;
					}else{
						prev = t;
					}
				}
			}
			int c = getTile(3, y) == 0 ? 0 : 8;
			c += getTile(2, y) == 0 ? 0 : 4;
			c += getTile(1, y) == 0 ? 0 : 2;
			c += getTile(0, y) == 0 ? 0 : 1;
			if(PATTERN[c]){
				return true;
			}
		}
		return false;
	}

	public boolean canUp(){
		for(int x = 0; x < 4; x++){
			int prev = getTile(x, 0);
			for(int y = 1; y < 4; y++){
				int t = getTile(x, y);
				if(t != 0){
					if(t == prev){
						return true;
					}else{
						prev = t;
					}
				}
			}
			int c = getTile(x, 0) == 0 ? 0 : 8;
			c += getTile(x, 1) == 0 ? 0 : 4;
			c += getTile(x, 2) == 0 ? 0 : 2;
			c += getTile(x, 3) == 0 ? 0 : 1;
			if(PATTERN[c]){
				return true;
			}
		}
		return false;
	}

	public boolean canDown(){
		for(int x = 0; x < 4; x++){
			int prev = getTile(x, 0);
			for(int y = 1; y < 4; y++){
				int t = getTile(x, y);
				if(t != 0){
					if(t == prev){
						return true;
					}else{
						prev = t;
					}
				}
			}
			int c= getTile(x, 3) == 0 ? 0 : 8;
			c += getTile(x, 2) == 0 ? 0 : 4;
			c += getTile(x, 1) == 0 ? 0 : 2;
			c += getTile(x, 0) == 0 ? 0 : 1;
			if(PATTERN[c]){
				return true;
			}
		}
		return false;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean canDo(int index){
		switch(index){
		case UP: return canUp();
		case DOWN: return canDown();
		case LEFT: return canLeft();
		case RIGHT: return canRight();
		default: throw new UnsupportedOperationException();
		}
	}

	public void makeMove(int index){
		switch(index){
		case UP: up(); break;
		case DOWN: down(); break;
		case LEFT: left(); break;
		case RIGHT: right();break;
		default: throw new UnsupportedOperationException();
		}
	}

	public List<Integer> getPossibleMoves(){
		possibleMove.clear();
		if(canUp())
			possibleMove.add(UP);
		if(canDown())
			possibleMove.add(DOWN);
		if(canLeft())
			possibleMove.add(LEFT);
		if(canRight())
			possibleMove.add(RIGHT);
		return possibleMove;
	}


	public void putTile(int x, int y, int i) {
		board[y][x] = i;
	}

	public int getTile(int x, int y){
		return board[y][x];
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}


}
