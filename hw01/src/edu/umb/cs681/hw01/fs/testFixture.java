package edu.umb.cs681.hw01.fs;
import java.time.LocalDateTime;

public class testFixture {
    public static FileSystem createFs() {
        FileSystem fs = FileSystem.getFileSystem();

        Directory repo = new Directory(null, "repo", 0, LocalDateTime.of(2021, 1, 21, 10, 0));
        fs.appendRootDir(repo);

        Directory src = new Directory(repo, "src", 0, LocalDateTime.of(2021, 2, 21, 10, 0));
        File A_java = new File(src, "A.java", 100, LocalDateTime.of(2021, 3, 21, 10, 0));
        File B_java = new File(src, "B.java", 200, LocalDateTime.of(2021, 4, 21, 10, 0));
        Directory src_bin = new Directory(src, "bin", 0, LocalDateTime.of(2021, 5, 21, 10, 0));
        File A_class = new File(src_bin, "A.class", 150, LocalDateTime.of(2021, 6, 21, 10, 0));
        File B_class = new File(src_bin, "B.class", 150, LocalDateTime.of(2021, 7, 21, 10, 0));

        Directory test = new Directory(repo, "test", 0, LocalDateTime.of(2021, 8, 21, 10, 0));
        Directory test_src = new Directory(test, "src", 0, LocalDateTime.of(2021, 9, 21, 10, 0));
        File ATest_java = new File(test_src, "ATest.java", 300, LocalDateTime.of(2021, 10, 21, 10, 0));
        File BTest_java = new File(test_src, "BTest.java", 400, LocalDateTime.of(2021, 11, 21, 10, 0));
        Directory test_bin = new Directory(test, "bin", 0, LocalDateTime.of(2021, 12, 21, 10, 0));
        File ATest_class = new File(test_bin, "ATest.class", 150, LocalDateTime.of(2022, 4, 21, 10, 0));
        File BTest_class = new File(test_bin, "BTest.class", 150, LocalDateTime.of(2022, 5, 21, 10, 0));

        File readme_md = new File(repo, "readme.md", 50, LocalDateTime.of(2023, 4, 21, 10, 0));

        repo.appendChild(src);
        repo.appendChild(test);
        repo.appendChild(readme_md);

        src.appendChild(A_java);
        src.appendChild(B_java);
        src.appendChild(src_bin);

        src_bin.appendChild(A_class);
        src_bin.appendChild(B_class);

        test.appendChild(test_src);
        test.appendChild(test_bin);

        test_src.appendChild(ATest_java);
        test_src.appendChild(BTest_java);

        test_bin.appendChild(ATest_class);
        test_bin.appendChild(BTest_class);

        return fs;
    }
}
