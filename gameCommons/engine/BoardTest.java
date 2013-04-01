
package gameCommons.engine;

import junit.framework.TestCase;


public class BoardTest extends TestCase {

    public final void test_place() {
        Boardable building = new Building();
        Board board = new Board();
        board.place(building);
        assertEquals(board.on(0,0), Board.empty);
    }
    public final void test_placeBuilding() {
        Boardable building = new Building();
        Board board = new Board();
        board.place(building).at(0,0);
        assertEquals(board.on(0,0), building);
    }
}
