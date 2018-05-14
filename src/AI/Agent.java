package AI;

import MODEL.GameBoard;

public interface Agent {
	/**
	 * Make Move form @param b (GameBoard)
	 * @param b
	 * @return move : int
	 */
	int makeMove(GameBoard b);
}
