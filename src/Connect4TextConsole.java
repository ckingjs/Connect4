import java.util.Scanner;

/**
 * UI for players to use the Connect4 game
 *
 * @author Chris King
 * @version 1.0
 */
public class Connect4TextConsole {

	/**
	 * Entry point for the application
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		Connect4 c4 = new Connect4();
		Scanner in = new Scanner(System.in);

		System.out.println("For a computer opponent, press 'C'.  Hit any other key for two player mode.");
		String opp = in.next();

		Connect4ComputerPlayer cpO = new Connect4ComputerPlayer(c4, 'O');

		while (c4.getWinner() == 0) {
			int col;

			if (!"C".equals(opp) || c4.getRound() % 2 == 1) {
				System.out.println("Round: " + c4.getRound());
				System.out.println("Player " + c4.getPlayerByTurn() + " pick a column: ");
				col = in.nextInt();
			}
			else {
				col = cpO.getMove();
				System.out.println("Computer Player picks column: " + col);
			}

			try {
				while (!c4.addPiece(col)) {
					System.out.println("Column filled.  Please pick another.");
				};
			}
			catch (Exception e) {
				System.out.println("There was an error :(");
				System.out.println(e.getMessage());
				return;
			}

			displayGrid(c4);
		}

		System.out.println("\n\nWinner is: " + c4.getWinner());
	}

	/**
	 * Displays a 7 column x 6 row (Connect4 default) grid to console
	 *
	 * @param c4 Connect4 game object
	 */
	public static void displayGrid(Connect4 c4) {
		try {
			for (int row = 6; row >= 1; row--) {
				System.out.print("|");
				for (int col = 1; col <= 7; col++) {
					System.out.print(c4.getPieceAtPosition(row, col) + "|");
				}
				System.out.println("");
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
