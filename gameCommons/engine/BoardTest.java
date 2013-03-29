
package gameCommons.engine;

import junit.framework.TestCase;


public class BoardTest extends TestCase {

    public final void test_place() {
        Boardable building = new Building();
        Board board = new Board();
        board.place(building);
        assertEquals(board.at(0,0), Board.empty);
    }
}
