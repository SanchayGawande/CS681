package edu.umb.cs681.hw16;
import java.awt.Point;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

class Dispatcher {
    private HashSet<Taxi> taxis; // All taxis
    private HashSet<Taxi> availableTaxis; // Taxis available/ready to pick up customers
    private Display display; // Assuming Display is some class for visual output
    private final ReentrantLock lock = new ReentrantLock();

    public Dispatcher(Display display) {
        this.taxis = new HashSet<>();
        this.availableTaxis = new HashSet<>();
        this.display = display;
    }

    public void registerTaxi(Taxi taxi) {
        lock.lock();
        try {
            taxis.add(taxi);
        } finally {
            lock.unlock();
        }
    }

    public void notifyAvailable(Taxi taxi) {
        lock.lock();
        try {
            if (!availableTaxis.contains(taxi)) {
                availableTaxis.add(taxi);
            }
        } finally {
            lock.unlock();
        }
    }

    public void dispatchTaxi(Point destination) {
        lock.lock();
        try {
            Taxi assignedTaxi = findNearestAvailableTaxi(destination);
            if (assignedTaxi != null) {
                assignedTaxi.setDestination(destination);
                availableTaxis.remove(assignedTaxi);
                System.out.println("Taxi dispatched to " + destination);
            } else {
                System.out.println("No available taxis");
            }
        } finally {
            lock.unlock();
        }
    }

    private Taxi findNearestAvailableTaxi(Point destination) {
        return availableTaxis.isEmpty() ? null : availableTaxis.iterator().next(); // Simplified
    }

    public void displayAvailableTaxis() {
        lock.lock();
        try {
            for (Taxi taxi : availableTaxis) {
                display.draw(taxi.getLocation());
            }
        } finally {
            lock.unlock();
        }
    }
}