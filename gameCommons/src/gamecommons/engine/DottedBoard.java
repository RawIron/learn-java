package gamecommons.engine;

/**
 board.place(building).at(2,3)
 board.at(2,3).place(building)

 board.move(building).from(2,3).to(4,5)
 board.move(building, from, to)

 move.is(place(building).at(2,3))
 mover.make(move)
 */


import java.util.LinkedList;


abstract class BoardCommand {
    protected LinkedList<String> words = new LinkedList<String>();
    protected String name;
    protected String wordSearch;
    private Runner command;

    public String name() { return name; }
    public String words() { return wordSearch; }
    public Runner runner() {
        return this.command;
    }

    public BoardCommand(Runner command) {
        this.command = command;
        init();
    }

    public abstract void init();

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
    public BoardPlaceCommand(BoardPlace command) {
        super(command);
    }

    @Override
    public void init() {
        words.add("place");
        words.add("at");
        name = "place";
        wordSearch = "place at";
    }
}

class BoardRemoveCommand extends BoardCommand {
    public BoardRemoveCommand(BoardRemove command) {
        super(command);
    }

    @Override
    public void init() {
        words.add("remove");
        words.add("at");
        name = "remove";
        wordSearch = "remove at";
    }
}

class BoardMoveCommand extends BoardCommand {
    public BoardMoveCommand(BoardMove command) {
        super(command);
    }

    @Override
    public void init() {
        words.add("move");
        words.add("from");
        words.add("to");
        name = "move";
        wordSearch = "move from to";
    }
}


class BoardRemove implements Runner {
    private DottedBoard board;
    private Boardable piece;
    private Point at;

    public Runner createRunner(DottedBoard board) {
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

    public Runner createRunner(DottedBoard board) {
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

    public Runner createRunner(DottedBoard board) {
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

class CommandMatcher {

    public CommandMatcher() {}
    public void got(String word) {
    }

    public boolean foundMatch() {
        return false;
    }
}

interface Runner {
    public void run();
}


public class DottedBoard extends Board
{
    public Point to = null;
    public Point from = null;
    public Point at = null;

    public Boardable piece = null;
    public BoardCommand command = null;
    public Runner runner = null;

    public DottedBoard remove(Boardable piece) {
        this.piece = piece;
        this.command = new BoardRemoveCommand(new BoardRemove());
        return this;
    }

    public DottedBoard move(Boardable piece) {
        this.piece = piece;
        this.command = new BoardMoveCommand(new BoardMove());
        return this;
    }

    public DottedBoard place(Boardable piece) {
        this.piece = piece;
        this.command = new BoardPlaceCommand(new BoardPlace());
        return this;
    }

    public DottedBoard at(int x, int y) {
        this.at = new Point(x, y);
        if (command.isComplete()) {
            runner.run();
        }
        return this;
    }

    public DottedBoard from(int x, int y) {
        this.from = new Point(x, y);
        if (command.isComplete()) {
            runner.run();
        }
        return this;
    }

    public DottedBoard to(int x, int y) {
        this.to = new Point(x, y);
        if (command.isComplete()) {
            runner.run();
        }
        return this;
    }
}
