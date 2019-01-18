package battletank.chat;

import org.jspace.SequentialSpace;

public class Chat {
    private SequentialSpace chatspace;

    public Chat(SequentialSpace chatspace) {
        this.chatspace = chatspace;
    }


}

class MessageListener implements Runnable{

    SequentialSpace chatspace;

    public MessageListener(SequentialSpace chatspace) {
        this.chatspace = chatspace;
    }

    @Override
    public void run() {
        while(true){

        }
    }
}
