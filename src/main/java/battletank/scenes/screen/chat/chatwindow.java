package battletank.scenes.screen.chat;

import spaces.game.connect.chat.ChatMsgManager;
import spaces.game.connect.chat.IChatListener;

import javax.swing.*;
import java.awt.*;

public class chatwindow implements IChatListener, Runnable {
    private JTextPane textFieldChat;
    private JTextField textFieldInput;
    private JButton sendMessageButton;
    private JPanel JPanelMain;

    private String ip, username;
    private ChatMsgManager manager;


    public chatwindow(String username, String ip) {
        this.username = username;
        this.ip = ip;


    }

    @Override
    public void notifyNewMessage(String username, String message) {
        System.out.println("TODO IMPLEMENT");
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("chatwindow");
        frame.setContentPane(new chatwindow(username, ip).JPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400));
        frame.pack();
        frame.setVisible(true);

        manager = new ChatMsgManager(username, this, ip);
        manager.sendMessage(username, "Test message: Chat started correctly.");
    }
}
