import java.io.Serializable;

/**
 * Connect4 represents a game of Connect 4, to include the game grid,
 * pieces, and rules
 *
 * @author Chris King
 * @version 1.0
 *
 */
public class Connect4 implements Serializable {

	private int roundNum;
	private final int connectToWin;
	private final int columnCount;
	private final int rowCount;
	private final int maxRounds;
	private GameColumn[] board;
	private char winner;

	/**
	 * Default constructor.  Sets game up for standard Connect 4 rules.
	 */
	public Connect4() {
		this(4, 7, 6);
	}

	/**
	 * Connect4 constructor that takes in values that modify the game rules
	 *
	 * @param ctw The number of pieces that need to be connected (vertically, horizontally, or diagonally) to win
	 * @param cc The number of columns in the grid
	 * @param rc The number of rows in the grid
	 */
	public Connect4(int ctw, int cc, int rc) {
		roundNum = 1;
		connectToWin = ctw;
		columnCount = cc;
		rowCount = rc;
		maxRounds = cc * rc;

		board = new GameColumn[columnCount];

		for (int i = 0; i < columnCount; i++) {
			board[i] = new GameColumn(rowCount, connectToWin);
		}
	}

	/**
	 * Getter for the round that the game is currently on.
	 *
	 * @return Which round the game is on.
	 */
	public int getRound() {
		return roundNum;
	}

	/**
	 * Getter for which player's turn it is (uses which round the game is on to determine)
	 *
	 * @return X or O, depending on round
	 */
	public char getPlayerByTurn() {
		return roundNum % 2 == 1 ? 'X' : 'O';
	}

	/**
	 * Adds a piece to the game grid
	 *
	 * @param col The column of the grid in which the player would like to drop their piece.
	 * @return True = piece was added to grid, False = piece was not added (because column was full)
	 * @throws Exception Indicates player selected an out of bounds column
	 */
	public boolean addPiece(int col) throws Exception {
		if (!isInBounds(col)) throw	new Exception("Out of bounds column!!!!"); //Out of bounds column

		if (winner != 0) { //Already have a winner, no piece added
			System.out.println("This game already has a winner.");
			return false;
		}

		if(board[col-1].addPiece(getPlayerByTurn())) {//addPiece will return true if the piece was added, false if column is full
			int currentRow = board[col-1].getCurrentRow();

			if (
					board[col-1].isWin()
					|| (new DiagonalChecker(this, col, currentRow, connectToWin)).getIsWinner() //Diagonal check will look for diagonal wins
					|| (new HorizontalChecker(this, col, currentRow, connectToWin)).getIsWinner()
			) winner = getPlayerByTurn();
			else if (roundNum == maxRounds) winner = '?';

			roundNum++;
			return true; //piece added
		}
		else return false; //Indicates this column is full
	}

	/**
	 * Determines whether the provided column number is in or out of bounds of the game grid.
	 *
	 * @param col The column number to check
	 * @return True = in bounds, False = out of bounds
	 */
	public boolean isInBounds(int col) {
		return col > 0 && col <= columnCount;
	}

	/**
	 * Determines whether the provided column/row numbers are in or out of bounds of the game grid.
	 *
	 * @param col The column number to check
	 * @param row The row number to check
	 * @return True = in bounds, False = out of bounds
	 */
	public boolean isInBounds(int col, int row) {
		return col > 0 && col <= columnCount
				&& row > 0 && row <= rowCount;
	}

	/**
	 * Getter for the piece at the specified location.
	 *
	 * @param row The row at which to check
	 * @param col The column at which to check
	 * @return 'X', 'O', or ' ' (if that position has not been filled)
	 * @throws Exception Indicates player selected an out of bounds column
	 */
	public char getPieceAtPosition(int row, int col) throws Exception {
		if (!isInBounds(col)) throw	new Exception("Out of bounds column!!!!"); //Out of bounds column

		return board[col-1].getPieceAtRow(row);
	}

	/**
	 * Getter for the winner of the game.
	 *
	 *
	 * @return 'X', 'O', 0 (no winner yet), '?' (board is full, but no winner)
	 */
	public char getWinner() {
		return winner;
	}

}
