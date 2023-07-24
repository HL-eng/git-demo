import org.junit.Test;

import java.io.File;

public class TestExtractFileName {
    public static void main(String[] args) {
        String path = "G:\\Test\\testZip.zip";

        File file = new File(path);
        String fileName = file.getName();

        // 去除扩展名
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1) {
            fileName = fileName.substring(0, lastIndex);
        }

        System.out.println("提取的文件名: " + fileName);
    }

    @Test
    public void extractFileName(){
        String path = "G:\\Test\\testZip.zip";
        // 提取文件名（包括扩展名）
        String fileNameWithExtension = path.substring(path.lastIndexOf("\\") + 1);
        // 去除扩展名
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
        System.out.println("提取的文件名: " + fileName);
    }
}

