package edu.umb.cs681.hw10;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeBankAccount implements BankAccount {
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();

	public void deposit(double amount) {
		lock.lock();
		try {
			System.out.println("Lock obtained");
			System.out.print("Current balance (d): " + balance);
			balance += amount;
			System.out.println(", New balance (d): " + balance);
		} finally {
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void withdraw(double amount) {
		lock.lock();
		try {
			System.out.println("Lock obtained");
			System.out.print("Current balance (w): " + balance);
			balance -= amount;
			System.out.println(", New balance (w): " + balance);
		} finally {
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public double getBalance() {
		lock.lock();
		try {
			return balance;
		} finally {
			lock.unlock();
		}
	}


	public static void main(String[] args){
		ThreadSafeBankAccount bankAccount = new ThreadSafeBankAccount();
		new Thread( new DepositRunnable(bankAccount) ).start();
		new Thread( new WithdrawRunnable(bankAccount) ).start();
	}
}
