package messaging;

import com.google.protobuf.Message;
import protocol.WorldUps;

import java.io.IOException;

public interface Communicator {
    void disconnect();
    void sendMessage(Message message) throws IOException;
}
