package edu.umb.cs681.hw09;

import java.util.LinkedList;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
    private volatile boolean done = false;

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        done = true;
    }

    @Override
    public void generatePrimeFactors() {
        long divisor = from;
        try {
            while (dividend != 1 && divisor <= to && !done) {
                if (divisor > 2 && isEven(divisor)) {
                    divisor++;
                    continue;
                }
                if (dividend % divisor == 0) {
                    factors.add(divisor);
                    dividend /= divisor;
                } else {
                    divisor += (divisor == 2) ? 1 : 2;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }
        System.out.println("Factorization was stopped. Factors: " + factors);
    }

    protected boolean isEven(long n) {
        return n % 2 == 0;
    }

    public LinkedList<Long> getPrimeFactors() {
        return factors;
    }

    public static void main(String[] args) {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            Thread.sleep(1000);
            factorizer.setDone();
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Final factors: " + factorizer.getPrimeFactors());
    }
}
