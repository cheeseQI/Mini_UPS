package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import protocol.WorldUps;

import java.net.Socket;

public class AmazonClient extends SocketClient {

    public AmazonClient(String host, int port) {
        super(host, port);
    }

    //public WorldAUps.AUConnected receiveAUConnected();

    //public WorldAUps.AUxx receiveAURequest();

    //public WorldAUps.AUxx receiveAUResponse();
}
