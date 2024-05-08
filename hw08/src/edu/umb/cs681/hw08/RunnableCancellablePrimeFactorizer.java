package edu.umb.cs681.hw08;
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
            while (dividend != 1 && divisor <= to && !done) {
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
            } catch (InterruptedException e) {
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

        protected boolean isEven(long n) {
            return n % 2 == 0;
        }

        public LinkedList<Long> getPrimeFactors() {
            return factors;
        }
    }




















