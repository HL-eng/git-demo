package com.zkby;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.*;

public class UnzipExample {
    public static void main(String[] args) {
        //需要解压的文件夹目录
        //String zipFilePath = "path/to/your/zip/file.zip";
        String zipFilePath = "G:\\Test\\testZip.zip"; //这个文件夹名称后续是需要根据自己的操作得到的
        //现在需要获取没有后缀的文件名，将其拼接在目标目录的后面
        // 提取文件名（包括扩展名）
        String fileNameWithExtension = zipFilePath.substring(zipFilePath.lastIndexOf("\\") + 1);
        // 去除扩展名
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));

        //解压好的目标文件夹(因为下面要对这个文件夹进行创建，所以我们可以生成一个日志文件夹(里面标有日期))
        String destDirectory = "G:\\Test\\directory\\";
        //上面这个目录还有些问题，现在的思路
        //获取当前系统时间，拼接到目标文件夹后面
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formatDate = sdf.format(date);
        String srcDestDirectory = destDirectory + formatDate;
        destDirectory = destDirectory + formatDate + "\\" + fileName; //做一下字符串的拼接操作


        try {
            unzip(zipFilePath, destDirectory,srcDestDirectory);
            System.out.println("解压缩完成。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFilePath, String destDirectory, String srcDestDirectory) throws IOException {
        //根据目录来创建解压的目标目录
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            boolean mkdir = destDir.mkdirs();//如果文件不存在，就创建文件,注意这个地方不要使用成了mkdir()
            System.out.println(mkdir);
        }

        //创建好destDirectory目录之后，我需要再做一个操作，就是把

        //创建解压流文件
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("GBK"));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = srcDestDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        //没有这个路径是不是因为文件夹的前面带了一个testZip这个目录问题
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) { //这个地方设置的内容一次性读取4M
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}

