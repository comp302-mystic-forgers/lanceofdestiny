package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiHostScreen extends JFrame {

    private JLabel ipLabel;
    private JLabel portLabel;
    private JLabel statusLabel;
    private JTextField messageInput;
    private JLabel lastMessageLabel;
    private String lastMessage = "None";
    private String givenMessage;
    private TCPServer tcpServer;

    public MultiHostScreen() {
        setTitle("Network Info Host");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            ipLabel = new JLabel("IP address: " + ipAddress);
        } catch (UnknownHostException e) {
            ipLabel = new JLabel("IP address: Not available");
            e.printStackTrace();
        }

        tcpServer = new TCPServer(this);

        portLabel = new JLabel("Port: " + tcpServer.getPort());
        statusLabel = new JLabel("Status: Waiting for connection...");
        messageInput = new JTextField();
        lastMessageLabel = new JLabel("Last message: " + lastMessage);
        JButton sendButton = new JButton("Send");

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
                // Send message logic if needed
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);

        // Start the server in a new thread
        new Thread(() -> tcpServer.startServer()).start();
    }

    public void updateStatus(String status) {
        statusLabel.setText("Status: " + status);
    }

    public void updateLastMessage(String message) {
        lastMessage = message;
        lastMessageLabel.setText("Last message: " + message);
    }

    public String getGivenMessage() {
        return givenMessage;
    }
}
