/*
    buy(inventory, coins)
    transaction.give(inventory).take(coins)
    sell(inventory, coins)
    transaction.give(coins).take(inventory)
 */

package gameCommons.engine;


interface Accountable {
    public void add(int quantity);
    public void sub(int quantity);
    public int balance();
}


class CoinsWallet implements Accountable {
    private int balance = 0;

    public void add(int quantity) {
        balance += quantity;
    }
    public void sub(int quantity) {
        balance -= quantity;
    }
    public int balance() {
        return balance;
    }
}

class BuildingInventory implements Accountable {
    private int balance = 0;

    public void add(int quantity) {
        balance += quantity;
    }
    public void sub(int quantity) {
        balance -= quantity;
    }
    public int balance() {
        return balance;
    }
}

public class Transaction {
    private Accountable lhs = null, rhs = null;
    private int added = 0, subtracted = 0;
    private boolean giveWasCalled = false;
    private boolean takeWasCalled = false;

    public Transaction() {}

    public Transaction give(int added, Accountable lhs) {
        if (giveWasCalled) {
            return null;
        }
        this.added = added;
        this.lhs = lhs;
        giveWasCalled = true;
        return this;
    }
    public Transaction take(int subtracted, Accountable rhs) {
        if (takeWasCalled) {
            return null;
        }
        this.subtracted = subtracted;
        this.rhs = rhs;
        takeWasCalled = true;
        return this;
    }
    public void commit() {
        if (lhs != null) { lhs.add(added); }
        if (rhs != null) { rhs.sub(subtracted); }
        reset();
    }
    public void rollback() {
        reset();
    }

    private void reset() {
        lhs = rhs = null;
        added = subtracted = 0;
        giveWasCalled = takeWasCalled = false;
    }
}

