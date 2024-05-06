package edu.umb.cs681.hw11;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class FileSystemTest {

    @Test
    public void testSingleton() {
        FileSystem fs1 = FileSystem.getInstance();
        FileSystem fs2 = FileSystem.getInstance();
        assertSame(fs1, fs2);
    }

    @Test
    public void testAddRootDir() {
        FileSystem fs = FileSystem.getInstance();
        Directory root = new Directory(null, "root", 0, LocalDateTime.now());
        fs.addRootDir(root);
        assertTrue(fs.getRootDirs().contains(root));
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        final FileSystem fs = FileSystem.getInstance();
        Directory root1 = new Directory(null, "root1", 0, LocalDateTime.now());
        Directory root2 = new Directory(null, "root2", 0, LocalDateTime.now());

        Thread thread1 = new Thread(() -> fs.addRootDir(root1));
        Thread thread2 = new Thread(() -> fs.addRootDir(root2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertTrue(fs.getRootDirs().contains(root1) && fs.getRootDirs().contains(root2));
    }
}
