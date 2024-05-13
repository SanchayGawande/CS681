package edu.umb.cs681.hw18;
import java.util.concurrent.ConcurrentHashMap;

public class LineChartObserver implements Observer<StockEvent> {
    private ConcurrentHashMap<String, Double> lineChartMap = new ConcurrentHashMap<>();

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.println(Thread.currentThread().threadId() + " LineChartObserver Output: (Quote,Ticker)"  + event.quote() + event.ticker() );
    }


    public ConcurrentHashMap<String, Double> getLineChartMap() {
        return lineChartMap;
    }
}
