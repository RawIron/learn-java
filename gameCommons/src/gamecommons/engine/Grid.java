package gamecommons.engine;


public class Grid {
    private Board grid = null;

    public Grid(int horizontalLines, int verticalLines) {
        this.grid = new Board(horizontalLines * horizontalLines,
                              verticalLines * verticalLines);
    }

    public Piece northIs() {
        return null;
    }
    public Piece southIs() {
        return null;
    }
    public Piece eastIs() {
        return null;
    }
    public Piece westIs() {
        return null;
    }
}
