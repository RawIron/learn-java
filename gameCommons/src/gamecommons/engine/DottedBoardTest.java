package gamecommons.engine;

import junit.framework.TestCase;


public class DottedBoardTest extends TestCase {

    public final void test_dottedPlaceIncomplete() {
        Boardable building = new Building();
        DottedBoard board = new DottedBoard();
        board.place(building);
        assertEquals(board.on(0,0), Board.empty);
    }
    public final void test_dottedPlaceBuilding() {
        Boardable building = new Building();
        DottedBoard board = new DottedBoard();
        board.place(building).at(0,0);
        assertEquals(board.on(0,0), building);
    }
    public final void test_dottedMoveBuilding() {
        Boardable building = new Building();
        DottedBoard board = new DottedBoard();
        board.place(building).at(0,0);
        board.move(building).from(0,0).to(1,1);
        assertEquals(board.on(0,0), Board.empty);
        assertEquals(board.on(1,1), building);
    }
    public final void test_dottedRemoveBuilding() {
        Boardable building = new Building();
        DottedBoard board = new DottedBoard();
        board.place(building).at(1,1);
        board.remove(building).at(1,1);
        assertEquals(board.on(1,1), Board.empty);
    }

}
