import java.io.Serializable;

/**
 * Represents an object that checks whether or not a newly placed Connect4 piece has resulted in
 * a horizontal win
 *
 * @author Chris King
 * @version 1.0
 */
public class HorizontalChecker implements Serializable {
    private char player;
    private int currentStreak;
    private final int connectToWin;

    /**
     * Constructor for the HorizontalChecker
     *
     * @param c4 The Connect4 game object
     * @param col The column where the latest piece was placed
     * @param row The row where the latest piece was placed
     * @param connectToWin Number of pieces that need to connect to make a win
     */
    public HorizontalChecker(Connect4 c4, int col, int row, int connectToWin) {
        this.connectToWin = connectToWin;

        try {
            this.player = c4.getPieceAtPosition(row, col);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        getStreak(c4, col, row);
        getStreak(c4, col, row);
    }

    private void getStreak(Connect4 c4, int startCol, int row) {
        if (currentStreak == connectToWin) return;

        currentStreak = 1;

        try {
            walkRow(c4, startCol, row, 1);
            walkRow(c4, startCol, row, -1);
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

    private void walkRow(Connect4 c4, int startCol, int row, int colAdj) throws Exception {
        if (currentStreak == connectToWin) return;

        boolean inBounds = c4.isInBounds(startCol + colAdj);
        char p = inBounds ? c4.getPieceAtPosition(row, startCol + colAdj) : 0;

        if (player == p) {
            currentStreak++;

            walkRow(c4, startCol + colAdj, row, colAdj);
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
