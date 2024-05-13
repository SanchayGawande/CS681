package edu.umb.cs681.hw15;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class ThreadSafeBankAccount2 implements BankAccount{
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();

	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().threadId() +
					" (d): current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().threadId() +
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().threadId() +
					" (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().threadId() +
					" (w): current balance: " + balance);
			while(balance <= 0){
				System.out.println(Thread.currentThread().threadId() +
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().threadId() +
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	public double getBalance() { return this.balance; }

	public static void main(String[] args)  {
		ThreadSafeBankAccount2 account2 = new ThreadSafeBankAccount2();
		int counter = 0;
		while (counter < 4){
			DepositRunnable dR =new DepositRunnable(account2);
			WithdrawRunnable wR =new WithdrawRunnable(account2);
			Thread t1= new Thread(dR);
			Thread t2=new Thread(wR);
			t1.start();
			t2.start();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException exception) {
				System.out.println(" Interrupted: " + exception.getMessage());
			}

			dR.setDone();
			wR.setDone();

			try {
				Thread.sleep(200);
			} catch (InterruptedException exception) {
				System.out.println(" Failed Stopping: " + exception.getMessage());
			}
			t1.interrupt();
			t2.interrupt();
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				System.out.println(" Join interrupted: " + e.getMessage());
			}
			counter++;
		}
	}
}
