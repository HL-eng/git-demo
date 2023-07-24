import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTraversalExample {
    public static void main(String[] args) {
        String directoryPath = "G:\\Test\\testZip"; // 修改为目标目录的路径
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            traverseDirectory(directory);
        } else {
            System.out.println("指定的路径不是一个有效的目录。");
        }
    }

    private static void traverseDirectory(File directory) {
        File[] files = directory.listFiles();
        List<File> excelFiles = new ArrayList<>();

        for(File file : files){
            excelFiles.add(file);
            System.out.println(file.getAbsolutePath());
        }


        // if (files != null) {
        //     for (File file : files) {
        //         if (file.isDirectory()) {
        //             // 递归遍历子目录
        //             traverseDirectory(file);
        //         } else {
        //             // 处理文件，这里可以根据需要进行相应的操作
        //             //统一将文件放到一个集合当中
        //             excelFiles.add(file);
        //             System.out.println(file.getAbsolutePath());
        //         }
        //     }
        // }
    }
}

