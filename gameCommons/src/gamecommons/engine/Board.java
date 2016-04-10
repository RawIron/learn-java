package gamecommons.engine;

import java.util.LinkedList;


interface Boardable {
    public int heigthIs();
    public int widthIs();
    Boardable at(int x, int y);
}

class Building implements Boardable {
    public int heigthIs() {
        return 1;
    }
    public int widthIs() {
        return 1;
    }

    public Boardable at(int x, int y) {
        return null;
    }
}


class Move {
    public Move() {}
    public void is() {
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

    public void remove(Boardable piece, Point at) {
        if (!board[at.x][at.y].equals(piece)) {
            return;
        }
        board[at.x][at.y] = Board.empty;
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

    public Boardable on(int x, int y) {
        return board[x][y];
    }
}
