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
            System.out.println(Thread.currentThread().getName() + " Intial Position: " + aircraft.getPosition());
            Position intialPosition=aircraft.getPosition();
            Position changedPosition=aircraft.getPosition().change(39.7749, -166.4194, 50);
            System.out.println(Thread.currentThread().getName() + " Changing position coordinates: "+changedPosition.coordinates());
            aircraft.setPosition(changedPosition.coordinates().get(0),(changedPosition.coordinates().get(1)),(changedPosition.coordinates().get(2)));
            System.out.println(Thread.currentThread().getName() + " Updated Position: "+aircraft.getPosition());
            System.out.println(Thread.currentThread().getName() + " higher altitude boolean comparison :"+aircraft.getPosition().higherAltThan(intialPosition));
            System.out.println(Thread.currentThread().getName() + " lower altitude boolean comparison :"+aircraft.getPosition().lowerAltThan(intialPosition));
            System.out.println(Thread.currentThread().getName() + " north of boolean comparison :"+aircraft.getPosition().northOf(intialPosition));
            System.out.println(Thread.currentThread().getName() + " south of boolean comparison :"+aircraft.getPosition().southOf(intialPosition));
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All threads have completed execution");
    }

}

