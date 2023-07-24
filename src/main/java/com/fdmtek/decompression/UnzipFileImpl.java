package com.fdmtek.decompression;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFileImpl {
    String basePath = "G:\\Test\\directory\\";
    //解压文件的主程序
    public boolean unzipFile(File file){
        // 提取文件名（包括扩展名）
        String zipFilePath = file.getAbsolutePath();//获取绝对路径
        String fileNameWithExtension = zipFilePath.substring(zipFilePath.lastIndexOf("\\") + 1);
        // 去除扩展名
        String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
        //获取当前系统时间，拼接到目标文件夹后面
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String formatDate = sdf.format(date);
        String srcDestDirectory = basePath + formatDate;
        basePath = basePath + formatDate + "\\" + fileName; //做一下字符串的拼接操作
        try {
            unzip(zipFilePath, basePath,srcDestDirectory);
            return true;//这个方法只要能够正常执行到这，说明就是正确的
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //实际的解压缩操作程序
    public void unzip(String zipFilePath, String destDirectory, String srcDestDirectory) throws IOException {
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

    //实际的提取文件程序
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
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
