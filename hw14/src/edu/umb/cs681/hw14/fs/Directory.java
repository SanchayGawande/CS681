package edu.umb.cs681.hw14.fs;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Directory extends FSElement {
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent,
                name,
                0,
                creationTime);
    }

    public LinkedList<FSElement> getChildren() {
        Lock.lock();
        try{
            return this.children;
        } finally {
            Lock.unlock();
        }

    }

    public void appendChild(FSElement child) {
        Lock.lock();
        try{
            this.children.add(child);
            child.parent = this;
        } finally {
            Lock.unlock();
        }
    }

    public int countChildren() {
        Lock.lock();
        try{
            return children.size();
        } finally {
            Lock.unlock();
        }
    }

    public LinkedList<Directory> getSubDirectories() {
        Lock.lock();
        try{
            LinkedList<Directory> subDirectories = new LinkedList<>();

            for (FSElement element : children) {
                if (element instanceof Directory) {
                    subDirectories.add((Directory) element);
                }
            }

            return subDirectories;
        } finally {
            Lock.unlock();
        }
    }

    public LinkedList<File> getFiles() {
        Lock.lock();
        try{
            LinkedList<File> files = new LinkedList<>();

            for (FSElement element : children) {
                if (element instanceof File) {
                    files.add((File) element);
                }
            }

            return files;
        } finally {
            Lock.unlock();
        }
    }

    public int getTotalSize() {
        Lock.lock();
        try{
            int totalSize = 0;
            for (FSElement element : children) {
                if (element instanceof File) {
                    totalSize += element.getSize();
                } else if (element instanceof Directory) {
                    totalSize += ((Directory) element).getTotalSize();
                } else if (element instanceof Link) {
                    FSElement target = ((Link) element).getTarget();
                    if (target instanceof File) {
                        totalSize += target.getSize();
                    } else if (target instanceof Directory) {
                        totalSize += ((Directory) target).getTotalSize();
                    }
                }
            }
            return totalSize;
        } finally {
            Lock.unlock();
        }
    }

    public void accept(FSVisitor v) {
        Lock.lock();
        try {
            v.visit(this);
            for (FSElement e : children) {
                e.accept(v);
            }
        }
        finally {
            Lock.unlock();
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
}
