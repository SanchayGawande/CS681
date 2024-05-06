package edu.umb.cs681.hw07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private static FileSystem instance = null;
    private static final ReentrantLock lock = new ReentrantLock();  // Lock object for synchronization
    private List<Directory> rootDirs;

    private FileSystem() {
        rootDirs = new ArrayList<>();
    }

    // Get the singleton instance of FileSystem
    public static FileSystem getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new FileSystem();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public void addRootDir(Directory dir) {
        lock.lock();
        try {
            rootDirs.add(dir);
        } finally {
            lock.unlock();
        }
    }

    public List<Directory> getRootDirs() {
        return rootDirs;
    }

    public static void main(String[] args) {
        int numberOfThreads = 12;
        Thread[] threads = new Thread[numberOfThreads];
        FileSystem[] fileSystems = new FileSystem[numberOfThreads];


        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                fileSystems[finalI] = FileSystem.getInstance();

                System.out.println("Thread " + finalI + " received instance with hashcode: " + fileSystems[finalI].hashCode());
            });
            threads[i].start();
        }


        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }


        boolean allSame = true;
        for (int i = 1; i < fileSystems.length; i++) {
            if (fileSystems[i] != fileSystems[0]) {
                allSame = false;
                break;
            }
        }

        if (allSame) {
            System.out.println("All threads received the same instance of FileSystem.");
        } else {
            System.out.println("Different instances were created, which is an error.");
        }
    }
}