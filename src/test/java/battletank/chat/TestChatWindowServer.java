package battletank.chat;

import battletank.lobby.PlayerInfo;
import battletank.scenes.screen.chat.chatwindow;
import spaces.game.connect.chat.ChatMsgManager;
import spaces.game.hosting.chat.ChatClient;
import spaces.game.hosting.chat.ChatProvider;

import javax.swing.*;
import java.util.ArrayList;

public class TestChatWindowServer {

    public static void main(String[] args) {

        ChatProvider provider = new ChatProvider();

        provider.createChat();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chatwindow window = new chatwindow("Peter", "0.0.0.0");

        window.initOwner("Peter");

        new Thread(window).start();
    }
}
