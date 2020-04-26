import java.io.Serializable;

/**
 * Represents an object that checks whether or not a newly placed Connect4 piece has resulted in
 * a diagonal win
 *
 * @author Chris King
 * @version 1.0
 */
public class DiagonalChecker implements Serializable {

    private final char player;
    private int currentStreak;
    private final int connectToWin;

    /**
     * Constructor for the DiagonalChecker
     *
     * @param c4 The Connect4 game object
     * @param col The column where the latest piece was placed
     * @param row The row where the latest piece was placed
     * @param connectToWin Number of pieces that need to connect to make a win
     */
    public DiagonalChecker(Connect4 c4, int col, int row, int connectToWin) {
        this.connectToWin = connectToWin;
        this.player = c4.getPlayerByTurn();

        getStreak(c4, col, row, 1);
        getStreak(c4, col, row, 2);
    }

	private void getStreak(Connect4 c4, int startCol, int startRow, int direction) {
		if (currentStreak == connectToWin) return;

		currentStreak = 1;
		int adjRow = direction == 1 ? -1 : 1;

		try {
			walkDiagonal(c4, startCol, startRow, 1, adjRow);
			walkDiagonal(c4, startCol, startRow, -1, -adjRow);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    private void getSingleStreak(Connect4 c4, int startCol, int startRow, int direction) {
        if (currentStreak == connectToWin) return;

        currentStreak = 1;

        try {
            walkDiagonal(c4, startCol, startRow, 1, direction);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter for the checker's streak
     *
     * @return
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    private void walkDiagonal(Connect4 c4, int startCol, int startRow, int colAdj, int rowAdj) throws Exception {
		if (currentStreak == connectToWin) return;

        boolean inBounds = c4.isInBounds(startCol + colAdj, startRow + rowAdj);
    	char p = inBounds ? c4.getPieceAtPosition(startRow + rowAdj, startCol + colAdj) : 0;

    	if (player == p) {
            currentStreak++;

            walkDiagonal(c4, startCol + colAdj, startRow + rowAdj, colAdj, rowAdj);
        }
    }

    /**
     * Helper to indicate whether or not are enough connected pieces of either player to create a win
     *
     * @return True = there is a winner, False = no winner yet
     */
    public boolean getIsWinner() {
        return connectToWin == currentStreak;
    }
}
