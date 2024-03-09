package edu.umb.cs681.hw01.fs.util;
import edu.umb.cs681.hw01.fs.Directory;
import edu.umb.cs681.hw01.fs.FSVisitor;
import edu.umb.cs681.hw01.fs.File;
import edu.umb.cs681.hw01.fs.Link;

import java.util.LinkedList;

public class FileSearchVisitor implements FSVisitor {
    private String fileName;
    private LinkedList<File> Filesfound;

    public FileSearchVisitor(String fileName) {
        Filesfound = new LinkedList<File>();
        this.fileName = fileName;
    }

    public void visit(Directory dir) {

    }

    public void visit(File file) {
        if (file.getName().equals(fileName)) {
            Filesfound.add(file);
        }
    }

    public void visit(Link link) {

    }

    public LinkedList<File> getFoundFiles() {
        return Filesfound;
    }
}
