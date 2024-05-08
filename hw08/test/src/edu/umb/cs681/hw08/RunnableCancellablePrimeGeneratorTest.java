package edu.umb.cs681.hw08;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class RunnableCancellablePrimeGeneratorTest {

    @Test
    public void VerifyPrimeGeneration() {
        RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
        Thread thread = new Thread(gen);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinkedList<Long> primes = gen.getPrimes();
        assertEquals(25, primes.size());
    }

    @Test
    public void VerifyPrimeGenerationStop() {
        RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
        Thread thread = new Thread(gen);
        thread.start();
        gen.setDone(); // Stop the generation
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(gen.getPrimes().size() < 25);
    }
}
