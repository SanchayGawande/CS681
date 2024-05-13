package edu.umb.cs681.hw08;
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


    public static void main(String[] args) {
        System.out.println("36 Factorization Process with flag variable to True");
        RunnableCancellablePrimeFactorizer rcpg1 = new RunnableCancellablePrimeFactorizer(36, 2, (long) Math.sqrt(36));
        Thread thread1 = new Thread(rcpg1);
        System.out.println("Thread #" + thread1.threadId() +
                " performs factorization in the range of " + rcpg1.getFrom() + "->" + rcpg1.getTo());
        thread1.start();
        rcpg1.setDone();
        System.out.println("Final factors: " + rcpg1.getPrimeFactors() + "\n");


        System.out.println("2489 Factorization Process with flag variable to True");
        LinkedList<RunnableCancellablePrimeFactorizer> runnables1 = new LinkedList<RunnableCancellablePrimeFactorizer>();
        LinkedList<Thread> threads1 = new LinkedList<>();
        runnables1.add( new RunnableCancellablePrimeFactorizer(2489, 2, (long)Math.sqrt(2489)/2 ));
        runnables1.add( new RunnableCancellablePrimeFactorizer(2489, 1+(long)Math.sqrt(2489)/2, (long)Math.sqrt(2489) ));

        thread1 = new Thread(runnables1.get(0));
        threads1.add(thread1);
        System.out.println("Thread #" + thread1.threadId() +
                " performs factorization in the range of " + runnables1.get(0).getFrom() + "->" + runnables1.get(0).getTo());

        thread1 = new Thread(runnables1.get(1));
        threads1.add(thread1);
        System.out.println("Thread #" + thread1.threadId() +
                " performs factorization in the range of " + runnables1.get(1).getFrom() + "->" + runnables1.get(1).getTo());

        threads1.forEach( (t)->t.start() );
        runnables1.forEach((r1)->r1.setDone());
        LinkedList<Long> factors3 = new LinkedList<>();
        runnables1.forEach( (factorizer) -> factors3.addAll(factorizer.getPrimeFactors()) );
        System.out.println("Final result: " + factors3);
    }
}
