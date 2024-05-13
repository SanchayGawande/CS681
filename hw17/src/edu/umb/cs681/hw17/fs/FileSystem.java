package edu.umb.cs681.hw17.fs;

import edu.umb.cs681.hw17.fs.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashSet;

public class FileSystem {
    private static FileSystem instance;
    private ConcurrentLinkedQueue<Directory> rootDirs;
    private FileSystem() {
        rootDirs = new ConcurrentLinkedQueue<>();
    }

    public static FileSystem getFileSystem() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public ConcurrentLinkedQueue<Directory> getRootDirs() {
        return rootDirs;
    }

    public void appendRootDir(Directory dir) {
        rootDirs.offer(dir);
    }

}