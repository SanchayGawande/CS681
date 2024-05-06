package edu.umb.cs681.hw12;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class AircraftTest {
    @Test
    void verifyPositionImmutable() {
        Position position = new Position(10, 20, 30);
        Position newPosition = position.change(11, 21, 31);

        assertNotSame(position, newPosition);
        assertEquals(10, position.latitude());
        assertEquals(20, position.longitude());
        assertEquals(30, position.altitude());
    }

    @Test
    void verifyPositionComparison() {
        Position position1 = new Position(10, 20, 30);
        Position position2 = new Position(11, 21, 31);  // Changed latitude to 11

        assertTrue(position2.northOf(position1));
        assertFalse(position2.southOf(position1));
        assertTrue(position2.higherAltThan(position1));
        assertFalse(position1.higherAltThan(position2));
    }


    @Test
    void verifyPositionEquality() {
        Position position1 = new Position(10, 20, 30);
        Position position2 = new Position(30, 10, 30);

        assertFalse(position1.northOf(position2));
        assertTrue(position1.southOf(position2));
        assertFalse(position1.higherAltThan(position2));
        assertFalse(position1.lowerAltThan(position2));
    }

    @Test
    void verifyPositionWithItself() {
        Position position = new Position(10, 20, 30);

        assertFalse(position.northOf(position));
        assertFalse(position.southOf(position));
        assertFalse(position.higherAltThan(position));
        assertFalse(position.lowerAltThan(position));
    }

    @Test
    void verifyAircraftConcurrentAccess() throws InterruptedException {
        AtomicReference<Position> position = new AtomicReference<>(new Position(42.3601, -71.0589, 10));
        Aircraft aircraft = new Aircraft(position.get());
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            service.execute(() -> {
                double newLat = 42.3601 + finalI * 0.001;
                double newLon = -71.0589 - finalI * 0.001;
                double newAlt = 10 + finalI * 5;
                aircraft.setPosition(newLat, newLon, newAlt);
                position.set(aircraft.getPosition());
                latch.countDown();
            });
        }

        latch.await();
        service.shutdown();
        Position finalPosition = position.get();
        assertNotNull(finalPosition, "Final position should not be null");
        System.out.println("Final position: " + finalPosition);
    }

}