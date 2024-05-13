package edu.umb.cs681.hw13;

import java.util.concurrent.locks.ReentrantLock;

public class EnhancedSafeStockTrader implements Runnable {
    private static int availableShares = 100; // Shared resource representing available stock shares
    private static final ReentrantLock lock = new ReentrantLock();
    private static volatile boolean done = false;

    @Override
    public void run() {
        while (!done) {
            buyShare();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        }
        System.out.println("flag set to True");
    }

    private void buyShare() {
        lock.lock();
        try {
            if (availableShares > 0) {
                availableShares--; // Decrement shares safely under lock
                System.out.println(Thread.currentThread().getName() + " bought a share. Shares left: " + availableShares);
            }
        } finally {
            lock.unlock();
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
        done = true;
    }

    public static void setDone() {
        EnhancedSafeStockTrader.done = true;
    }

    public static void main(String[] args) throws InterruptedException {
        EnhancedSafeStockTrader trader = new EnhancedSafeStockTrader();
        Thread t1 = new Thread(trader, "Trader 1");
        Thread t2 = new Thread(trader, "Trader 2");
        t1.start();
        t2.start();

        Thread.sleep(3000);
        trader.stopTrading();
        setDone();

        try {
            t1.interrupt(); // Interrupt thread if sleeping or waiting
            t2.interrupt();
            t1.join();
            t2.join();
        } catch (InterruptedException exception){
            System.out.println("interrupted");
        }

    }
}
