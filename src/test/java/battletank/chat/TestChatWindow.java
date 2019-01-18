package battletank.chat;

import battletank.scenes.screen.chat.chatwindow;

import javax.swing.*;

public class TestChatWindow {

    public static void main(String[] args) {
        chatwindow window = new chatwindow("Peter", "0.0.0.0");

        new Thread(window).start();
    }
}
