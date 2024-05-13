package edu.umb.cs681.hw09;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
    private volatile boolean done = false;
    private ReentrantLock rlock = new ReentrantLock();

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        done = true;
    }

    @Override
    public void generatePrimeFactors() {
        long divisor = from;
        while (dividend != 1 && divisor <= to && !done) {
            if (done) {
                System.out.println("Prime factors generation is Stopped!");
                return;
            }
            rlock.lock();
            try {
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
                rlock.unlock();
            }
        }

    }

    protected boolean isEven(long n) {
        return n % 2 == 0;
    }

    public LinkedList<Long> getPrimeFactors() {
        return factors;
    }

    public static void main(String[] args) {

        System.out.println("36 Factorization Process with 2 step termination");
        RunnableCancellablePrimeFactorizer rcpg1 = new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
        Thread thread1 = new Thread(rcpg1);
        System.out.println("Thread #" + thread1.threadId() +
                " performs factorization in the range of " + rcpg1.getFrom() + "->" + rcpg1.getTo());
        thread1.start();

        try {
            rcpg1.setDone();
            thread1.interrupt();
            System.out.println("isInterrupt value is: "+thread1.isInterrupted());
            thread1.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt .....");
        }
        System.out.println("Final factors: " + rcpg1.getPrimeFactors() + "\n");

    }
}
