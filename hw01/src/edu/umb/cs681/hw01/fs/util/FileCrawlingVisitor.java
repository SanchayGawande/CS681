package edu.umb.cs681.hw01.fs.util;
import edu.umb.cs681.hw01.fs.*;
import edu.umb.cs681.hw01.fs.testFixture;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCrawlingVisitor implements FSVisitor {

    private LinkedList<File> files;

    public LinkedList<File> getFiles() {
        return files;
    }

    public FileCrawlingVisitor() {
        files = new LinkedList<File>();

    }
    public Stream<File> files() {
        return this.files.stream();
    }
    public Map<String, Long> groupFilesByExtensionAndSumSizes() {
        return files.stream()
                .collect(Collectors.groupingBy(
                        File::getExtension,
                        Collectors.summingLong(File::getSize)
                ));
    }
    @Override
    public void visit(Link link) {
    }
    @Override
    public void visit(Directory dir) {
    }
    @Override
    public void visit (File file) {
        files. add (file);
    }

    public static void main(String[] args) {
        FileSystem fs = testFixture.createFs();
        Directory rootDir = fs.getRootDirs().get(0);

        FileCrawlingVisitor visitor = new FileCrawlingVisitor();
        rootDir.accept(visitor);
        LocalDateTime dateThreshold = LocalDateTime.of(2021, 3, 22, 0, 0);
        long count = visitor.files()
                .filter(file -> file.getName().endsWith(".java"))
                .filter(file -> file.getCreationTime().isAfter(dateThreshold))
                .count();

        System.out.println("Number of .java files created after " + dateThreshold + ": " + count);
    }
}


