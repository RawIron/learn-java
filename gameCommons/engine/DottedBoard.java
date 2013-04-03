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
import gameCommons.engine.Board;


abstract class BoardCommand {
    protected LinkedList<String> words = new LinkedList<String>();
    protected String name;
    protected String wordSearch;

    public BoardCommand() {
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
    public String name() { return name; }
    public String words() { return wordSearch; }
}

class BoardPlaceCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("place");
        words.add("at");
        name = "place";
        wordSearch = "place at";
    }
}

class BoardRemoveCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("remove");
        words.add("at");
        name = "remove";
        wordSearch = "remove at";
    }
}

class BoardMoveCommand extends BoardCommand {
    @Override
    public void init() {
        words.add("move");
        words.add("from");
        words.add("to");
        name = "move";
        wordSearch = "move from to";
    }
}


interface Runner {
    public void run();
}

class BoardRemove implements Runner {
    private Board board;
    private Boardable piece;
    private Point at;
    public BoardRemove(Board board, Boardable piece, Point at) {
        this.board = board;
        this.piece = piece;
        this.at = at;
    }
    public void run() {
        board.remove(piece, at);
    }
}

class BoardPlace implements Runner {
    private Board board;
    private Boardable piece;
    private Point at;
    public BoardPlace(Board board, Boardable piece, Point at) {
        this.board = board;
        this.piece = piece;
        this.at = at;
    }
    public void run() {
        board.place(piece, at);
    }
}

class BoardMove implements Runner {
    private Board board;
    private Boardable piece;
    private Point from;
    private Point to;
    public BoardMove(Board board, Boardable piece, Point from, Point to) {
        this.board = board;
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
    public void run() {
        board.move(piece, from, to);
    }
}

class CommandMatcher {
    public CommandMatcher() {
        
    }
    public void got(String word) {
    }
    public boolean foundMatch() {
    }
}


public class DottedBoard {
    int x = 0, y = 0;
    Boardable piece = null;
    BoardCommand command = null;
    Runner runner = null;

    public Board remove(Boardable piece) {
        this.piece = piece;
        this.command = new BoardRemoveCommand();
        return this;
    }

    public Board move(Boardable piece) {
        this.piece = piece;
        this.command = new BoardMoveCommand();
        return this;
    }

    public Board place(Boardable piece) {
        this.piece = piece;
        this.command = new BoardPlaceCommand();
        return this;
    }

    public Board at(int x, int y) {
        this.x = x;
        this.y = y;
        if (command.isComplete()) {
            runner.run(this, piece, new Point(x,y));
        } else {
            return this;
        }
    }
    public Board from(int x, int y) {
        if (command.isComplete()) {
            runner.run(this, piece, new Point(x,y), new Point(this.x, this.y));
        } else {
            this.x = x;
            this.y = y;
            return this;
        }
    }
    public Board to(int x, int y) {
        if (command.isComplete()) {
            runner.run(this, piece, new Point(this.x, this.y), new Point(x,y));
        } else {
            this.x = x;
            this.y = y;
            return this;
        }
    }
}

