package edu.umb.cs681.hw01.fs;
import java.util.LinkedList;
public class FileSystem {
    private static FileSystem instance;
    private LinkedList<Directory> rootDirs;

    private FileSystem() {
        rootDirs = new LinkedList<>();
    }

    public static FileSystem getFileSystem() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public LinkedList<Directory> getRootDirs() {
        return rootDirs;
    }

    public void appendRootDir(Directory dir) {
        rootDirs.add(dir);
    }


}
