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
    private  String givenMessage;
    private TCPClient tcpClient = new TCPClient();

    public MultiJoinScreen(BuildingModeController buildingModeController) {

        setTitle("Network Info Join");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        ipInput = new JTextField("IP address:");
        portInput = new JTextField("Port:");

        add(ipInput);
        add(portInput);

        JButton connectButton = new JButton("Connect");

        add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipInput.getText();
                int port = Integer.parseInt(portInput.getText());
                tcpClient.connectToServer(ip, port);
            }
        });

        if (tcpClient.getIsConnected()) {

            messageInput = new JTextField();
            lastMessageLabel = new JLabel("Last message: " + lastMessage);
            JButton sendButton = new JButton("Send");

            add(new JLabel("Message:"));
            add(messageInput);
            add(lastMessageLabel);
            add(sendButton);

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    givenMessage = messageInput.getText();
                }
            });

        }

        setLocationRelativeTo(null);
    }
}
