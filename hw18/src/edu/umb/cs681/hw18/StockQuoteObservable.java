package edu.umb.cs681.hw18;

import java.util.concurrent.ConcurrentHashMap;

public class StockQuoteObservable extends Observable<StockEvent> {
    private ConcurrentHashMap<String, Double> stockPriceQuote = new ConcurrentHashMap<>();

    public void changeQuote(String ticker, double quote) {
        stockPriceQuote.put(ticker, quote);
        notifyObservers(new StockEvent(ticker, quote));
    }

    public ConcurrentHashMap<String, Double> getStockPriceQuote() {
        return stockPriceQuote;
    }
}
