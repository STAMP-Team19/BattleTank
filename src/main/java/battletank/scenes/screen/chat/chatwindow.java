package battletank.scenes.screen.chat;

import spaces.game.connect.chat.ChatMsgManager;
import spaces.game.connect.chat.IChatListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chatwindow implements IChatListener, Runnable {
    private JTextPane textFieldChat;
    private JTextField textFieldInput;
    private JButton buttonSend;

    private String ip;
    private String username;

    private ChatMsgManager manager;

    public chatwindow(String username, String ip) {

        this.username = username;
        this.ip = ip;

        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked.");
            }
        });
    }

    @Override
    public void notifyNewMessage(String username, String message) {
        System.out.println("TODO IMPLEMENT");
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("chatwindow");
        frame.setContentPane(new chatwindow(username, ip).textFieldChat);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        manager = new ChatMsgManager(username, this, ip);
        manager.sendMessage(username, "Hey");
    }
}
