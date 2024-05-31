package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiHostScreen extends JFrame{

    private JLabel ipLabel;
    private JLabel portLabel;
    private JLabel statusLabel;
    private JTextField messageInput;
    private JLabel lastMessageLabel;
    private String lastMessage = "None";
    private  String givenMessage;

    private String ipAddress;
    private TCPServer tcpServer = new TCPServer();
    private TCPClient tcpClient = new TCPClient();


    public MultiHostScreen(BuildingModeController buildingModeController) {

        setTitle("Network Info Host");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("IP address could not be determined.");
            e.printStackTrace();
        }

        ipLabel = new JLabel("IP address: " + ipAddress);
        portLabel = new JLabel("Port: " + tcpServer.getPort());
        statusLabel = new JLabel("Status: " + tcpClient.getIsConnected());
        messageInput = new JTextField();
        lastMessageLabel = new JLabel("Last message: " + tcpServer.getMessage());
        JButton sendButton = new JButton("Send");

        // Add components to the frame
        add(ipLabel);
        add(portLabel);
        add(statusLabel);
        add(new JLabel("Message:"));
        add(messageInput);
        add(lastMessageLabel);
        add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                givenMessage = messageInput.getText();
                tcpClient.sendMessage(givenMessage);
            }
        });

        setLocationRelativeTo(null);
    }

    public String getGivenMessage() {return givenMessage;}

}
