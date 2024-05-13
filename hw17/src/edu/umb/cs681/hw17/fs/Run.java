package edu.umb.cs681.hw17.fs;
import edu.umb.cs681.hw17.fs.util.FileCrawlingVisitor;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Run implements Runnable {
    private static AtomicBoolean done = new AtomicBoolean(false);
    private Directory dir;
    private static ConcurrentLinkedQueue<File> commonQueue = new ConcurrentLinkedQueue<>();

    public Run(Directory dir) {
        this.dir = dir;
    }

    @Override
    public void run() {
        FileCrawlingVisitor fileCrawlingInstance = new FileCrawlingVisitor();
        System.out.println(Thread.currentThread().getId() + ": With file crawling instance :" + fileCrawlingInstance.hashCode());
        if (done.get()) {
            System.out.println("Done is true, process stopped!!");
        } else {
            dir.accept(fileCrawlingInstance);
            commonQueue.addAll(fileCrawlingInstance.getFiles());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Process interrupted ");
            }
        }
    }

    public static void main(String[] args) {
        FileSystem system1 = testFixture.createFs();

        Run run1 = new Run(system1.getRootDirs().poll());
        Run run2 = new Run(system1.getRootDirs().poll());
        Run run3 = new Run(system1.getRootDirs().poll());

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);
        Thread thread3 = new Thread(run3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(1000);
            done.set(true);
            thread1.interrupt();
            thread2.interrupt();
            thread3.interrupt();
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Crawling completed. Files Stored:");
        for (File file : commonQueue) {
            System.out.println(file.getName());
        }
    }
}