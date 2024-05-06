package edu.umb.cs681.hw13;

import java.util.concurrent.locks.ReentrantLock;

public class EnhancedSafeStockTrader implements Runnable {
    private static int availableShares = 100; // Shared resource representing available stock shares
    private static final ReentrantLock lock = new ReentrantLock();
    private volatile boolean done = false; // Volatile flag for controlling thread termination

    @Override
    public void run() {
        while (!done) {
            buyShare();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    private void buyShare() {
        lock.lock();
        try {
            if (availableShares > 0) {
                availableShares--; // Decrement shares safely under lock
                System.out.println(Thread.currentThread().getName() + " bought a share. Shares left: " + availableShares);
            }
        } finally {
            lock.unlock(); // Unlocking in case of exceptions
        }
    }

    void sellShare() {
        lock.lock();
        try {
            availableShares++; // Increment shares safely under lock
            System.out.println(Thread.currentThread().getName() + " sold a share. Shares available: " + availableShares);
        } finally {
            lock.unlock();
        }
    }

    public void stopTrading() {
        done = true; // Signal to stop trading
    }

    public static synchronized int getAvailableShares() {
        return availableShares;
    }

    public static synchronized void resetShares() {
        availableShares = 100;
    }

    public static void main(String[] args) throws InterruptedException {
        EnhancedSafeStockTrader trader = new EnhancedSafeStockTrader();
        Thread t1 = new Thread(trader, "Trader 1");
        Thread t2 = new Thread(trader, "Trader 2");
        t1.start();
        t2.start();

        Thread.sleep(100);
        trader.stopTrading();
        t1.interrupt(); // Interrupt thread if sleeping or waiting
        t2.interrupt();
        t1.join(); // Wait for threads to finish
        t2.join();
    }
}
