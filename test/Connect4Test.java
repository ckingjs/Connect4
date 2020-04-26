import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Connect4Test {
    private Connect4 c4 = new Connect4();

    @Test
    void getRound() throws Exception {
        assertEquals(1, c4.getRound());
        c4.addPiece(2);
        assertEquals(2, c4.getRound());
    }

    @Test
    void getPlayerByTurn() {
        assertEquals('X', c4.getPlayerByTurn());
    }

    @Test
    void addPiece() throws Exception {
        c4.addPiece(1);
        assertEquals('X', c4.getPieceAtPosition(1,1));

        c4.addPiece(2);
        assertEquals('O', c4.getPieceAtPosition(1,2));

        c4.addPiece(1);
        c4.addPiece(2);

        c4.addPiece(1);
        c4.addPiece(2);

        assertEquals(true, c4.addPiece(1));
        assertEquals(false, c4.addPiece(2));
    }

    @Test
    void isInBounds() {
        assertEquals(false, c4.isInBounds(0));
        assertEquals(false, c4.isInBounds(8));

        assertEquals(true, c4.isInBounds(1));

        assertEquals(false, c4.isInBounds(1, 0));
        assertEquals(false, c4.isInBounds(1, 7));

    }

    @Test
    void getPieceAtPosition() throws Exception {
        assertEquals(' ', c4.getPieceAtPosition(1,1));
    }

    @Test
    void getWinner() {
        assertEquals(0, c4.getWinner());
    }
}