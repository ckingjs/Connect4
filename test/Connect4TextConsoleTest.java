import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Connect4TextConsoleTest {

    @Test
    void main() throws Exception {
        String[] args = {};
        Connect4TextConsole.main(args);
    }

    @Test
    void displayGrid() throws Exception {
        Connect4 c4 = new Connect4();
        Connect4TextConsole.displayGrid(c4);

        c4.addPiece(1);
        Connect4TextConsole.displayGrid(c4);

        c4.addPiece(2);
        Connect4TextConsole.displayGrid(c4);
    }
}