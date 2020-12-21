import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Communications {
    private Socket listenSocket;
    private DataInputStream inputStream;
    private ServerSocket serverSocket;

    public Communications() {
        this.connect();
    }

    private void connect() {
        try {
            serverSocket = new ServerSocket(7777);
            listenSocket = serverSocket.accept();
            inputStream = new DataInputStream(listenSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String listen() throws Exception {
        return inputStream.readUTF();
    }

    public void close() {
        try {
            inputStream.close();
            listenSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
