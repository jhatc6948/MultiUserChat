package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * User list pane extending jPanel and implemting USerStatusListener
 * this shows a window with online users
 */

public class UserListPane extends JPanel implements UserStatusListener {


    private final ChatClient client;
    private JList<String> userListUI;
    private DefaultListModel<String> userListModel;


    public UserListPane(ChatClient client) {
        this.client = client;
        this.client.addUserStatusListener(this);

        userListModel = new DefaultListModel<>();
        userListUI = new JList<>(userListModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(userListUI), BorderLayout.CENTER);

        userListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()>1){
                    String login = userListUI.getSelectedValue();
                    MessagePane messagePane = new MessagePane(client, login);

                    JFrame f = new JFrame("Message: " + login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(500, 500);
                    f.getContentPane().add(messagePane, BorderLayout.CENTER);
                    f.setVisible(true);
                }
            }
        });

    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 9999);

        UserListPane userListPane = new UserListPane(client);
        JFrame frame = new JFrame("User List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,600);

        frame.getContentPane().add(userListPane, BorderLayout.CENTER);
        frame.setVisible(true);

        if (client.connect());
            try {
                client.login("josh", "josh");
                System.out.println("Working?");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void online(String login) {
        System.out.println("Test");
        userListModel.addElement(login);
        System.out.println("TESTING");
    }

    @Override
    public void offline(String login) {
        userListModel.removeElement(login);

    }

}