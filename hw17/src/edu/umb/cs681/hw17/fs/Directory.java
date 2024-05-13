package edu.umb.cs681.hw17.fs;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Directory extends FSElement {
    private Queue<FSElement> children = new ConcurrentLinkedQueue<>();

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, 0, creationTime);
    }

    public List<FSElement> getChildren() {
        return children.stream().collect(Collectors.toList());
    }

    public void appendChild(FSElement child) {
        this.children.add(child);
        child.setParent();
    }

    public int countChildren() {
        return children.size();
    }

    public List<Directory> getSubDirectories() {
        return children.stream()
                .filter(e -> e instanceof Directory)
                .map(e -> (Directory) e)
                .collect(Collectors.toList());
    }

    public List<File> getFiles() {
        return children.stream()
                .filter(e -> e instanceof File)
                .map(e -> (File) e)
                .collect(Collectors.toList());
    }

    public int getTotalSize() {
        return children.stream()
                .mapToInt(e -> {
                    if (e.isDirectory()) {
                        return ((Directory) e).getTotalSize();
                    } else {
                        return e.getSize();
                    }
                }).sum();
    }

    @Override
    public void accept(FSVisitor v) {
        v.visit(this);
        for(FSElement element:getChildren()){
            element.accept(v);
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