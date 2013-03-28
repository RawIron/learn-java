
package gameCommons.engine;

import junit.framework.TestCase;


public class TransactionTest extends TestCase {

    public final void test_give100Coins() {
        Accountable coins = new CoinsWallet();
        coins.add(100);
        assertEquals(coins.balance(), 100);
    }

    public final void test_take100Coins() {
        Accountable coins = new CoinsWallet();
        coins.sub(100);
        assertEquals(coins.balance(), -100);
    }

    public final void test_giveAndtake100Coins() {
        Accountable coins = new CoinsWallet();
        coins.add(100);
        coins.sub(100);
        assertEquals(coins.balance(), 0);
    }

    public final void test_transactionGiveBuildingTakeCoinsRollback() {
        Accountable coins = new CoinsWallet();
        Accountable building = new BuildingInventory();
        Transaction t = new Transaction();
        t.give(1, building).take(100, coins);
        assertEquals(coins.balance(), 0);
        assertEquals(building.balance(), 0);
    }

    public final void test_transactionGiveBuildingTakeCoinsCommit() {
        Accountable coins = new CoinsWallet();
        Accountable building = new BuildingInventory();
        Transaction t = new Transaction();
        t.give(1, building).take(100, coins).commit();
        assertEquals(coins.balance(), -100);
        assertEquals(building.balance(), 1);
    }

    public final void test_transactionGiveBuildingCommit() {
        Accountable building = new BuildingInventory();
        Transaction t = new Transaction();
        t.give(1, building).commit();
        assertEquals(building.balance(), 1);
    }

    public final void test_transactionTakeCoinsCommit() {
        Accountable coins = new CoinsWallet();
        Transaction t = new Transaction();
        t.take(100, coins).commit();
        assertEquals(coins.balance(), -100);
    }

    public final void test_transactionTakeCoinsTakeCoins() {
        Accountable coins = new CoinsWallet();
        Transaction t = new Transaction();
        assertNull(t.take(100, coins).take(50, coins));
    }

    public final void test_transactionGiveCoinsGiveCoins() {
        Accountable coins = new CoinsWallet();
        Transaction t = new Transaction();
        assertNull(t.give(100, coins).give(50, coins));
    }

    public final void test_twoTransactionsGiveBuildingTakeCoinsCommit() {
        Accountable coins = new CoinsWallet();
        Accountable building = new BuildingInventory();
        Transaction t = new Transaction();
        t.give(1, building).take(100, coins).commit();
        assertEquals(coins.balance(), -100);
        assertEquals(building.balance(), 1);
        t.give(2, building).take(200, coins).commit();
        assertEquals(coins.balance(), -300);
        assertEquals(building.balance(), 3);
    }

    public final void test_twoTransactionsGiveBuildingRollbackTakeCoinsCommit() {
        Accountable coins = new CoinsWallet();
        Accountable building = new BuildingInventory();
        Transaction t = new Transaction();
        t.give(1, building);
        t.rollback();
        assertEquals(coins.balance(), 0);
        assertEquals(building.balance(), 0);
        t.take(200, coins).commit();
        assertEquals(coins.balance(), -200);
        assertEquals(building.balance(), 0);
    }

}
