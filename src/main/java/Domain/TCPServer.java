package Domain;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {
    private String ipAddress;
    private int port = 5001;
    private MultiHostScreen multiHostScreen;
    private Socket clientSocket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    private boolean isConnected = false;

    public TCPServer(MultiHostScreen multiHostScreen) {
        this.multiHostScreen = multiHostScreen;
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("IP address could not be determined.");
            e.printStackTrace();
        }
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at IP: " + ipAddress + ", Port: " + port);
            multiHostScreen.updateStatus("Server started at IP: " + ipAddress + ", Port: " + port);

            System.out.println("Listening for clients...");
            multiHostScreen.updateStatus("Listening for clients...");

            clientSocket = serverSocket.accept();
            String clientSocketIP = clientSocket.getInetAddress().toString();
            int clientSocketPort = clientSocket.getPort();
            System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort + "] Client Connection Successful!");
            multiHostScreen.updateStatus("Client connected from IP: " + clientSocketIP + ", Port: " + clientSocketPort);
            isConnected = true;

            objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objectIn = new ObjectInputStream(clientSocket.getInputStream());

            ArrayList<Rectangle> placedBarriersMulti = GameLayoutPanel.placedBarriers;
            ArrayList<Integer> placedBarrierTypesMulti = GameLayoutPanel.placedBarrierTypes;

            objectOut.writeObject(placedBarriersMulti);
            objectOut.writeObject(placedBarrierTypesMulti);

            while (true) {
                try {
                    int scoreStatus = GameInfo.getScore();
                    int livesStatus = GameInfo.getLives();
                    int barrierCountStatus = GameView.remainingBarrierCount();

                    ArrayList<Integer> gameStatus = new ArrayList<>();
                    gameStatus.add(scoreStatus);
                    gameStatus.add(livesStatus);
                    gameStatus.add(barrierCountStatus);

                    objectOut.writeObject(gameStatus);

                } catch (EOFException e) {
                    System.out.println("Client disconnected.");
                    break;
                }
            }

            objectIn.close();
            objectOut.close();
            clientSocket.close();
            multiHostScreen.updateStatus("Client disconnected. Listening for new clients...");

        } catch (IOException e) {
            System.err.println("IOException occurred.");
            e.printStackTrace();
            multiHostScreen.updateStatus("Server error: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            if (objectOut != null) {
                objectOut.writeObject(message);
                objectOut.flush();
            }
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}