import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Communications {
    private Socket listenSocket;
    private Socket writeSocket;
    private DataInputStream inputStream;
    private ServerSocket serverSocket;
    private DataOutputStream outputStream;

    public Communications() {
        this.connect();
    }

    public String listen() throws Exception {
        return inputStream.readUTF();
    }

    public void connect() {
        try {
            serverSocket = new ServerSocket(6666);
            listenSocket = serverSocket.accept();
//            writeSocket = new Socket("172.17.0.1", 1755);
            writeSocket = new Socket("localhost", 7777);

            inputStream = new DataInputStream(listenSocket.getInputStream());
            outputStream = new DataOutputStream(writeSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String string) throws Exception {
        if (!string.equals("")) {
            outputStream.writeUTF(string);
            outputStream.flush();
        }
    }

    public void close() {
        try {
            this.send("ESCAPE");
            inputStream.close();
            listenSocket.close();
            writeSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
