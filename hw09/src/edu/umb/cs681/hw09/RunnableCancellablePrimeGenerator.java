package edu.umb.cs681.hw09;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private volatile boolean done = false;

	public RunnableCancellablePrimeGenerator(long from, long to) {
		super(from, to);
	}

	public void setDone() {
		done = true;
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

	protected boolean isPrime(long n) {
		if (n <= 1) return false;
		if (n == 2) return true;
		if (n % 2 == 0) return false;
		for (long i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		RunnableCancellablePrimeGenerator generator = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(generator);
		thread.start();
		try {
			Thread.sleep(3000);
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
