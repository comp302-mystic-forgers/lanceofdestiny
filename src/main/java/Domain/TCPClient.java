package Domain;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {

    private static Socket socket;
    private static DataInputStream dataIn;
    private static DataOutputStream dataOut;
    private boolean isConnected = false;

    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            socket.connect(new java.net.InetSocketAddress(ip, port), 1000);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            isConnected = true;
            System.out.println("Connected to server at " + ip + ":" + port);

            new Thread(this::listenForMessages).start();
        } catch (IOException ex) {
            System.err.println("Connection failed");
            ex.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            while (true) {
                String message = dataIn.readUTF();
                System.out.println("Server: " + message);
            }
        } catch (IOException ex) {
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

    public boolean getIsConnected() {return isConnected;}
}


