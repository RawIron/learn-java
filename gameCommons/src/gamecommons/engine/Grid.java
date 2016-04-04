
package gamecommons.engine;

import gamecommons.engine.Board;


public class Grid {
    private Board grid = null;

    public Grid(int horizontalLines, int verticalLines) {
        this.grid = new Board(horizontalLines * horizontalLines,
                              verticalLines * verticalLines);
    }

    public northIs() {
    }
    public southIs() {
    }
    public eastIs() {
    }
    public westIs() {
    }
}
