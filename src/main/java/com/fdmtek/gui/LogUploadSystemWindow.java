package com.fdmtek.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LogUploadSystemWindow extends JFrame {

    private JButton selectFileButton;
    private JButton uploadButton;

    public LogUploadSystemWindow() {
        setTitle("日志上传系统");
        setSize(600,360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());


        selectFileButton = new JButton("选择文件");
        selectFileButton.addActionListener(new ActionListener() {
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

                int result = fileChooser.showOpenDialog(LogUploadSystemWindow.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // 处理选择的文件
                    // TODO: 在这里添加文件处理的逻辑
                    System.out.println("选择的文件: " + selectedFile.getAbsolutePath());//这个地方可以考虑做成弹窗，把需要上传的文件提示一下
                }
            }
        });

        uploadButton = new JButton("上传文件");

        add(selectFileButton);
        add(uploadButton);

        pack();
        setVisible(true);
    }
}
