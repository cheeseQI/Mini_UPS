package messaging;

import java.io.IOException;
import java.net.Socket;

public class AmazonMock {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 7474);
        while (true) {
            System.out.println("socket with ups");
            socket.getInputStream();
        }
    }
}
