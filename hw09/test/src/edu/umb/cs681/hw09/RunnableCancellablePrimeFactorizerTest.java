package edu.umb.cs681.hw09;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class RunnableCancellablePrimeFactorizerTest {

    @Test
    public void verifyFactorization() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(84, 2, 84);
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            fail("Thread was interrupted");
        }
        LinkedList<Long> factors = factorizer.getPrimeFactors();
        Long[] expectedFactors = {2L, 2L, 3L, 7L};
        assertArrayEquals(expectedFactors, factors.toArray());
    }

    @Test
    public void VerifyThreadTerminationBeforeCompletion() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(36, 2, 36);
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            Thread.sleep(500);
            factorizer.setDone();
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
            fail("Test was interrupted unexpectedly.");
        }
        assertTrue(factorizer.getPrimeFactors().size() < 4, "Factorization should have stopped early, factors found: " + factorizer.getPrimeFactors().size());
    }

    @Test
    public void VerifyImmediateStop() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(100, 2, 100);
        Thread thread = new Thread(factorizer);
        thread.start();
        factorizer.setDone();
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            fail("Test was interrupted unexpectedly.");
        }
        assertTrue(factorizer.getPrimeFactors().isEmpty());
    }
}
