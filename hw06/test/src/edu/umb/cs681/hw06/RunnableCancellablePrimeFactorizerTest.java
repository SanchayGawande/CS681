package edu.umb.cs681.hw06;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RunnableCancellablePrimeFactorizerTest {
    private RunnableCancellablePrimeFactorizer factorizer;
    private Thread thread;

    @BeforeEach
    public void setUp() {
        factorizer = new RunnableCancellablePrimeFactorizer(36, 2, (long)Math.sqrt(36));
        thread = new Thread(factorizer);
    }

    @AfterEach
    public void cleanUp() throws InterruptedException {
        factorizer.setDone();
        thread.join();
    }

    @Test
    public void testPrimeFactorsCorrectlyGenerated() throws InterruptedException {
        thread.start();
        thread.join();
        assertIterableEquals(List.of(2L, 2L, 3L, 3L), factorizer.getPrimeFactors());
    }
}
