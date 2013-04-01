
interface Action {
    public void doIt();
}

abstract class Action {
    public abstract void doIt();
}


// mover.on(board).take(building.at(2,3)).putInto(storage)
class StorageMover {
    private int x;
    private int y;
    private Board board;
    private Boardable item;
    private Inventory storage;

    public StorageMover() {}

    public void on(Board board) {}
    public void take(Boardable Item, int x, int y) {}
    pubic void putInto(Inventory storage) {}

    public void moveIt() {
        board.remove(item.at(x,y));
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
