import java.net.*;
import java.io.*;

public class Communications {
    private Socket socket;
    private DataOutputStream outputStream;

    public Communications() {

    }

    public void connect() {
        try {
            socket = new Socket("localhost", 6666);
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            socket = null;
            outputStream = null;
        }
    }

    public void sendMessage(String string) {
        if (!string.equals("")) {
            try {
                outputStream.writeUTF(string);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
