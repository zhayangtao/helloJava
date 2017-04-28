package javaaio;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by shliangyan on 2017/4/28.
 */
public class FileSystemAio {
    public static void main(String[] args) {
        //获得方法1
        Path path1 = FileSystems.getDefault().getPath("f:/", "text.txt");
        System.out.println(path1.getNameCount());

        //获得方法2
        File file = new File("f:/text.txt");
        Path path2 = file.toPath();
        System.out.println(path1.compareTo(path2));
    }
}
