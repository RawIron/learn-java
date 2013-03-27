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
    public Transaction() {}

    public Transaction give(int added, Accountable lhs) {
        return this;
    }
    public Transaction take(int subtracted, Accountable rhs) {
        return this;
    }
    public void commit() {
        return;
    }
}
