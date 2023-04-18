package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Message;
import protocol.WorldUps;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient implements Communicator {

    private String host;
    private int port;
    private Socket socket;
    private CodedInputStream codedInputStream;
    private CodedOutputStream codedOutputStream;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() {
        try {
            socket = new Socket(host, port);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            codedInputStream = CodedInputStream.newInstance(inputStream);
            codedOutputStream = CodedOutputStream.newInstance(outputStream);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + host);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error connecting to " + host + ":" + port);
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error disconnecting from " + host + ":" + port);
            e.printStackTrace();
        }
    }

    public WorldUps.UConnected receiveUConnected() {
        try {
            return WorldUps.UConnected.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }

    //todo: resend function for <seq, ugopickup>, <seq, ugodeliver> ...
    @Override
    public void sendMessage(Message message) {
        try {
            codedOutputStream.writeUInt32NoTag(message.toByteArray().length);
            message.writeTo(codedOutputStream);
            codedOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending message to " + host + ":" + port);
            e.printStackTrace();
        }
    }

    @Override
    public WorldUps.UResponses receiveMessage() {
        try {
            return WorldUps.UResponses.parseFrom(codedInputStream.readByteArray());
        } catch (IOException e) {
            System.err.println("Error receiving message from " + host + ":" + port);
            e.printStackTrace();
            return null;
        }
    }
}

