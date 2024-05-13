package edu.umb.cs681.hw14.fs;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FSElement {
    protected String name;
    protected int size;
    protected LocalDateTime creationTime;
    protected Directory parent;
    protected ReentrantLock Lock = new ReentrantLock();

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        this.parent = parent;
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
    }

    public Directory getParent() {
        Lock.lock();
        try {
            return this.parent;
        } finally {
            Lock.unlock();
        }
    }

    public int getSize() {
        Lock.lock();
        try {
            return this.size;
        } finally {
            Lock.unlock();
        }
    }

    public String setName(String name) {
        Lock.lock();
        try {
            return this.name = name;
        } finally {
            Lock.unlock();
        }
    }

    public String getName() {
        Lock.lock();
        try {
            return this.name;
        } finally {
            Lock.unlock();
        }
    }

    public LocalDateTime setCreationTime(LocalDateTime creationTime) {
        {
            Lock.lock();
            try{
                return this.creationTime = creationTime;
            } finally {
                Lock.unlock();
            }
        }
    }

    public LocalDateTime getCreationTime() {
            Lock.lock();
            try{
                return this.creationTime;
            } finally {
                Lock.unlock();
            }
        }

    public String getPath() {
        Lock.lock();
        try {
            if (this.parent == null) {
                return this.name;  // Root directory
            } else {
                return this.parent.getPath() + "/" + this.name;
            }
        }finally{
            Lock.unlock();
        }
    }

    public abstract boolean isDirectory();
    public abstract boolean isFile();
    public abstract void accept(FSVisitor v);

}
