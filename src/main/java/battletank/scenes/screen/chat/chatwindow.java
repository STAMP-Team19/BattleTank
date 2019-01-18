package battletank.scenes.screen.chat;

import battletank.lobby.PlayerInfo;
import spaces.game.connect.chat.ChatMsgManager;
import spaces.game.connect.chat.IChatListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
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

    private String ip, name;
    private ChatMsgManager manager;
    Dimension border = new Dimension(500, 400);

    private void addChatUser(String username) {
        manager.addPlayer(new PlayerInfo(username));
    }

    public chatwindow(String name, String ip) {
        this.name = name;
        this.ip = ip;

        if (manager == null)
            manager = new ChatMsgManager(name, this, ip);

        addChatUser(name);

        buttonSendMessage.addKeyListener(this);


        buttonSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgInput = textFieldInput.getText();
                //textAreaChat.append(name + ": " + msgInput + "\n");
                manager.sendMessage(name, msgInput);
                textFieldInput.setText("");
            }
        });
    }


    @Override
    public void notifyNewMessage(String username, String message) {
        textAreaChat.append(username + ": " + message + "\n");
    }


    @Override
    public void run() {
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Chat room"));

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textAreaChat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        middlePanel.add(jScrollPane);


        JFrame frame = new JFrame("chatwindow");
        frame.setContentPane(this.JPanelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(border);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //manager.sendMessage(username, "Test message: Chat started correctly.");
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
