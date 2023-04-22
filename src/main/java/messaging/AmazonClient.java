package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import protocol.AmazonUps;
import protocol.WorldUps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class AmazonClient extends SocketClient {

    public AmazonClient(Socket socket) throws IOException {
        super(socket);
    }

    public AmazonClient(String host, int port) {
        super(host, port);
    }

    public AmazonUps.AUConnected receiveAUConnected() {
        try {
            return AmazonUps.AUConnected.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    public AmazonUps.AUCommands receiveARequest() {
        try {
            return AmazonUps.AUCommands.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }
}
