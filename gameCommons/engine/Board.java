/*
    place(building).at(2,3)
    at(2,3).place(building)

    move.is(place(building).at(2,3))
    make(move)
    mover.make(move)
 */


interface Boardable {
}


class Move {
    public Move() {}

    public void is() {
    }
}


class Board {
    private Boardable[][] board;
    int xMax = 0, yMax = 0;
    int x = 0, y = 0;
    Boardable item = null;

    public Board() {
        this.xMax = 3;
        this.yMax = 3;
    }
    public Board(int xDim, int yDim) {
        this.xMax = xDim;
        this.yMax = yDim;
    }

    public Board place(Boardable item) {
        this.item = item;
        return this;
    }
    public Board at(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}

