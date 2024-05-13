package edu.umb.cs681.hw13;
public class EnhancedStockTrader implements Runnable {
    private static int availableShares = 100;


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            buyShare();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void buyShare() {
        if (availableShares > 0) {
            availableShares--; // Decrement shares unsafely
            System.out.println(Thread.currentThread().getName() + " bought a share. Shares left: " + availableShares);
        }
    }

    void sellShare() {
        availableShares++; // Increment shares unsafely
        System.out.println(Thread.currentThread().getName() + " sold a share. Shares available: " + availableShares);
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new EnhancedStockTrader(), "Trader 1");
        Thread t2 = new Thread(new EnhancedStockTrader(), "Trader 2");
        t1.start();
        t2.start();
    }
}
