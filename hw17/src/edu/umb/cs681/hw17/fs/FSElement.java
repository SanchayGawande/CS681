package edu.umb.cs681.hw17.fs;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class FSElement {
    protected String name;
    protected int size;
    protected LocalDateTime creationTime;
    protected Directory parent;
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();  // Lock object for synchronization

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        this.parent = parent;
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
    }

    public Directory getParent() {
        lock.readLock().lock();
        try {
            return this.parent= parent;
        } finally {
            lock.readLock().unlock();
        }
    }

    public Directory setParent() {
        lock.readLock().lock();
        try {
            return this.parent;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getSize() {
        lock.readLock().lock();
        try {
            return this.size;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setName(String name) {
        lock.writeLock().lock();
        try {
            this.name = name;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String getName() {
        lock.readLock().lock();
        try {
            return this.name;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setCreationTime(LocalDateTime creationTime) {
        lock.writeLock().lock();
        try {
            this.creationTime = creationTime;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public LocalDateTime getCreationTime() {
        lock.readLock().lock();
        try {
            return this.creationTime;
        } finally {
            lock.readLock().unlock();
        }
    }

    public String getPath() {
        lock.readLock().lock();
        try {
            if (this.parent == null) {
                return this.name;  // Root directory
            } else {
                return this.parent.getPath() + "/" + this.name;
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public abstract void accept(FSVisitor v);



    public abstract boolean isDirectory();

    public abstract boolean isFile();
}
