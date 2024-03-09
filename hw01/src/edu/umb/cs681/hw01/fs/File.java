package edu.umb.cs681.hw01.fs;
import java.time.LocalDateTime;

public class File extends FSElement {
    public File(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent,
                name,
                size,
                creationTime);
    }

    public String getExtension() {
        int lastIndexOfDot = this.getName().lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return ""; // No extension found
        }
        return this.getName().substring(lastIndexOfDot + 1);
    }


    @Override
    public void accept(FSVisitor v) {
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