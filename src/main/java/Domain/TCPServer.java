package Domain;

import java.io.*;
import java.net.*;

public class TCPServer {

    private static String ipAddress;
    private static int port = 5001;
    private static String clientMessage;
    private static MultiHostScreen multiHostScreen;

    public static void main(String[] args) throws IOException {

        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started at IP: " + ipAddress + ", Port: " + port);

            System.out.println("Listening for clients...");
            Socket clientSocket = serverSocket.accept();
            String clientSocketIP = clientSocket.getInetAddress().toString();
            int clientSocketPort = clientSocket.getPort();
            System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort + "]  " + "Client Connection Successful!");

            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());

            String clientMessage = dataIn.readUTF();

            dataOut.writeUTF(multiHostScreen.getGivenMessage());

            dataIn.close();
            dataOut.close();
            clientSocket.close();
            serverSocket.close();
        } catch (UnknownHostException e) {
            System.out.println("IP address could not be determined.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException occurred.");
            e.printStackTrace();
        }
    }

    public int  getPort() {return port;}

    public String getMessage() {return clientMessage;}





}
