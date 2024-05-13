package edu.umb.cs681.hw14.fs;
import edu.umb.cs681.hw14.fs.util.FileCrawlingVisitor;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem  {

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
        lock.lock();
        try {
            return rootDirs;
        } finally {
            lock.unlock();
        }
    }

    public void appendRootDir(Directory dir) {
        lock.lock();
        try {
            rootDirs.add(dir);
        } finally {
            lock.unlock();
        }
    }

}