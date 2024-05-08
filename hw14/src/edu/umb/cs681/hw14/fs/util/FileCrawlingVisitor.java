package edu.umb.cs681.hw14.fs.util;
import edu.umb.cs681.hw14.fs.Directory;
import edu.umb.cs681.hw14.fs.FSVisitor;
import edu.umb.cs681.hw14.fs.File;
import edu.umb.cs681.hw14.fs.Link;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileCrawlingVisitor implements FSVisitor {
    private LinkedList<File> sharedFileList;
    private ReentrantLock listLock;
    private AtomicBoolean running;
    private HashSet<String> visitedPaths;

    public FileCrawlingVisitor(LinkedList<File> sharedFileList, ReentrantLock listLock, AtomicBoolean running) {
        this.sharedFileList = sharedFileList;
        this.listLock = listLock;
        this.running = running;
        this.visitedPaths = new HashSet<>();
    }

    @Override
    public void visit(Link link) {
        if (running.get() && visitedPaths.add(link.getPath())) {
            System.out.println(Thread.currentThread().getName() + " visited link: " + link.getPath());
            link.getTarget().accept(this);
        }
    }

    @Override
    public void visit(File file) {
        if (running.get() && visitedPaths.add(file.getPath())) {
            listLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " visited file: " + file.getPath());
                sharedFileList.add(file);
            } finally {
                listLock.unlock();
            }
        }
    }

    @Override
    public void visit(Directory dir) {
        if (running.get() && visitedPaths.add(dir.getPath())) {
            System.out.println(Thread.currentThread().getName() + " visited directory: " + dir.getPath());
            dir.getChildren().forEach(child -> child.accept(this));
        }
    }
}
