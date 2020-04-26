import java.io.Serializable;

/**
 * Represents one column in a Connect4 game grid
 *
 * @author Chris King
 * @version 1.0
 */

public class GameColumn implements Serializable {

	private final int rowCount;
	private final int connectToWin;
	private char[] column;
	private int currentRow;
	private int currentStreak;
	private char currentStreakPlayer;


    /**
     * Constructor for a new Connect4 GameColumn
     *
     * @param rowCount Number of rows this column will have
     * @param connectToWin Number of pieces that need to connect to make a win
     */
	public GameColumn(int rowCount, int connectToWin) {
		this.rowCount = rowCount;
		this.connectToWin = connectToWin;
		currentRow = 0;
		currentStreak = 0;

		initializeColumn();
	}

	private void initializeColumn() {
		this.column = new char[rowCount];

		for (int i = 0; i < rowCount; i++) {
			this.column[i] = ' ';
		}
	}

    /**
     * Adds a piece to the column
     *
     * @param player 'X' or 'O'
     * @return True = piece was added, False = piece was not added (column was full)
     */
	public boolean addPiece(char player) {
		if (isFull() || isWin()) return false;
		if (currentStreakPlayer == 0) currentStreakPlayer = player;  //Needed to handle first play

		column[currentRow++] = player;
		updateStreak(player);

		return true;
	}

    /**
     * Getter for the last row where a piece was placed
     *
     * @return integer indicating the last row a piece was placed
     */
	public int getCurrentRow() {
		return currentRow;
	}

    /**
     * Getter for the piece at the specified row
     *
     * @param row The row from which to get the piece
     * @return 'X', 'O', or ' ' (if a piece has not been placed in that row yet)
     */
	public char getPieceAtRow(int row) {
		return column[row-1];
	}

	private void updateStreak(char player) {
		if (player == currentStreakPlayer) currentStreak++;
		else {
			swapPlayers();
			currentStreak = 1;
		}
	}

	private void swapPlayers() {
		currentStreakPlayer = currentStreakPlayer == 'X' ? 'O' : 'X';
	}

    /**
     * Helper that indicates whether or not a column is full
     *
     * @return True = the column is full, False = there is at least one row still open in the column
     */
	public boolean isFull() {
		return currentRow == rowCount;
	}

    /**
     * Helper to indicate whether or not are enough connected pieces of either player to create a win
     *
     * @return True = there is a winner, False = no winner yet
     */
	public boolean isWin() {
		return currentStreak == connectToWin;
	}

}
