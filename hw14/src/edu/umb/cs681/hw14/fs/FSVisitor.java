package edu.umb.cs681.hw14.fs;

public interface FSVisitor {
    void visit(Directory dir);
    void visit(File file);
    void visit(Link link);
}
