/*
    board.place(building).at(2,3)
    board.at(2,3).place(building)
    board.move(building).from(2,3).to(4,5)

    board.move(building, from, to)

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

class BoardRemoveCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("remove");
        words.add("at");
    }
}

class BoardMoveCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("move");
        words.add("from");
        words.add("to");
    }
}


class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}


public class Board {
    public static final Boardable empty = null;
    public static final int defaultSize = 3;
    private Boardable[][] board;
    int xMax = 0, yMax = 0;
    int x = 0, y = 0;
    Boardable piece = null;
    BoardCommand command = null;

    public Board() {
        this.xMax = Board.defaultSize;
        this.yMax = Board.defaultSize;
        board = new Boardable[xMax][yMax];
    }
    public Board(int xDim, int yDim) {
        this.xMax = xDim;
        this.yMax = yDim;
        board = new Boardable[xMax][yMax];
    }

    public Board remove(Boardable piece) {
        this.piece = piece;
        this.command = new BoardRemoveCommand();
        return this;
    }
    public void remove(Boardable piece, Point at) {
        if (!board[at.x][at.y].equals(piece)) {
            return;
        }
        board[at.x][at.y] = Board.empty;
    }

    public Board move(Boardable piece) {
        this.piece = piece;
        this.command = new BoardMoveCommand();
        return this;
    }
    public void move(Boardable piece, Point from, Point to) {
        if (!board[from.x][from.y].equals(piece)) {
            return;
        }
        board[from.x][from.y] = Board.empty;
        board[to.x][to.y] = piece;
    }

    public void place(Boardable piece, Point at) {
        if (!(board[at.x][at.y] == Board.empty)) {
            return;
        }
        board[at.x][at.y] = piece;
    }
    public Board place(Boardable piece) {
        this.piece = piece;
        this.command = new BoardPlaceCommand();
        return this;
    }

    public Boardable on(int x, int y) {
        return board[x][y];
    }

    public Board at(int x, int y) {
        this.x = x;
        this.y = y;
        board[x][y] = piece;
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

