package edu.umb.cs681.hw06;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
    private boolean done = false;
    private ReentrantLock lock = new ReentrantLock();

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        lock.lock();
        try {
            done = true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void generatePrimeFactors() {
        long divisor = from;
        while (dividend != 1 && divisor <= to && !done) {
            lock.lock();
            try {
                if (done) {
                    System.out.println("Stopped generating prime factors.");
                    return;
                }
                if (divisor > 2 && isEven(divisor)) {
                    divisor++;
                    continue;
                }
                if (dividend % divisor == 0) {
                    factors.add(divisor);
                    dividend /= divisor;
                } else {
                    if (divisor == 2) {
                        divisor++;
                    } else {
                        divisor += 2;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }


    @Override
    public void run() {
        generatePrimeFactors();
        System.out.println("Thread #" + Thread.currentThread().getId() + " generated " + factors);
    }

public static void main(String[] args) {
    RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
    Thread thread = new Thread(factorizer);
    thread.start();
    try {
        Thread.sleep(1000); // Sleep for 1 second
    } catch(InterruptedException e) {
        e.printStackTrace();
    }
    factorizer.setDone();
    try {
        thread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("Factors are: " + factorizer.getPrimeFactors());
}
}
