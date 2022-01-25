import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame implements ActionListener {
    static JPanel topPanel;
    static JTextField inputTextField;
    static JButton sendButton;
    static JTextArea chatTextArea;

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInput;
    static DataOutputStream dataOutput;

    Server() {
        // FRAME
        setSize(450, 600);
        setLocation(400, 150);
        setLayout(null);
        setUndecorated(true); // hides default decorations like top header

        // TOP PANEL
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBackground(new Color(7, 94, 84));
        topPanel.setBounds(0, 0, 450, 70);
        add(topPanel);

        // BACK_ARROW
        ImageIcon backArrowImageIcon = new ImageIcon(
                ClassLoader.getSystemResource("icons/back_arrow.png"));
        Image backArrowImage = backArrowImageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        backArrowImageIcon = new ImageIcon(backArrowImage);
        JLabel backArrowImageIconLabel = new JLabel(backArrowImageIcon);
        backArrowImageIconLabel.setBounds(5, 20, 30, 30);
        topPanel.add(backArrowImageIconLabel);

        // ADDING MOUSE LISTENER TO BACK_ARROW LABEL
        backArrowImageIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // PROFILE PIC
        ImageIcon profilePicImageIcon = new ImageIcon(
                ClassLoader.getSystemResource("icons/1.png"));
        Image profilePicImage = profilePicImageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        profilePicImageIcon = new ImageIcon(profilePicImage);
        JLabel profilePicLabel = new JLabel(profilePicImageIcon);
        profilePicLabel.setBounds(45, 5, 60, 60);
        topPanel.add(profilePicLabel);

        // PROFILE NAME
        JLabel profileNameLabel = new JLabel("Farhan");
        profileNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        profileNameLabel.setForeground(Color.WHITE);
        profileNameLabel.setBounds(115, 15, 100, 18);
        topPanel.add(profileNameLabel);

        // ACTIVE STATUS
        JLabel activeStatusLabel = new JLabel("Active Now");
        activeStatusLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 12));
        activeStatusLabel.setForeground(Color.WHITE);
        activeStatusLabel.setBounds(115, 35, 100, 14);
        topPanel.add(activeStatusLabel);

        // 3 DOT MENU
        ImageIcon dotsMenuImageIcon = new ImageIcon(
                ClassLoader.getSystemResource("icons/3dot_menu.png"));
        Image dotsMenuImage = dotsMenuImageIcon.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        dotsMenuImageIcon = new ImageIcon(dotsMenuImage);
        JLabel dotsMenuLabel = new JLabel(dotsMenuImageIcon);
        dotsMenuLabel.setBounds(405, 5, 30, 60);
        topPanel.add(dotsMenuLabel);

        // PHONEImageIcon ICON
        ImageIcon phoneImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image phoneImage = phoneImageIcon.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        phoneImageIcon = new ImageIcon(phoneImage);
        JLabel phoneLabel = new JLabel(phoneImageIcon);
        phoneLabel.setBounds(365, 5, 35, 60);
        topPanel.add(phoneLabel);

        // VIDEO ICON
        ImageIcon videoImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image videoImage = videoImageIcon.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
        videoImageIcon = new ImageIcon(videoImage);
        JLabel videoLabel = new JLabel(videoImageIcon);
        videoLabel.setBounds(310, 5, 30, 60);
        topPanel.add(videoLabel);

        // TEXT FIELD
        inputTextField = new JTextField();
        inputTextField.setBounds(3, 567, 350, 30);
        inputTextField.setFont(new Font("SANS_SERIF", Font.PLAIN, 18));
        add(inputTextField);

        // SEND BUTTON
        sendButton = new JButton("Send");
        sendButton.setBounds(356, 567, 91, 30);
        sendButton.setBackground(new Color(7, 84, 94));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
        sendButton.addActionListener(this);
        add(sendButton);

        // TEXT AREA
        chatTextArea = new MyTextArea("icons/background.jpg", 444, 491);
        chatTextArea.setBounds(3, 73, 444, 491);
        chatTextArea.setBackground(new Color(1, 1, 1, (float) 0.01));
        chatTextArea.setFont(new Font("SANS_SERIF", Font.PLAIN, 18));
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);
        add(chatTextArea);
    }

    // overriding method in ActionListener - it is mandatory
    public void actionPerformed(ActionEvent ae) {
        try {
            String out =  inputTextField.getText();
            chatTextArea.setText(chatTextArea.getText() + "\n\t\t" + out);
            inputTextField.setText("");
            dataOutput.writeUTF(out);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        new Server().setVisible(true);
        try {
            serverSocket = new ServerSocket(6001);
            socket = serverSocket.accept();

            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());

            while(true) {
                String messageInput = dataInput.readUTF();
                chatTextArea.setText(chatTextArea.getText() + "\n" + messageInput);
            }
            // serverSocket.close();
            // socket.close();
        }
        catch(Exception e) {
        }
    }
}
