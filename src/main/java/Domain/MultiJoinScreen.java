package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiJoinScreen extends JFrame {

    private JTextField ipInput;
    private JTextField portInput;
    private JTextField messageInput;
    private JLabel lastMessageLabel;
    private String lastMessage = "None";
    private TCPClient tcpClient = new TCPClient();

    public MultiJoinScreen(BuildingModeController buildingModeController) {
        setTitle("Network Info Join");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        ipInput = new JTextField();
        portInput = new JTextField();
        messageInput = new JTextField();
        lastMessageLabel = new JLabel("Last message: " + lastMessage);
        JButton connectButton = new JButton("Connect");
        JButton sendButton = new JButton("Send");

        add(new JLabel("IP address:"));
        add(ipInput);
        add(new JLabel("Port:"));
        add(portInput);
        add(connectButton);
        add(new JLabel(""));
        add(lastMessageLabel);
        add(sendButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipInput.getText();
                int port = Integer.parseInt(portInput.getText());
                tcpClient.connectToServer(ip, port);
                if (tcpClient.isConnected()) {
                    lastMessageLabel.setText("Connected to server");
                    connectButton.setEnabled(false);
                    sendButton.setEnabled(true);
                } else {
                    lastMessageLabel.setText("Connection failed");
                }
            }
        });

        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageInput.getText();
                tcpClient.sendMessage(message);
                lastMessage = message;
                lastMessageLabel.setText("Last message: " + lastMessage);
                messageInput.setText("");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
