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

    public Transaction() {}

    public Transaction give(int added, Accountable lhs) {
        this.added = added;
        this.lhs = lhs;
        return this;
    }
    public Transaction take(int subtracted, Accountable rhs) {
        this.subtracted = subtracted;
        this.rhs = rhs;
        return this;
    }
    public void commit() {
        if (lhs != null) { lhs.add(added); }
        if (rhs != null) { rhs.sub(subtracted); }
        return;
    }
}

