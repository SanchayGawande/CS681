package edu.umb.cs681.hw08;
import java.util.LinkedList;
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




	public static void main(String[] args) {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(gen);
		thread.start();
		gen.setDone();
		gen.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
		System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found.");
	}


}
