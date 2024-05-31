package Domain;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {
    private Socket socket;
    ObjectInputStream objectIn;
    ObjectOutputStream objectOut;


    private boolean isConnected = false;

    public void connectToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            System.out.println("Connected to server at " + ip + ":" + port);

            try {
                ArrayList<Rectangle> placedBarriersMulti = (ArrayList<Rectangle>) objectIn.readObject();
                ArrayList<Integer> placedBarrierTypesMulti = (ArrayList<Integer>) objectIn.readObject();
            } catch (ClassNotFoundException ex) {
                isConnected = false;
                ex.printStackTrace();
            }

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
                ArrayList<Integer> intValues = (ArrayList<Integer>) objectIn.readObject();
            }
        } catch (IOException ex) {
            isConnected = false;
            System.err.println("Disconnected from server");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            isConnected = false;
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            objectOut.writeUTF(message);
        } catch (IOException ex) {
            System.err.println("Failed to send message");
            ex.printStackTrace();
        }
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
