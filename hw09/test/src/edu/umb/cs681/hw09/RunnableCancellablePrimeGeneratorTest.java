package edu.umb.cs681.hw09;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class RunnableCancellablePrimeGeneratorTest {

    @Test
    public void verifyPrimeNumbersGeneration() {
        RunnableCancellablePrimeGenerator generator = new RunnableCancellablePrimeGenerator(1, 100);
        Thread t = new Thread(generator);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            fail("Thread was interrupted");
        }
        LinkedList<Long> primes = generator.getPrimes();
        assertEquals(primes.size(), 25);
    }

    @Test
    public void VerifyInterruption() {
        RunnableCancellablePrimeGenerator generator = new RunnableCancellablePrimeGenerator(1, 100);
        Thread t = new Thread(generator);
        t.start();
        try {
            Thread.sleep(10);
            generator.setDone();
            t.interrupt();
            t.join();
        } catch (InterruptedException e) {
            fail("Test was interrupted unexpectedly.");
        }
        assertTrue(generator.getPrimes().size() < 25);
    }

    @Test
    public void VerifyImmediateStop() {
        RunnableCancellablePrimeGenerator generator = new RunnableCancellablePrimeGenerator(1, 100);
        Thread t = new Thread(generator);
        t.start();
        generator.setDone();
        t.interrupt();
        try {
            t.join();
        } catch (InterruptedException e) {
            fail("Test was interrupted unexpectedly.");
        }
        assertTrue(generator.getPrimes().isEmpty());
    }
}
