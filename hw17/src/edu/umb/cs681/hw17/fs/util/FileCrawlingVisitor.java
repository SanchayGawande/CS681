package edu.umb.cs681.hw17.fs.util;
import edu.umb.cs681.hw17.fs.Directory;
import edu.umb.cs681.hw17.fs.File;
import edu.umb.cs681.hw17.fs.FSVisitor;
import edu.umb.cs681.hw17.fs.Link;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileCrawlingVisitor implements FSVisitor {


    private ConcurrentLinkedQueue files= new ConcurrentLinkedQueue();;

    public ConcurrentLinkedQueue<File> getFiles() {
            return files;
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
        files.add(file);
    }
}