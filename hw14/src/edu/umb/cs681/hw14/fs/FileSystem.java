package edu.umb.cs681.hw14.fs;
import edu.umb.cs681.hw14.fs.util.FileCrawlingVisitor;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private static FileSystem instance;
    private LinkedList<Directory> rootDirs;
    private ReentrantLock lock = new ReentrantLock();

    private FileSystem() {
        rootDirs = new LinkedList<>();
    }

    public static FileSystem getFileSystem() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public LinkedList<Directory> getRootDirs() {
        return rootDirs;
    }

    public void appendRootDir(Directory dir) {
        rootDirs.add(dir);
    }

    public static void main(String[] args) {
        AtomicBoolean running = new AtomicBoolean(true);
        LinkedList<File> sharedFileList = new LinkedList<>();
        ReentrantLock listLock = new ReentrantLock();

        FileSystem fs1 = testFixture.createFs();
        FileSystem fs2 = testFixture.createFs();
        FileSystem fs3 = testFixture.createFs();

        FileCrawlingVisitor visitor1 = new FileCrawlingVisitor(sharedFileList, listLock, running);
        FileCrawlingVisitor visitor2 = new FileCrawlingVisitor(sharedFileList, listLock, running);
        FileCrawlingVisitor visitor3 = new FileCrawlingVisitor(sharedFileList, listLock, running);

        Thread t1 = new Thread(() -> fs1.getRootDirs().get(0).accept(visitor1));
        Thread t2 = new Thread(() -> fs2.getRootDirs().get(0).accept(visitor2));
        Thread t3 = new Thread(() -> fs3.getRootDirs().get(0).accept(visitor3));

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(10000); // Simulate work
            running.set(false);
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted.");
        }

        System.out.println("Crawling complete. Collected files:");
        listLock.lock();
        try {
            sharedFileList.forEach(file -> System.out.println(file.getName()));
        } finally {
            listLock.unlock();
        }
    }
}