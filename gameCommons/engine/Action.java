
interface Action {
    public void doIt();
}

abstract class Action {
    public abstract void doIt();
}


class StorageMover {
    public void moveIt() {
        Board board = new Board();
        Boardable building = new Building();
        Inventory storage = new Storage();

        board.remove(building).at(2,3);
        storage.packAway(building);
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
