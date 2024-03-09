package edu.umb.cs681.hw01.fs.util;

import edu.umb.cs681.hw01.fs.Directory;
import edu.umb.cs681.hw01.fs.FSVisitor;
import edu.umb.cs681.hw01.fs.File;
import edu.umb.cs681.hw01.fs.Link;

public class CountingVisitor implements FSVisitor {
    private int dirNum = 0;
    private int fileNum = 0;
    private int linkNum = 0;


    public void visit(Directory dir) {
        dirNum++;
    }

    public void visit(File file) {
        fileNum++;
    }

    public void visit(Link link) {
        linkNum++;
    }

    public int getDirNum() {
        return dirNum;
    }

    public int getFileNum() {
        return fileNum;
    }

    public int getLinkNum(){
        return linkNum;
    }

}
