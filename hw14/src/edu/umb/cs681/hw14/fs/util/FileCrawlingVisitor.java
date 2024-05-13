package edu.umb.cs681.hw14.fs.util;
import edu.umb.cs681.hw14.fs.Directory;
import edu.umb.cs681.hw14.fs.FSVisitor;
import edu.umb.cs681.hw14.fs.File;
import edu.umb.cs681.hw14.fs.Link;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileCrawlingVisitor implements FSVisitor {

        private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

        private LinkedList<File> files= new LinkedList<File>();;

        public LinkedList<File> getFiles() {
            rwlock.readLock().lock();
            try{
                return files;
            }finally {
                rwlock.readLock().unlock();
            }
        }

        @Override
        public void visit(Link link) {
            return;
        }
        @Override
        public void visit(Directory dir) {
            return;
        }
        @Override
        public void visit (File file) {
            rwlock.readLock().lock();
            try{
                files.add(file);
            }finally {
                rwlock.readLock().unlock();
            }
        }
    }