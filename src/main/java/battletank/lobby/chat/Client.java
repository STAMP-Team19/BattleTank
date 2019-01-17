package battletank.lobby.chat;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {

        try {

            // Set the URI of the loby of the chat server
            String uri = "tcp://127.0.0.1:9001/lobby?keep";

            // Connect to the remote lobby
            System.out.println("Connecting to lobby " + uri + "...");
            RemoteSpace lobby = new RemoteSpace(uri);

            // Read user name from the console
            System.out.print("Enter your name: ");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String name = input.readLine();

            // Read chatroom from the console
            System.out.print("Select a chat room: ");
            String chatroom = input.readLine();

            // Send request to enter chatroom
            lobby.put("enter", name, chatroom);

            // Get response with chatroom URI
            Object[] response = lobby.get(new ActualField("roomURI"), new ActualField(name), new ActualField(chatroom), new FormalField(String.class));
            String chatroom_uri = (String) response[3];
            System.out.println("Connecting to chat space " + chatroom_uri);
            RemoteSpace chatroom_space = new RemoteSpace(chatroom_uri);

            new Thread(new msgListener(chatroom_space)).start();

            // Keep sending whatever the user types
            System.out.println("Start chatting...");
            while (true) {
                String message = input.readLine();
                chatroom_space.put(name, message);
            }


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class msgListener implements Runnable {

    RemoteSpace remoteSpaceClient;
    int msgCounter = 0;

    @Override
    public void run() {

        try {
            try {
                msgCounter = (int) remoteSpaceClient.queryp(new ActualField("serverCount"), new FormalField(Integer.class))[1];
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.getMessage();
        }


        while (true) {
            try {
                Object[] newMessage = remoteSpaceClient.query(new FormalField(String.class), new FormalField(String.class), new ActualField(msgCounter));
                System.out.println(newMessage[0] + ":" + newMessage[1]);
                msgCounter++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public msgListener(RemoteSpace remoteSpace) {
        remoteSpaceClient = remoteSpace;

    }
}
