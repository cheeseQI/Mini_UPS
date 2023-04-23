package messaging;

import protocol.AmazonUps;
import protocol.WorldUps;

import java.io.IOException;
import java.net.Socket;
// test class, only used for mock amazon class
public class UpsClient extends SocketClient{
    public UpsClient(Socket socket) throws IOException {
        super(socket);
    }

    public UpsClient(String host, int port) {
        super(host, port);
    }

    public AmazonUps.UAConnect receiveUAConnect() {
        try {
            return AmazonUps.UAConnect.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    public AmazonUps.UACommands receiveUACommands() {
        try {
            return AmazonUps.UACommands.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }
}
