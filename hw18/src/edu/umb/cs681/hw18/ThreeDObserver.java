package edu.umb.cs681.hw18;

import java.util.concurrent.ConcurrentHashMap;

public class ThreeDObserver implements Observer<StockEvent> {
    private ConcurrentHashMap<String, Double> threeDMap = new ConcurrentHashMap<>();

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println(Thread.currentThread().threadId()+": ThreeDObserver Output: (Quote,Ticker) " + event.ticker() + event.quote());
    }

    public ConcurrentHashMap<String, Double> getThreeDMap() {
        return threeDMap;
    }
}