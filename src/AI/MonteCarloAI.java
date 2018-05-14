package AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import MODEL.GameBoard;


public class MonteCarloAI implements Agent{

	Random rand = new Random();
	
	int roundPlayed = 0;
	int searchDepth = 100;
	int simulation = 1024;
	
	static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	Future<Integer> createTask(final GameBoard b, final int move){
		return pool.submit(new Callable<Integer>(){
			@Override
			public Integer call() throws Exception {
				return evalMove(b, move);
			}
		});
	}
	
	@Override
	public synchronized int makeMove(final GameBoard b) {
		List<Integer> moves = b.getPossibleMoves();
		int best = -1;
		int bestScore = -1;
		ArrayList<ArrayList< Future<Integer> >> futures = new ArrayList<>();
		for(int i : moves){
			ArrayList< Future<Integer> > list = new ArrayList<>();
			list.add(createTask(b, i));
			list.add(createTask(b, i));
			list.add(createTask(b, i));
			list.add(createTask(b, i));
			futures.add(list);
		}
		int id = 0;
		for(int i : moves){
			ArrayList< Future<Integer> > arr = futures.get(id++);
			int score = 0;
			for(Future<Integer> f : arr){
				try {
					score += f.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			if(score > bestScore){
				bestScore = score;
			 	best = i;
			}
		}
		roundPlayed++;
		searchDepth = roundPlayed / 10 + 100;
	    simulation = roundPlayed / 16 + 1024;
		return best;
	}
	/**
	 * evalMove with @param b
	 * @param b
	 * @param move
	 * @return eval value
	 */
	private int evalMove(GameBoard b, int move) {
		GameBoard movedBoard = new GameBoard(b);
		movedBoard.makeMove(move);
		movedBoard.generateRandomly();
		GameBoard board = new GameBoard();
		int eval = 0;
		for(int i = 0; i < simulation; i++){
			int currDepth = 0;
			board.copyFrom(movedBoard);
			List<Integer> possibleMoves;
			while(currDepth < searchDepth && !(possibleMoves = board.getPossibleMoves()).isEmpty() ){
				board.makeMove( possibleMoves.get(rand.nextInt(possibleMoves.size())) );
				board.generateRandomly();
				currDepth++;
			}
			eval += currDepth == searchDepth ? searchDepth * 1.5 : currDepth;
		}
		return eval;
	}

}
