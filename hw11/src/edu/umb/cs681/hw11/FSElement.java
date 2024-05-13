package edu.umb.cs681.hw11;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public abstract class FSElement {
    protected String name;
    protected int size;
    protected LocalDateTime creationTime;
    protected Directory parent;
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

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
        lock.writeLock().lock();
        try {
            return this.parent;
        } finally {
            lock.writeLock().unlock();
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

    public  boolean isDirectory(){
        return false;
    }

    public  boolean isFile(){
        return false;
    }


    public boolean isLink(){
        return false;
    }
}
