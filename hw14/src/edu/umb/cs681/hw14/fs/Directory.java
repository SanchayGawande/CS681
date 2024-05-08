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
        return this.children;
    }

    public void appendChild(FSElement child) {
        this.children.add(child);
        child.parent = this;
    }

    public int countChildren() {
        return children.size();
    }

    public LinkedList<Directory> getSubDirectories() {
        LinkedList<Directory> subDirectories = new LinkedList<>();

        for (FSElement element : children) {
            if (element instanceof Directory) {
                subDirectories.add((Directory) element);
            }
        }

        return subDirectories;
    }

    public LinkedList<File> getFiles() {
        LinkedList<File> files = new LinkedList<>();

        for (FSElement element : children) {
            if (element instanceof File) {
                files.add((File) element);
            }
        }

        return files;
    }

    public int getTotalSize() {
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
    }

    public synchronized void accept(FSVisitor v) {
        v.visit(this);
        for (FSElement e : children) {
            e.accept(v);
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
