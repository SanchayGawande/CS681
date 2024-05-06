package edu.umb.cs681.hw07;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileSystemTest {

    @Test
    void verifySingleton() {
        FileSystem fs1 = FileSystem.getInstance();
        FileSystem fs2 = FileSystem.getInstance();
        assertSame(fs1, fs2, "Both instances should be the same");
    }

    @Test
    void verifySingletonMultiThreaded() throws InterruptedException {
        Set<FileSystem> instances = new HashSet<>();
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                FileSystem instance = FileSystem.getInstance();
                synchronized (instances) {
                    instances.add(instance);
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(1, instances.size());
    }
}
