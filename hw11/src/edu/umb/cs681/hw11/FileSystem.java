package edu.umb.cs681.hw11;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private static AtomicReference<FileSystem> fsInstance = new AtomicReference<FileSystem>();
    private LinkedList<Directory> RootDirectories;

    private static ReentrantLock lock = new ReentrantLock();

    private FileSystem() {
        this.RootDirectories = new LinkedList<>();
    }


    public static FileSystem getFileSystem() {
        FileSystem current;
        do {
            current = fsInstance.get();
            if (current != null) {
                return current;
            }
        } while (!fsInstance.compareAndSet(null, new FileSystem()));
        return fsInstance.get();
    }

    public LinkedList<Directory> getRootDirectories() {
        lock.lock();
        try {
            return RootDirectories;
        } finally {
            lock.unlock();
        }
    }

    public void appendRootDir(Directory root) {
        lock.lock();
        try {
            RootDirectories.add(root);
        } finally {
            lock.unlock();
        }
    }
}

