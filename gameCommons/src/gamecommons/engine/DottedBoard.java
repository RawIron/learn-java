package gamecommons.engine;

/**
 * board.place(building).at(2,3)
 * board.at(2,3).place(building)
 * board.place(building, at)
 * <p>
 * board.move(building).from(2,3).to(4,5)
 * board.move(building, from, to)
 * <p>
 * move.is(place(building).at(2,3))
 * mover.make(move)
 */


import java.util.LinkedList;


class BoardCommand
{
    protected LinkedList<String> wordsNeeded = new LinkedList<String>();
    protected LinkedList<String> wordsFound = new LinkedList<String>();
    private Runner command;

    public void setRunner(Runner command) {
        this.command = command;
        this.wordsNeeded.clear();
        for (String word : command.getKeywords().split(" ")) {
            wordsNeeded.add(word);
        }
    }

    public Runner getRunner() {
        return this.command;
    }

    public boolean isComplete() {
        if (wordsNeeded.isEmpty()) {
            return false;
        } else {
            return wordsFound.containsAll(wordsNeeded);
        }
    }

    public void found(String word) {
        if (!wordsFound.contains(word)) {
            wordsFound.add(word);
        }
    }
}


class BoardRemove implements Runner {
    private DottedBoard board;
    private Boardable piece;
    private Point at;

    public String getKeywords() {
        return "remove at";
    }

    public Runner create(DottedBoard board) {
        this.board = board;
        this.piece = board.piece;
        this.at = board.at;
        return this;
    }

    public void run() {
        board.remove(piece, at);
    }
}

class BoardPlace implements Runner {
    private DottedBoard board;
    private Boardable piece;
    private Point at;

    public String getKeywords() {
        return "place at";
    }

    public Runner create(DottedBoard board) {
        this.board = board;
        this.piece = board.piece;
        this.at = board.at;
        return this;
    }

    public void run() {
        board.place(piece, at);
    }
}

class BoardMove implements Runner {
    private DottedBoard board;
    private Boardable piece;
    private Point from;
    private Point to;

    public String getKeywords() {
        return "move from to";
    }

    public Runner create(DottedBoard board) {
        this.board = board;
        this.piece = board.piece;
        this.from = board.from;
        this.to = board.to;
        return this;
    }

    public void run() {
        board.move(piece, from, to);
    }
}


interface Runner {
    Runner create(DottedBoard board);
    void run();
    String getKeywords();
}


public class DottedBoard extends Board {
    public Point to = null;
    public Point from = null;
    public Point at = null;

    public Boardable piece = null;
    public BoardCommand command = new BoardCommand();

    public DottedBoard remove(Boardable piece) {
        this.command.setRunner(new BoardRemove());
        this.command.found("remove");
        this.piece = piece;
        return this;
    }

    public DottedBoard move(Boardable piece) {
        this.command.setRunner(new BoardMove());
        this.command.found("move");
        this.piece = piece;
        return this;
    }

    public DottedBoard place(Boardable piece) {
        this.command.setRunner(new BoardPlace());
        this.command.found("place");
        this.piece = piece;
        return this;
    }

    public DottedBoard at(int x, int y) {
        this.command.found("at");
        this.at = new Point(x, y);
        if (command.isComplete()) {
            command.getRunner().create(this).run();
        }
        return this;
    }

    public DottedBoard from(int x, int y) {
        this.command.found("from");
        this.from = new Point(x, y);
        if (command.isComplete()) {
            command.getRunner().create(this).run();
        }
        return this;
    }

    public DottedBoard to(int x, int y) {
        this.command.found("to");
        this.to = new Point(x, y);
        if (command.isComplete()) {
            command.getRunner().create(this).run();
        }
        return this;
    }
}
