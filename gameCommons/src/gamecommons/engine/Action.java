package gamecommons.engine;


interface IAction {
    public void doIt();
}

abstract class Action {
    public abstract void doIt();
}


// mover.on(board.at(2,3)).take(building).stashItInto(storage)
class StorageMover {
    private int x;
    private int y;
    private Board board;
    private Boardable item;
    private StoreHouse storage;

    public StorageMover() {}

    public void on(gamecommons.engine.Board board) {}
    public void take(Boardable Item, int x, int y) {}
    public void stashItInto(StoreHouse storage) {}
    public void trashIt() {}

    public void moveIt() {
        board.remove(item.at(x,y), new Point(x, y));
        storage.packAway(item);
    }
}


class MoveIntoStorage extends Action {
    private StorageMover mover;
    public MoveIntoStorage(StorageMover m) {
        this.mover = m;
    }

    public void doIt() {
        mover.moveIt();
    }
}
