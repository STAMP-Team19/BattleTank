package battletank.scenes.screen.chat;

import spaces.game.connect.chat.ChatMsgManager;
import spaces.game.connect.chat.IChatListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class chatwindow implements IChatListener, Runnable, KeyListener {
    private JTextField textFieldInput;
    private JButton buttonSendMessage;
    private JPanel JPanelMain;
    private JTextArea textAreaChat;

    private String ip, username;
    private ChatMsgManager manager;
    Dimension border = new Dimension(500, 400);


    public chatwindow(String username, String ip) {
        this.username = username;
        this.ip = ip;

        buttonSendMessage.addKeyListener(this);


        buttonSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgInput = textFieldInput.getText();
                textAreaChat.append(username + ": " + msgInput + "\n");
                textFieldInput.setText("");
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
        frame.setContentPane(new chatwindow(username, ip).JPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(border);


        frame.pack();
        frame.setVisible(true);

        manager = new ChatMsgManager(username, this, ip);
        manager.sendMessage(username, "Test message: Chat started correctly.");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("Enter pushed");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
