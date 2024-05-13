package edu.umb.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private volatile boolean done = false;

	private ReentrantLock lock = new ReentrantLock();
	public RunnableCancellablePrimeGenerator(long from, long to) {
		super(from, to);
	}

	public void setDone() {
		done = true;
	}

	public void generatePrimes() {
		for (long n = from; n <= to; n++) {
			if (done) {
				System.out.println("Stopped generating prime numbers.");
				break;
			}
			lock.lock();
			try {
				if (isPrime(n)) {
					this.primes.add(n);
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public void run() {
		try {
			for (long n = from; n <= to && !done; n++) {
				if (isPrime(n)) {
					primes.add(n);
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted.");
		}
		System.out.println("Primes generation was stopped. Generated primes: " + primes);
	}

	public static void main(String[] args) {
		RunnableCancellablePrimeGenerator generator = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(generator);
		thread.start();
		try {
			Thread.sleep(300);
			generator.setDone();
			thread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Final primes: " + generator.getPrimes());
	}
}
