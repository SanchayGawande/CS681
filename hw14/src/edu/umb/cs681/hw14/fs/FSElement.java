package edu.umb.cs681.hw14.fs;
import java.time.LocalDateTime;

public abstract class FSElement {
    protected String name;
    protected int size;
    protected LocalDateTime creationTime;
    protected Directory parent;

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        this.parent = parent;
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
    }

    public Directory getParent() {
        return this.parent;
    }

    public int getSize() {
        return this.size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public String getPath() {
        if (this.parent == null) {
            return this.name;  // Root directory
        } else {
            return this.parent.getPath() + "/" + this.name;
        }
    }

    public abstract void accept(FSVisitor v);

}
