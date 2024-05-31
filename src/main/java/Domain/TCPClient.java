package Domain;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private boolean isConnected = false;

    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            isConnected = true;
            System.out.println("Connected to server at " + ip + ":" + port);

            new Thread(this::listenForMessages).start();
        } catch (IOException ex) {
            isConnected = false;
            System.err.println("Connection failed");
            ex.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            while (isConnected) {
                String message = dataIn.readUTF();
                System.out.println("Server: " + message);
            }
        } catch (IOException ex) {
            isConnected = false;
            System.err.println("Disconnected from server");
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            dataOut.writeUTF(message);
        } catch (IOException ex) {
            System.err.println("Failed to send message");
            ex.printStackTrace();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
