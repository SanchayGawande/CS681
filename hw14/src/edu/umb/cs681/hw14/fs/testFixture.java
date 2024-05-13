package edu.umb.cs681.hw14.fs;
import java.time.LocalDateTime;

public class testFixture {
    public static FileSystem createFs() {
        FileSystem fs = FileSystem.getFileSystem();

        Directory repo = new Directory(null, "repo", 0, LocalDateTime.now());
        fs.appendRootDir(repo);

        Directory src = new Directory(repo, "src", 0, LocalDateTime.of(2021, 4, 21, 10, 0));
        File A_java = new File(src, "A.java", 100, LocalDateTime.of(2021, 4, 22, 11, 0));
        File B_java = new File(src, "B.java", 200, LocalDateTime.of(2021, 4, 23, 11, 0));

        Directory test = new Directory(repo, "test", 0, LocalDateTime.of(2021, 4, 24, 12, 0));
        Directory test_src = new Directory(test, "src", 0, LocalDateTime.of(2021, 4, 25, 13, 0));
        File ATest_java = new File(test_src, "ATest.java", 250, LocalDateTime.of(2021, 4, 26, 14, 0));
        File BTest_java = new File(test_src, "BTest.java", 300, LocalDateTime.of(2021, 4, 27, 14, 0));

        File readme_md = new File(repo, "readme.md", 50, LocalDateTime.of(2021, 4, 28, 15, 0));
        Link rm_md = new Link(test_src, "rm.md", LocalDateTime.of(2021, 4, 29, 15, 0), readme_md);

        repo.appendChild(src);
        repo.appendChild(test);
        repo.appendChild(readme_md);

        src.appendChild(A_java);
        src.appendChild(B_java);

        test.appendChild(test_src);
        test_src.appendChild(ATest_java);
        test_src.appendChild(BTest_java);
        test_src.appendChild(rm_md);

        Directory mainRepo = new Directory(null, "mainRepo", 0, LocalDateTime.now());
        fs.appendRootDir(mainRepo);

        Directory lib = new Directory(mainRepo, "lib", 0, LocalDateTime.of(2021, 5, 1, 10, 0));
        File C_java = new File(lib, "C.java", 120, LocalDateTime.of(2021, 5, 2, 12, 0));
        File D_java = new File(lib, "D.java", 180, LocalDateTime.of(2021, 5, 3, 12, 0));

        Directory doc = new Directory(mainRepo, "doc", 0, LocalDateTime.of(2021, 5, 4, 13, 0));
        Directory doc_src = new Directory(doc, "src", 0, LocalDateTime.of(2021, 5, 5, 14, 0));
        File CDoc_java = new File(doc_src, "CDoc.java", 200, LocalDateTime.of(2021, 5, 6, 15, 0));
        File DDoc_java = new File(doc_src, "DDoc.java", 220, LocalDateTime.of(2021, 5, 7, 15, 0));

        File changelog_md = new File(mainRepo, "changelog.md", 70, LocalDateTime.of(2021, 5, 8, 16, 0));
        Link cl_md = new Link(doc_src, "cl.md", LocalDateTime.of(2021, 5, 9, 16, 0), changelog_md);

        mainRepo.appendChild(lib);
        mainRepo.appendChild(doc);
        mainRepo.appendChild(changelog_md);

        lib.appendChild(C_java);
        lib.appendChild(D_java);

        doc.appendChild(doc_src);
        doc_src.appendChild(CDoc_java);
        doc_src.appendChild(DDoc_java);
        doc_src.appendChild(cl_md);

        Directory codeBase = new Directory(null, "codeBase", 0, LocalDateTime.now());
        fs.appendRootDir(codeBase);

        Directory components = new Directory(codeBase, "components", 0, LocalDateTime.of(2021, 6, 1, 10, 0));
        File E_java = new File(components, "E.java", 150, LocalDateTime.of(2021, 6, 2, 11, 0));
        File F_java = new File(components, "F.java", 190, LocalDateTime.of(2021, 6, 3, 11, 0));

        Directory examples = new Directory(codeBase, "examples", 0, LocalDateTime.of(2021, 6, 4, 12, 0));
        Directory examples_src = new Directory(examples, "src", 0, LocalDateTime.of(2021, 6, 5, 13, 0));
        File EExample_java = new File(examples_src, "EExample.java", 230, LocalDateTime.of(2021, 6, 6, 14, 0));
        File FExample_java = new File(examples_src, "FExample.java", 250, LocalDateTime.of(2021, 6, 7, 14, 0));

        File license_md = new File(codeBase, "license.md", 55, LocalDateTime.of(2021, 6, 8, 15, 0));
        Link lic_md = new Link(examples_src, "lic.md", LocalDateTime.of(2021, 6, 9, 15, 0), license_md);

        codeBase.appendChild(components);
        codeBase.appendChild(examples);
        codeBase.appendChild(license_md);

        components.appendChild(E_java);
        components.appendChild(F_java);

        examples.appendChild(examples_src);
        examples_src.appendChild(EExample_java);
        examples_src.appendChild(FExample_java);
        examples_src.appendChild(lic_md);

        return fs;
    }
}
