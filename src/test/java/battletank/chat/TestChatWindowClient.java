package battletank.chat;

import battletank.scenes.screen.chat.chatwindow;

public class TestChatWindowClient {
    public static void main(String[] args) {
        chatwindow window = new chatwindow("Mads", "10.16.172.138");

        new Thread(window).start();
    }
}
