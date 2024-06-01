package Domain;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {
    private Socket socket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
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
                // Process the received barriers and types
                System.out.println("Received barriers and types from server.");
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
                String message = (String) objectIn.readObject();
                if (message != null) {
                    // Process received messages
                    System.out.println("Received message: " + message);
                }
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
            objectOut.writeObject(message);
            //objectOut.flush();
        } catch (IOException ex) {
            System.err.println("Failed to send message");
            ex.printStackTrace();
        }
    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        return (String) objectIn.readObject();
    }
    public GameInfo receiveGameInfo() {
        try {
            if (objectIn != null) {
                return (GameInfo) objectIn.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to receive GameInfo: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public void stopConnection() {
        try {
            if (objectIn != null) objectIn.close();
            if (objectOut != null) objectOut.close();
            if (socket != null) socket.close();
            isConnected = false;
            System.out.println("Disconnected from server.");
        } catch (IOException ex) {
            System.err.println("Failed to close connection: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
