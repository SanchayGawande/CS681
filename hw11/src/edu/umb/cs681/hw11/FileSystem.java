package edu.umb.cs681.hw11;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FileSystem {
    private static AtomicReference<FileSystem> instance = new AtomicReference<>();
    private List<FSElement> rootDirs;

    private FileSystem() {
        rootDirs = new ArrayList<>();
    }

    public static FileSystem getInstance() {
        instance.updateAndGet(existingInstance -> existingInstance != null ? existingInstance : new FileSystem());
        return instance.get();
    }

    public void addRootDir(FSElement dir) {
        rootDirs.add(dir);
    }

    public void appendRootDir(Directory dir) {
        rootDirs.add(dir);
    }

    public List<FSElement> getRootDirs() {
        return rootDirs;
    }

    private static AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {

        FileSystem fs = testFixture.createFs();

        Runnable accessFileSystem = () -> {
            while (running.get()) {
                FileSystem fileSystem = FileSystem.getInstance();
                List<FSElement> rootDirs = fileSystem.getRootDirs();
                for (FSElement dir : rootDirs) {
                    Directory directory = (Directory) dir;
                    System.out.println("Accessed Directory: " + directory.getName());
                    System.out.println("Total Size: " + directory.getTotalSize());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        };


        Thread[] threads = new Thread[15];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(accessFileSystem);
            threads[i].start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }


        running.set(false);


        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Failed to join thread " + i);
            }
        }

        System.out.println("All threads have been terminated");
    }
}