package battletank.chat;

import battletank.scenes.screen.chat.chatwindow;

public class TestChatWindowClient {
    public static void main(String[] args) {
        chatwindow window = new chatwindow("Peter", "10.16.172.138");

        window.initOwner("Mads");

        new Thread(window).start();
    }
}
