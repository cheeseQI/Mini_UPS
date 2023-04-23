package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import common.BuilderUtil;
import common.SeqGenerator;
import protocol.WorldUps;

import java.io.*;
import java.net.Socket;
public class UserClient extends SocketClient {

    public UserClient(String host, int port) {
        super(host, port);
    }
    
    public void sendQueryByUserId(Integer userId){}
    public void sendQueryByPackageId(Integer packageId){}
    public void sendRedirect(Integer packageId,Integer destX,Integer destY){}
}
