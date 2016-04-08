package gamecommons.engine;

/**
 buy(inventory, coins)
 transaction.give(inventory).take(coins).commit()
 sell(inventory, coins)
 transaction.give(coins).take(inventory).commit()
 */


interface Accountable {
    public void add(int quantity);
    public void sub(int quantity);
    public int balance();
}


class CoinsAccount implements Accountable {
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


class BuildingAccount implements Accountable {
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


public class Transfer {
    private Accountable lhs = null, rhs = null;
    private int added = 0, subtracted = 0;
    private boolean giveWasCalled = false;
    private boolean takeWasCalled = false;

    public Transfer() {}

    public Transfer give(int added, Accountable lhs) {
        if (giveWasCalled) {
            return null;
        }
        this.added = added;
        this.lhs = lhs;
        giveWasCalled = true;
        return this;
    }
    public Transfer take(int subtracted, Accountable rhs) {
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
