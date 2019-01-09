package commands;

public class ConnectionTest {

    public static void main(String[] args){
        CommandRetriever retriever = new CommandRetriever();
        new Thread(retriever).start();
        CommandSender sender = new CommandSender();
        for(int i = 0; i<100; i++) {
            sender.sendCommand("hello"+i);
        }
    }
}
