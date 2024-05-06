package edu.umb.cs681.hw13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnhancedStockTraderTest {
    private EnhancedStockTrader trader;

    @BeforeEach
    void setUp() {
        trader = new EnhancedStockTrader();
        EnhancedStockTrader.resetShares(); // Reset to initial condition before each test
    }

    @Test
    void testBuyShare() {
        trader.run();  // Simulate a single thread running the buy operation
        assertTrue(EnhancedStockTrader.getAvailableShares() < 100);
    }

    @Test
    void testConcurrentBuyShare() throws InterruptedException {
        Thread t1 = new Thread(trader, "Trader 1");
        Thread t2 = new Thread(trader, "Trader 2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertTrue(EnhancedStockTrader.getAvailableShares() < 100);
    }

    @Test
    void testSellShare() {
        trader.sellShare(); // Directly test the sell functionality
        assertEquals(101, EnhancedStockTrader.getAvailableShares());
    }
}
