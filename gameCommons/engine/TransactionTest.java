
package gameCommons.engine;

import junit.framework.TestCase;


public class TransactionTest extends TestCase {

    public final void test_give100Coins() {
        Accountable coins = new CoinsWallet();
        coins.add(100);
        assertEquals(coins.balance(), 100);
    }
}
