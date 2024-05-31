package Domain;

import java.io.*;
import java.net.*;

public class TCPServer {

    private String ipAddress;
    private int port = 5001;
    private String clientMessage;
    private MultiHostScreen multiHostScreen;

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

            Socket clientSocket = serverSocket.accept();
            String clientSocketIP = clientSocket.getInetAddress().toString();
            int clientSocketPort = clientSocket.getPort();
            System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort + "] Client Connection Successful!");
            multiHostScreen.updateStatus("Client connected from IP: " + clientSocketIP + ", Port: " + clientSocketPort);

            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                try {
                    clientMessage = dataIn.readUTF();
                    System.out.println("Received from client: " + clientMessage);
                    multiHostScreen.updateLastMessage(clientMessage);

                    dataOut.writeUTF("Hi, this is coming from Server!");

                } catch (EOFException e) {
                    System.out.println("Client disconnected.");
                    break;
                }
            }

            dataIn.close();
            dataOut.close();
            clientSocket.close();
            multiHostScreen.updateStatus("Client disconnected. Listening for new clients...");

        } catch (IOException e) {
            System.err.println("IOException occurred.");
            e.printStackTrace();
            multiHostScreen.updateStatus("Server error: " + e.getMessage());
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getMessage() {
        return clientMessage;
    }
}
