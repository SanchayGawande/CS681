package edu.umb.cs681.hw08;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class RunnableCancellablePrimeFactorizerTest {

    @Test
    public void VerifyPrimeFactorization() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(84, 2, 84);
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinkedList<Long> factors = factorizer.getPrimeFactors();
        assertArrayEquals(new Long[]{2L, 2L, 3L, 7L}, factors.toArray());
    }

    @Test
    public void VerifyPrimeFactorizationStop() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
        Thread thread = new Thread(factorizer);
        thread.start();
        factorizer.setDone();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(factorizer.getPrimeFactors().size() < 4);
    }

    @Test
    public void VerifyFactorizationOfPrimeNumber() {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(13, 2, 13);
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, factorizer.getPrimeFactors().size());
        assertEquals(Long.valueOf(13), factorizer.getPrimeFactors().getFirst());
    }
}
