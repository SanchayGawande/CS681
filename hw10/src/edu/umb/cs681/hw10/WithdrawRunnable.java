package edu.umb.cs681.hw10;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class WithdrawRunnable implements Runnable{
	private BankAccount account;
	private static final AtomicBoolean done = new AtomicBoolean(false);

	public WithdrawRunnable(BankAccount account) {
		this.account = account;
	}

	public static void setDone() {
		done.set(true);
	}

	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				if (done.get()) {
					System.out.println("Withdraw operation was stopped.");
					break;
				}
				account.withdraw(100);
				Thread.sleep(Duration.ofSeconds(2));
			}
		} catch (InterruptedException exception) {
			System.out.println("Thread interrupted.");
		}
	}
}
