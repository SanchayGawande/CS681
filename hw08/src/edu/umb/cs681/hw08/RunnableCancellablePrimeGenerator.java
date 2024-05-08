package edu.umb.cs681.hw08;
import java.util.LinkedList;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private volatile boolean done = false;

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
				this.primes.clear();
				break;
			}
			if (isPrime(n)) {
				this.primes.add(n);
			}
		}
	}

	public static void main(String[] args) {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(gen);
		thread.start();
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gen.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));
		System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found.");
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

	public LinkedList<Long> getPrimes() {
		return primes;
	}
}
