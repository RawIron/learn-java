/*
    board.place(building).at(2,3)
    board.at(2,3).place(building)
    board.move(building).from(2,3).to(4,5)

    move.is(place(building).at(2,3))
    mover.make(move)
 */


public interface Boardable {
    public int heigthIs();
    public int widthIs();
}


class Move {
    public Move() {}
    public void is() {
    }
}


class BoardCommand {
    private LinkedList<String> words = new LinkedList<String>;
    public void init() {}

    public BoardCommand() {
        init();
    }
    public boolean isComplete() {
        return (words.isEmpty());
    }
    public void found() {
        if (words.contains(word)) {
            words.remove(word);
        }
    }
}

class BoardPlaceCommand extends BoardCommand {
    @Override
    public void init() {
        words.put("place", false);
        words.put("at", false);
    }
}



public class Board {
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


