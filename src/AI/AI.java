package AI;

import MODEL.GameBoard;

public class AI {
	
	private Algorithm algo;
	
	public AI(Algorithm algo) {
		this.algo = algo;
	}
	
	public int makeMove(GameBoard b) {
		return algo.makeMove(b);
	}
	
}
