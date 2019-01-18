package battletank.scenes.screen.chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chatwindow {
    private JTextPane textFieldChat;
    private JTextField textFieldInput;
    private JButton buttonSend;


    public chatwindow() {
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked.");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("chatwindow");
        frame.setContentPane(new chatwindow().textFieldChat);

    }

}
