package messaging;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class SocketClient implements Communicator{
    protected String host;
    protected int port;
    protected Socket socket;
    protected CodedInputStream codedInputStream;
    protected CodedOutputStream codedOutputStream;

    //pass in socket, act as server socket
    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        codedInputStream = CodedInputStream.newInstance(inputStream);
        codedOutputStream = CodedOutputStream.newInstance(outputStream);
    }

    //pass in target host and port, act as client socket
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
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
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error disconnecting from " + host + ":" + port);
            e.printStackTrace();
        }
    }
}
