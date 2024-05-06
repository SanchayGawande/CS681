package edu.umb.cs681.hw12;
import java.util.concurrent.atomic.AtomicReference;

public class Aircraft {
    private AtomicReference<Position> position;

    public Aircraft(Position pos) {
        this.position = new AtomicReference<>(pos);
    }

    public void setPosition(double newLat, double newLong, double newAlt) {
        position.updateAndGet(current -> new Position(newLat, newLong, newAlt));
    }

    public Position getPosition() {
        return position.get();
    }

    public static void main(String[] args) {
        Aircraft aircraft = new Aircraft(new Position(42.3601, -71.0589, 10));
        Runnable task = () -> {
            aircraft.setPosition(37.7749, -122.4194, 20);
            System.out.println(Thread.currentThread().getName() + " Updated Position: " + aircraft.getPosition());
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted: " + thread.getName());
            }
        }
        System.out.println("All threads have completed execution.");
    }

}

