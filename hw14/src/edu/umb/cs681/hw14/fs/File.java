package edu.umb.cs681.hw14.fs;
import java.time.LocalDateTime;

public class File extends FSElement {
    public File(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent,
                name,
                size,
                creationTime);
    }

    @Override
    public synchronized void accept(FSVisitor v) {
        v.visit(this);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isFile() {
        return true;
    }
}