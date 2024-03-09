package edu.umb.cs681.hw01.fs;

import java.time.LocalDateTime;

public class Link extends FSElement {
    private FSElement target;

    public Link(Directory parent, String name, LocalDateTime creationTime, FSElement target) {
        super(parent, name, 0, creationTime);
        this.target = target;
    }

    public FSElement getTarget() {
        return this.target;
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
        return false;
    }

    public boolean isLink() {
        return true;
    }
}
