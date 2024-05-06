package edu.umb.cs681.hw06;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class RunnableCancellablePrimeGeneratorTest {
    private RunnableCancellablePrimeGenerator generator;
    private Thread thread;

    @BeforeEach
    public void setUp() {
        generator = new RunnableCancellablePrimeGenerator(1, 100);
        thread = new Thread(generator);
    }

    @AfterEach
    public void cleanUp() throws InterruptedException {
        generator.setDone();
        thread.join();
    }

    @Test
    public void testPrimesGeneratedCorrectly() throws InterruptedException {
        thread.start();
        thread.join();
        LinkedList<Long> primes = generator.getPrimes();
        assertTrue(primes.contains(2L));
        assertTrue(primes.contains(3L));
        assertTrue(primes.contains(7L));
        assertTrue(primes.contains(11L));
        assertTrue(primes.contains(41L));
        assertTrue(primes.contains(97L));
    }

    @Test
    public void testNoPrimesAfterStop() throws InterruptedException {
        thread.start();
        generator.setDone();
        thread.join();
        LinkedList<Long> primes = generator.getPrimes();
        assertEquals(0, primes.size());
    }
}
