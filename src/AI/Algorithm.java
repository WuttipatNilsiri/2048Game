package AI;

import MODEL.GameBoard;

public interface Algorithm {
	/**
	 * Make Move form @param b (GameBoard)
	 * @param b
	 * @return move : int
	 */
	public int makeMove(GameBoard b);
}
