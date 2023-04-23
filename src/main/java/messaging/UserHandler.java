package messaging;

import protocol.AmazonUps;
import protocol.WorldUps;
import protocol.UpsUser;
import java.net.*;
import messaging.*;
import messaging.UserRequestHandler;
import java.io.IOException;
public class UserHandler implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try (ServerSocket serverSocket = new ServerSocket(1029)) {
                UserClient userClient = new UserClient(serverSocket.accept());
                UserRequestHandler userRequestHandler = new UserRequestHandler(userClient);
                Thread thread = new Thread(userRequestHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // private void recvUserRequest() {
    //     Server.amazonClient.sendMessage(uaCommandBuilder.build());
    // }

    // private void sendUserResponse() {
    //     Server.worldClient.sendMessage(uCommandsBuilder.build());
    //     //todo: add other message
    //     // todo: need to do database operation after recv acks
//    }
}
