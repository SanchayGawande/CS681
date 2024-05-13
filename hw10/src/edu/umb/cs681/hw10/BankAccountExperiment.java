package edu.umb.cs681.hw10;
import java.util.concurrent.atomic.AtomicBoolean;

public class BankAccountExperiment {
    private static final int MAX_READERS = 6;

    public static void main(String[] args) {
        System.out.println("Pessimistic Locking Test:");
        runTest(false);

        System.out.println("\nOptimistic Locking Test:");
        runTest(true);
    }

    private static void runTest(boolean useOptimistic) {
        for (int numReaders = 1; numReaders <= MAX_READERS; numReaders++) {
            ThreadSafeBankAccount account = useOptimistic ?
                    new ThreadSafeBankAccountOptimistic() : new ThreadSafeBankAccount();
            AtomicBoolean done = new AtomicBoolean(false);
            Thread[] readers = new Thread[numReaders];
            long startTime = System.nanoTime();

            for (int i = 0; i < numReaders; i++) {
                readers[i] = new Thread(() -> {
                    try {
                        while (!done.get()) {
                            Thread.sleep(100); // Reduced sleep for quicker access
                        }
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted");
                    } finally {
                        System.out.println(Thread.currentThread().getName() + " terminating.");
                    }
                });
                readers[i].start();
            }

            // Allow threads to read for a certain period and then signal them to stop
            try {
                Thread.sleep(2000); // Increase the running time to allow more reads
                done.set(true);
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }

            // Wait for all threads to finish
            for (Thread t : readers) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    System.out.println("Reader thread interrupted.");
                }
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            System.out.println("Readers: " + numReaders + ", Time taken: " + duration + " ms");
        }
    }
}
