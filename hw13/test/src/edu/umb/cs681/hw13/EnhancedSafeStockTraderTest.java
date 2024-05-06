package edu.umb.cs681.hw13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnhancedSafeStockTraderTest {
    private EnhancedSafeStockTrader trader;

    @BeforeEach
    public void setUp() {
        trader = new EnhancedSafeStockTrader();
        EnhancedSafeStockTrader.resetShares(); // Reset shares to initial state before each test
    }

    @Test
    public void testBuyShare() throws InterruptedException {
        Thread t1 = new Thread(trader, "Trader 1");
        t1.start();
        Thread.sleep(20); // Allow some time for the thread to buy some shares
        trader.stopTrading(); // Stop the thread from buying more shares
        t1.join();
        assertTrue(EnhancedSafeStockTrader.getAvailableShares() < 100);
    }

    @Test
    public void testMultipleTradersBuyingShares() throws InterruptedException {
        Thread t1 = new Thread(trader, "Trader 1");
        Thread t2 = new Thread(trader, "Trader 2");
        t1.start();
        t2.start();
        Thread.sleep(50); // Allow time for both threads to operate
        trader.stopTrading();
        t1.join();
        t2.join();
        assertTrue(EnhancedSafeStockTrader.getAvailableShares() < 100);
    }

    @Test
    public void testSellShare() {
        trader.sellShare();
        assertEquals(101, EnhancedSafeStockTrader.getAvailableShares());
    }
}
