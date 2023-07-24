package com.fdmtek.gui;

import com.fdmtek.createproject.CreateProject;
import com.fdmtek.login.Login;
import com.fdmtek.utils.LoginReturnCode;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;


public class LogUploadSystemLoginWindow {
    //private JFrame frame;
    private Login loginObj = new Login();

    //创建项目的对象
    private CreateProject createProjectObj = new CreateProject();

    // 获取logger实列,获取日志记录器并读取配置文件,这个记录器将负责控制日志信息
    private static Logger logger = Logger.getLogger(LogUploadSystemWindow2.class);

    //直接在构造方法当中声明窗口
    public LogUploadSystemLoginWindow(){
        JFrame frame = new JFrame("日志上传系统");
        //设置窗口大小
        frame.setSize(600,360);
        //设置窗口弹出位置
        frame.setLocation(700,300);
        //设置窗口大小不可变
        frame.setResizable(true);
        //用户单击窗口的关闭按钮时程序执行的操作 // **************
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //定义面板
        JPanel panel = new JPanel(null);

        //定义一个标签
        JLabel label = new JLabel("登 录 界 面");
        label.setFont(new Font("宋体", Font.BOLD, 30)); // 设置字体、样式和大小
        label.setBounds(210, 0, 300, 50); // 设置标签的位置和大小

        //这两个地方要有文本框，其中一个是文本框，用于输入用户名，另外一个是密码框，用于输入密码
        // 创建用户名标签和文本框
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("宋体", Font.BOLD, 25)); // 设置字体、样式和大小
        usernameLabel.setBounds(100, 70, 150, 50); // 设置标签的位置和大小
        JTextField usernameField = new JTextField();
        usernameField.setBounds(240,85,200,30);//设置文本框的的位置

        // 创建密码标签和密码框
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("宋体", Font.BOLD, 25)); // 设置字体、样式和大小
        passwordLabel.setBounds(100, 130, 150, 50); // 设置标签的位置和大小
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(240,145,200,30);//设置密码框的位置

        JButton exitButton = new JButton("退出");
        exitButton.setSize(80,40);
        exitButton.setLocation(170,200);
        exitButton.setFont(new Font("宋体", 1, 20));

        JButton loginButton = new JButton("登录");
        loginButton.setSize(80,40);
        loginButton.setLocation(350,200);
        loginButton.setFont(new Font("宋体", 1, 20));

        //将定义好的标签加入到panel面板上面
        panel.add(label);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(exitButton);
        panel.add(loginButton);
        frame.add(panel);//将上传按钮添加到frame当中
        //退出系统按钮
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //直接退出系统即可
                System.exit(0);
            }
        });

        //添加登录按钮的单击事件，实际的登录过程当中就要请求redmine服务器了
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //得到用户名文本框的用户名
                String username = usernameField.getText();
                //得到密码框当中的密码 passwordField.getPassword()得到的是一个字符数组，要将这个字符数组转为字符串
                String password = new String(passwordField.getPassword());
                //System.out.println(username+"-"+password);
                //调用请求后端登录的业务逻辑代码（用于用户的登录验证）
                Map<String, String> loginMessage = loginObj.login(username, password);
                if("1".equals(loginMessage.get("loginCode"))){
                    JOptionPane.showMessageDialog(null, "用户名不能为空！", "登录提示框", JOptionPane. ERROR_MESSAGE);
                }else if("2".equals(loginMessage.get("loginCode"))){
                    JOptionPane.showMessageDialog(null, "密码不能为空！", "登录提示框", JOptionPane. ERROR_MESSAGE);
                }else if("4".equals(loginMessage.get("loginCode"))){
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "登录提示框", JOptionPane. ERROR_MESSAGE);
                }else {
                    /**登录成功，提示登录成功，0.5秒之后切换到上传文件的桌面，
                     *所以在上传文件的桌面上也可以定义一个返回按钮，可以返回到登录界面
                     * */
                    JOptionPane.showMessageDialog(null, "登录成功", "登录提示框", JOptionPane.INFORMATION_MESSAGE);
                    //关闭当前窗口，切换到上传文件的窗口
                    frame.setVisible(false); //隐藏掉当前窗口

                    //在这个地方我就要把创建项目的操作给完成，创建问题的地方好像有上传一个日志文件
                    //先获取session信息
                    String sessionID = loginMessage.get("sessionID"); //将这个sessionId信息传到创建项目的url当中

                    logger.info("创建项目开始...");
                    try {
                        createProjectObj.testCreateProject(sessionID);
                        logger.info("创建项目成功！！！");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        logger.info(ioException);
                    }

                    //进入上传文件的界面
                    new LogUploadSystemWindow2(sessionID);

                }
            }
        });
        frame.setVisible(true);
    }
}
