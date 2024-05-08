package edu.umb.cs681.hw14.fs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;

public class FileSystemTest {

    private FileSystem fileSystem;

    @BeforeEach
    public void setUp() {
        fileSystem = testFixture.createFs();
    }

    @Test
    public void testFileSystemStructure() {
        assertEquals(1, fileSystem.getRootDirs().size(), "File system should have one root directory.");
        Directory rootDir = fileSystem.getRootDirs().getFirst();
        assertEquals("repo", rootDir.getName(), "The root directory should be named 'repo'.");
        assertEquals(3, rootDir.countChildren(), "Root directory should contain three children.");
    }
}
