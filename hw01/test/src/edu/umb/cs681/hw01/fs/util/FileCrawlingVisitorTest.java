package edu.umb.cs681.hw01.fs.util;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.umb.cs681.hw01.fs.*;

import java.util.Map;

public class FileCrawlingVisitorTest {
    private static FileSystem fs;
    private static FileCrawlingVisitor visitor;
    @BeforeAll
    public static void setUp() {
        fs = testFixture.createFs();
        visitor = new FileCrawlingVisitor();
        fs.getRootDirs().get(0).accept(visitor);
    }

    @Test
    public void VerifyGroupFilesByExtensionAndSumStats() {
        Map<String, Long> extensionSizes = visitor.groupFilesByExtensionAndSumSizes();
        assertEquals(Long.valueOf(1000), extensionSizes.get("java")); // Sum of .java files
        assertEquals(Long.valueOf(600), extensionSizes.get("class")); // Sum of .class files
        assertEquals(Long.valueOf(50), extensionSizes.get("md")); // Size of readme.md
    }
}