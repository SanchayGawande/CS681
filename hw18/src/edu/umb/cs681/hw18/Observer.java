package edu.umb.cs681.hw18;

public interface Observer<StockEvent> {
	 void update(Observable<edu.umb.cs681.hw18.StockEvent> sender, edu.umb.cs681.hw18.StockEvent event);
}
