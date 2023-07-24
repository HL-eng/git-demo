package com.fdmtek.gui;

import com.fdmtek.decompression.UnzipFileImpl;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LogUploadSystemWindow4 extends JFrame {
    private JFrame frame;
    private UnzipFileImpl unzipFile = new UnzipFileImpl(); //unZipFile

    //这个地方再定义一个全局文件，表示解压好的文件，用于上传至服务器使用
    File selectedFile = null;

    // 获取logger实列,获取日志记录器并读取配置文件,这个记录器将负责控制日志信息
    private static Logger logger = Logger.getLogger(LogUploadSystemWindow4.class);

    public LogUploadSystemWindow4(String sessionID) {

        frame = new JFrame("日志上传系统");

        //设置窗口大小
        frame.setSize(760,540);
        //设置窗口弹出位置
        frame.setLocation(700,300);
        //设置窗口大小不可变
        frame.setResizable(true);
        //用户单击窗口的关闭按钮时程序执行的操作 // **************
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        //定义面板
        JPanel panel = new JPanel(null);
        //定义一个标签
        JLabel label = new JLabel("日志上传系统");
        label.setFont(new Font("宋体", Font.BOLD, 30)); // 设置字体、样式和大小
        label.setBounds(280, 30, 200, 50); // 设置标签的位置和大小

        //定义一个提示标签，让用户只能上传.zip格式的文件
        JLabel tipLabel = new JLabel("Tips:仅支持后缀为.zip的压缩文件,否则会上传失败!");
        tipLabel.setFont(new Font("宋体", Font.BOLD, 15)); // 设置字体、样式和大小
        tipLabel.setBounds(20, 400, 400, 50); // 设置标签的位置和大小


        JButton selectLogFileButton = new JButton("选择日志压缩文件");
        selectLogFileButton.setSize(180,40);
        selectLogFileButton.setLocation(160,140);
        selectLogFileButton.setFont(new Font("宋体", 1, 15));


        JButton uploadButton = new JButton("上传至服务器");
        uploadButton.setSize(180,40);
        uploadButton.setLocation(400,140);
        uploadButton.setFont(new Font("宋体", 1, 15));

        //信息打印区
        JLabel infoLable = new JLabel("信息打印区");
        infoLable.setFont(new Font("宋体", Font.BOLD, 15));
        infoLable.setBounds(30,180,200,50);


        //这个地方加上一个文本打印区域
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("宋体", 1, 20));

        //为文本区添加滚动条
        JScrollPane scrollpane=new JScrollPane();//创建滚动条面板
        scrollpane.setBounds(120,220,510,180);//自定义该面板位置并设置大小为100*50

        scrollpane.setViewportView(jTextArea);//（这是关键！不是用add）把text1组件放到滚动面板里


        //将定义好的标签加入到panel面板上面
        panel.add(label);
        panel.add(tipLabel);
        panel.add(infoLable);
        panel.add(selectLogFileButton);
        panel.add(uploadButton);
        frame.add(scrollpane);//将滚动条面板加到窗体
        frame.add(panel);//将上传按钮添加到frame当中

        //选择文件按钮执行的操作
        selectLogFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File file) {
                        return file.getName().toLowerCase().endsWith(".zip") || file.isDirectory();
                    }

                    public String getDescription() {
                        return "ZIP Files (*.zip)";
                    }
                });

                int result = fileChooser.showOpenDialog(LogUploadSystemWindow4.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    // 处理选择的文件
                    // boolean flag = unzipFile.unzipFile(selectedFile);
                    // if(flag){
                    //     //System.out.println("上传成功");
                    //     JOptionPane.showMessageDialog(null, "文件上传成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // }else{
                    //     //System.out.println("上传失败");
                    //     JOptionPane.showMessageDialog(null, "服务器繁忙，请稍后再试", "失败", JOptionPane.INFORMATION_MESSAGE);
                    // }
                }
            }
        });


        //上传至服务器的按钮操作【这个地方要携带者session信息，以及解压好的文件夹，里面是包含着解压完成的文件的】
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //选择好的文件，请求上传的url信息，定位每个人的url信息，即每个人对应着一个上传连接的url，以及session信息
                // 但是在执行这个操作之前需要先创建好问题，把下面的这些步骤成功信息全部写到日志当中



                logger.info("创建问题开始..."); //把这些信息也全部加入到textArea当中
                //1.创建问题操作【这个操作需要携带session信息,所以Session信息我会在构造方法当中当作参数传进来】
                //读取创建问题的IP地址，然后传入比哦但信息

                logger.info("创建问题结束！！！");

                logger.info("解压文件开始...");
                //2.解压文件操作 解压成功之后，我还得知道解压好的文件路径，因为之后我要遍历每一个文件，然后上传到服务器
                boolean flag = unzipFile.unzipFile(selectedFile);
                if(flag){
                    logger.info("解压文件成功!!!");
                }else {
                    logger.error("解压文件失败！！！");
                }


                logger.info("定位url开始...");
                //3.定位每个人的url信息操作【这个操作需要携带session信息】

                logger.info("定位url结束");

                logger.info("文件正在上传到服务器...");
                //4.上传至服务器操作【这个操作需要携带session信息，还有定位好的每一个用户的url，这是一个字典】，返回一个bool类型的值
                //这个地方的参数，最少得需要三个，第一个是解压好的文件目录/文件对象，第二个是Map<String,String> 第三个是SessionID

                logger.info("文件上传成功！！！");

            }
        });


        frame.setVisible(true);
    }
}
