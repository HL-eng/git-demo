package com.fdmtek.gui;

import javax.swing.*;

public class LogUploadSystemMainWindow {
    public static void main(String[] args) {
        //新创建一个线程
        SwingUtilities.invokeLater(new Runnable() {
            //因为窗口是在构造方法当中定义的，所以此处直接new对象就行
            public void run() {
                //new LogUploadSystemLoginWindow();
                //new LogUploadSystemWindow();
                new LogUploadSystemWindow4(null);
            }
        });
    }
}
