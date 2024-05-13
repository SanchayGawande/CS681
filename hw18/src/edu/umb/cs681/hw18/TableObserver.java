package edu.umb.cs681.hw18;
import java.util.concurrent.ConcurrentHashMap;

public class TableObserver implements Observer<StockEvent> {
    private ConcurrentHashMap<String, Double> tableMap = new ConcurrentHashMap<>();

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println(Thread.currentThread().threadId()+": TableObserver Output: (Quote,Ticker) " + event.ticker() + event.quote());
    }

    public ConcurrentHashMap<String, Double> getTableMap() {
        return tableMap;
    }
}