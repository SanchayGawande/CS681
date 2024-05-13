package edu.umb.cs681.hw10;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadRunnable implements Runnable {
    private static final AtomicBoolean done = new AtomicBoolean(false);
    private ThreadSafeBankAccountOptimistic account;

    public ReadRunnable(ThreadSafeBankAccountOptimistic account) {
        this.account = account;
    }


    public void run() {
        try {
            while (!ReadRunnable.done.get()) {
                System.out.println("Current balance (read): " + account.getBalance());
                Thread.sleep(1000);
            }
        } catch (InterruptedException exception) {
            System.out.println("Thread interrupted");
        }
    }

    public static void setDone() {
        done.set(true);
    }

    public static void main(String[] args) {
            ThreadSafeBankAccountOptimistic bankAccount = new ThreadSafeBankAccountOptimistic();
            DepositRunnable depositRunnable=new DepositRunnable(bankAccount);
            WithdrawRunnable withdrawRunnable=new WithdrawRunnable(bankAccount);
            Thread writer1 = new Thread(depositRunnable);
            Thread writer2 = new Thread(withdrawRunnable);
            Thread reader1=new Thread(new ReadRunnable(bankAccount));

            reader1.start();
            writer1.start();
            writer2.start();


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ReadRunnable.setDone();
            WithdrawRunnable.setDone();
            DepositRunnable.setDone();


        try {
            Thread.sleep(3000);
            reader1.interrupt();
            writer1.interrupt();
            writer2.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            reader1.join();
            writer1.join();
            writer2.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
