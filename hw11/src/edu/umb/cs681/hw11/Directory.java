package edu.umb.cs681.hw11;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Directory extends FSElement {
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent,
                name,
                0,
                creationTime);
    }

    public List<FSElement> getChildren() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(children);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void appendChild(FSElement child) {
        lock.writeLock().lock();
        try {
            this.children.add(child);
            child.setParent();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int countChildren() {
        lock.readLock().lock();
        try {
            return children.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public LinkedList<Directory> getSubDirectories() {
        lock.readLock().lock();
        try {
            LinkedList<Directory> subDirectories = new LinkedList<>();
            for (FSElement element : children) {
                if (element instanceof Directory) {
                    subDirectories.add((Directory) element);
                }
            }
            return subDirectories;
        } finally {
            lock.readLock().unlock();
        }
    }

    public LinkedList<File> getFiles() {
        lock.readLock().lock();
        try {
            LinkedList<File> files = new LinkedList<>();
            for (FSElement element : children) {
                if (element instanceof File) {
                    files.add((File) element);
                }
            }
            return files;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getTotalSize() {
        lock.readLock().lock();
        try {
            int totalSize = 0;
            for (FSElement element : children) {
                if (element instanceof Directory) {
                    totalSize += ((Directory) element).getTotalSize();
                } else {
                    totalSize += element.getSize();
                }
            }
            return totalSize;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isLink(){
        return false;
    }
}