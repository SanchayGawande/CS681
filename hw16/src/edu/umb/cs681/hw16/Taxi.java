package edu.umb.cs681.hw16;
import java.awt.Point;
import java.util.concurrent.locks.ReentrantLock;
class Taxi implements Runnable {
    private Point location;
    private Point destination;
    private Dispatcher dispatcher;
    private final ReentrantLock lock = new ReentrantLock();

    public Taxi(Dispatcher dispatcher, Point initialLocation, Point initialDestination) {
        this.dispatcher = dispatcher;
        this.location = initialLocation;
        this.destination = initialDestination; // New: Initialize with a destination
        this.dispatcher.registerTaxi(this);
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Point getLocation() {
        lock.lock();
        try {
            return location;
        } finally {
            lock.unlock();
        }
    }

    public void setLocation(Point location) {
        lock.lock();
        try {
            this.location = location;
            if (location.equals(destination)) {
                System.out.println("Taxi at " + location + " is now available.");
                dispatcher.notifyAvailable(this);
            }
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        try {
            Thread.sleep(1000);
            setLocation(destination);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) {
        Display display = new Display();
        Dispatcher dispatcher = new Dispatcher(display);

        Taxi taxi1 = new Taxi(dispatcher, new Point(10, 10), new Point(10, 10));
        Taxi taxi2 = new Taxi(dispatcher, new Point(20, 20), new Point(20, 20));

        Thread t1 = new Thread(taxi1);
        Thread t2 = new Thread(taxi2);

        t1.start();
        t2.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        dispatcher.dispatchTaxi(new Point(15, 15));

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        dispatcher.displayAvailableTaxis(); // Display available taxis after dispatching
    }
}
