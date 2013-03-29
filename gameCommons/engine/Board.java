/*
    move.is(place(building).at(2,3))
    make(move)
    mover.make(move)
 */


interface Boardable {
}


class Move {
    public Move() {}

    public is() {
    }
}


class Board {
    private Boardable[][] board;
    int xMax = 0;
    int yMax = 0;

    public Board() {
        this.xMax = 3;
        this.yMax = 3;
    }
    public Board(int xDim, int yDim) {
        this.xMax = xDim;
        this.yMax = yDim;
    }

    public Board place(Boardable item) {
        return this;
    }
    public Board at(int x, int y) {
        return this;
    }
}

