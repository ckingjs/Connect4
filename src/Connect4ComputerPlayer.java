import java.util.HashMap;

/**
 * Programmatic player for Connect4 game
 *
 * @author Chris King
 * @version 1.0
 */

public class Connect4ComputerPlayer {
    private final char player;
    private final int rows;
    private final int cols;
    private final Connect4 board;
    private char[][] mirrorBoard;

    /**
     * Default constructor for Connect4 Player.  Sets up game for standard board size
     *
     * @param c4 The Connect4 game object
     * @param player A character representing the side (X or O) the player should play for
     */
    public Connect4ComputerPlayer(Connect4 c4, char player) {
        this(c4, player, 6, 7);
    }

    /**
     * Constructor that adjusts the board size for non-standard play
     *
     * @param c4 The Connect4 game object
     * @param player A character representing the side (X or O) the player should play for
     * @param rows Number of rows in the board
     * @param cols Number of columns in the board
     */
    public Connect4ComputerPlayer(Connect4 c4, char player, int rows, int cols) {
        this.board = c4;
        this.player = player;
        this.rows = rows;
        this.cols = cols;

        initializeBoard();
    }

    /**
     * Sets up this Player's reflection of the game board
     *
     */
    private void initializeBoard() {
        mirrorBoard = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                mirrorBoard[r][c] = ' ';
            }
        }
    }

    /**
     * Selects the column where the next piece should be dropped
     *
     * @return The desired column for the next piece
     */
    public int getMove() {
        HashMap<String, Integer> positions;
        int bestCol;

        try {
            positions = analyzeBoard();

            //For the win
            bestCol = lookForWinOrBlock(positions.get("thisCol"), positions.get("thisRow"));
            if(bestCol != -1) return bestCol;

            //For the block
            bestCol = lookForWinOrBlock(positions.get("oppCol"), positions.get("oppRow"));
            if(bestCol != -1) return bestCol;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return (int) (Math.ceil(Math.random() * 1000) % cols) + 1;
    }

    /**
     * Compares the game board to this Player's reflection to determine changes since the last round.
     *
     * @return The position of the player's and opponent's most recent moves.
     * @throws Exception
     */
    private HashMap analyzeBoard() throws Exception {
        HashMap<String, Integer> positions = new HashMap<>();
        positions.put("oppCol", 0);
        positions.put("oppRow", 0);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char boardPiece = board.getPieceAtPosition(r+1,c+1);

                if (mirrorBoard[r][c] != boardPiece) {
                    mirrorBoard[r][c] = boardPiece;

                    if (boardPiece != player) {
                        positions.put("oppCol", c+1);
                        positions.put("oppRow", r+1);
                    }
                    else {
                        positions.put("thisCol", c+1);
                        positions.put("thisRow", r+1);
                    }
                }
            }
        }

        return positions;
    }

    private int lookForWinOrBlock(int col, int row) {
        int winCol = 0;

        if ((new VerticalChecker(board,col,row, 4)).getCurrentStreak() == 3) {
            try {
                return col;
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        if ((new HorizontalChecker(board,col,row, 4)).getCurrentStreak() == 3) {
            try {
                winCol = findNextHorizontalOpening(row, col);
                if (winCol != -1) return winCol;
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return -1;
    }

    private int findNextHorizontalOpening(int row, int col) throws Exception {
        int adj = 1;
        char p = board.getPieceAtPosition(row, col);

        while (board.isInBounds(col+adj, row) && board.getPieceAtPosition(row, col+adj) == p) {
            adj++;
        }
        if (board.isInBounds(col+adj, row) && board.getPieceAtPosition(row, col+adj) == ' ') return col+adj;

        adj = -1;
        while (board.isInBounds(col+adj, row) && board.getPieceAtPosition(row, col+adj) == p) {
            adj--;
        }
        if (board.isInBounds(col+adj, row) && board.getPieceAtPosition(row, col+adj) == ' ') return col+adj;

        return -1;
    }
}
