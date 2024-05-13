package edu.umb.cs681.hw18;

import java.util.concurrent.atomic.AtomicBoolean;

public class Run implements Runnable {
    private final StockQuoteObservable observable;
    private final String ticker;
    private final double quote;
    private TableObserver tableObserver=new TableObserver();
    private ThreeDObserver threeDObserver=new ThreeDObserver();
    private LineChartObserver lineChartObserver=new LineChartObserver();
    private StockQuoteObservable stockQuoteObservable=new StockQuoteObservable();
    private AtomicBoolean done=new AtomicBoolean(false);

    public Run(StockQuoteObservable observable, String ticker, double quote) {
        this.observable = observable;
        this.ticker = ticker;
        this.quote = quote;
    }

    public void setDone() {
        done.set(true);
    }

    @Override
    public void run() {
        while (true) {
            if (done.get()) {
                System.out.println(Thread.currentThread().threadId() + ": Flag value is Set to True Stopped!!");
                return;
            } else {
                try {
                    stockQuoteObservable.addObserver(tableObserver);
                    stockQuoteObservable.addObserver(threeDObserver);
                    stockQuoteObservable.addObserver(lineChartObserver);
                    stockQuoteObservable.changeQuote("APPL", 126.83);
                    Thread.sleep(4000);
                    return;
                } catch (InterruptedException ex) {
                    System.out.println(Thread.currentThread().threadId() + ": Interrupted " + ex.getMessage());
                }
            }
        }
    }
    public static void main(String[] args) {
        StockQuoteObservable observable = new StockQuoteObservable();
        observable.addObserver(new TableObserver());
        observable.addObserver(new LineChartObserver());
        observable.addObserver(new ThreeDObserver());

        Thread[] threads = new Thread[15];
        Run[] handlers = new Run[15];

        for (int i = 0; i < threads.length; i++) {
            double quote = 100.0 + Math.random() * 10;
            handlers[i] = new Run(observable, "Ticker" + i, quote);
            threads[i] = new Thread(handlers[i]);
            threads[i].start();
        }

        // First step: Attempt to finish threads gracefully
        try {
            Thread.sleep(2000);  // Let threads run for a period
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Request all threads to stop
        for (Run handler : handlers) {
            handler.setDone();
        }

        // Second step: Forcefully stop threads that are still running
        for (Thread thread : threads) {
            thread.interrupt();
            try {
                thread.join(1000);  // Wait for threads to finish after interruption
            } catch (InterruptedException e) {
                System.out.println("Failed to join thread: " + e.getMessage());
            }
        }
        System.out.println("Finished all stock updates.");
    }
}
