package edu.umb.cs681.hw14.fs;

import edu.umb.cs681.hw14.fs.util.FileCrawlingVisitor;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Run implements Runnable {

    private static AtomicBoolean done = new AtomicBoolean(false);
    private Directory dir;
    private ReentrantLock lock = new ReentrantLock();

    private static LinkedList<File> Commonlist=new LinkedList<>();

    public Run(Directory dir) {
        this.dir = dir;
    }

    @Override
    public void run() {
        FileCrawlingVisitor FileCrewlingInstance = new FileCrawlingVisitor();
        System.out.println(Thread.currentThread().threadId() + ": With file crawling instance :" + FileCrewlingInstance.hashCode());
        if (done.get()){
            System.out.println("Done is true process stopped!!");
        } else {
            dir.accept(FileCrewlingInstance);
            lock.lock();
            try{
                Commonlist.addAll(FileCrewlingInstance.getFiles());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Process interrupted ");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock listLock = new ReentrantLock();

        FileSystem system1 = testFixture.createFs();
        Run run1=new Run(system1.getRootDirs().get(0));
        Run run2=new Run(system1.getRootDirs().get(1));
        Run run3=new Run(system1.getRootDirs().get(2));

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
        listLock.lock();

        try {
            for (File file : Commonlist) {
                System.out.println(file.getName());
            }
        }catch (NullPointerException exception){
            return;
        }
    }
}
