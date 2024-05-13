package edu.umb.cs681.hw15;

import java.util.concurrent.atomic.AtomicBoolean;

public class DepositRunnable implements Runnable {

	private ThreadSafeBankAccount2  account;

	private AtomicBoolean done = new AtomicBoolean(false);

	public DepositRunnable(ThreadSafeBankAccount2  account){
		this.account=account;
	}

	@Override
	public void run() {
		while(true){
			if(done.get())
			{
				System.out.println("Deposit Thread:"+Thread.currentThread().threadId()+" Done value is Set to True Stopped!! ");
				return;
			}
			else {
				account.deposit(100);
				try{
					Thread.sleep(2500);
				} catch (InterruptedException exception) {
					System.out.println("Deposit Thread:"+Thread.currentThread().threadId()+" Interrupted " + exception.getMessage());
				}
			}
		}
	}

	public void setDone(){
		done.set(true);
	}
}
