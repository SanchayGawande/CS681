package edu.umb.cs681.hw15;

import java.util.concurrent.atomic.AtomicBoolean;

public class WithdrawRunnable implements Runnable{

	private ThreadSafeBankAccount2 account;

	private AtomicBoolean done = new AtomicBoolean(false);

	public WithdrawRunnable(ThreadSafeBankAccount2  account){
		this.account=account;
	}

	@Override
	public void run() {
		while(true){
			if(done.get())
			{
				System.out.println("Withdraw Thread:"+Thread.currentThread().threadId()+" Done value is Set to True Stopped!! ");
				return;
			}
			else {
				account.withdraw(100);
				try{
					Thread.sleep(2500);
				} catch (InterruptedException ex) {
					System.out.println("Withdraw Thread:"+Thread.currentThread().threadId()+" Interrupted " + ex.getMessage());

				}
			}
		}
	}

	public void setDone(){
		done.set(true);
	}

}

