package edu.umb.cs681.hw18;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<StockEvent> {
	private final CopyOnWriteArrayList<Observer<StockEvent>> observers = new CopyOnWriteArrayList<>();
	private final ReentrantLock lockObs = new ReentrantLock();

	public void addObserver(Observer<StockEvent> stockEventObserver) {
		lockObs.lock();
		try{
			observers.add(stockEventObserver);
		}finally {
			lockObs.unlock();
		}
	}

	public void removeObserver(Observer<StockEvent> stockEventObserver) {
		lockObs.lock();
		try {
			observers.remove(stockEventObserver);
		}finally {
			lockObs.unlock();
		}
	}

	public void notifyObservers(StockEvent event) {
		Observer<StockEvent>[] localObservers;
		lockObs.lock();
		try {
			localObservers = observers.toArray(new Observer[0]);
		} finally {
			lockObs.unlock();
		}

		for (Observer<StockEvent> observer : localObservers) {
			observer.update((Observable<edu.umb.cs681.hw18.StockEvent>) this, (edu.umb.cs681.hw18.StockEvent) event);
		}
	}
}
