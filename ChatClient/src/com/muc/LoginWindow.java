package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * login window extending jframe
 * Its a login window to login with your username and password in jFrame
 */
public class LoginWindow extends JFrame {



    private final ChatClient client;
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    public LoginWindow(){
        super("Login");

        this.client = new ChatClient("localhost", 9999);
        client.connect();

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(loginField);
        p.add(passwordField);
        p.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }


        });

        getContentPane().add(p, BorderLayout.CENTER);

        pack();

        setVisible(true);

    }
    private void doLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();
        System.out.println(login + " " + password);
        try {
            //show error message
            if(client.login(login, password)){
                UserListPane userListPane = new UserListPane(client);
                JFrame frame = new JFrame("User List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,600);

                frame.getContentPane().add(userListPane, BorderLayout.CENTER);
                frame.setVisible(true);
                //bring up the user list window
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "invalid login/password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        LoginWindow loginWin = new LoginWindow();
        loginWin.setVisible(true);

    }
}
