/*
    board.place(building).at(2,3)
    board.at(2,3).place(building)
    board.move(building).from(2,3).to(4,5)

    move.is(place(building).at(2,3))
    mover.make(move)
 */

package gameCommons.engine;

import java.util.LinkedList;

interface Boardable {
    public int heigthIs();
    public int widthIs();
}

class Building implements Boardable {
    public int heigthIs() {
        return 4;
    }
    public int widthIs() {
        return 4;
    }
}


class Move {
    public Move() {}
    public void is() {
    }
}


class BoardCommand {
    protected LinkedList<String> words = new LinkedList<String>();
    public void init() {}

    public BoardCommand() {
        init();
    }
    public boolean isComplete() {
        return (words.isEmpty());
    }
    public void found(String word) {
        if (words.contains(word)) {
            words.remove(word);
        }
    }
}

class BoardPlaceCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("place");
        words.add("at");
    }
}



public class Board {
    public static final Boardable empty = null;
    private Boardable[][] board;
    int xMax = 0, yMax = 0;
    int x = 0, y = 0;
    Boardable item = null;
    BoardCommand command = null;

    public Board() {
        this.xMax = 3;
        this.yMax = 3;
        board = new Boardable[xMax][yMax];
    }
    public Board(int xDim, int yDim) {
        this.xMax = xDim;
        this.yMax = yDim;
        board = new Boardable[xMax][yMax];
    }

    public Board remove(Boardable item) {
        this.item = item;
        return this;
    }
    public Board move(Boardable item) {
        this.item = item;
        return this;
    }
    public Board place(Boardable item) {
        this.item = item;
        return this;
    }
    public Boardable on(int x, int y) {
        return board[x][y];
    }

    public Board at(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Board from(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Board to(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}


