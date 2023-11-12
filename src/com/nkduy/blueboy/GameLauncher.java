package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GameLauncher extends JFrame implements ActionListener {

    JLabel title, version;
    JButton startButton;
    JPanel panel;

    String[] args;
    Color primaryColor = new Color(45, 87, 223);

    public static GameInfo gameInfo;
    public static GamePanel gamePanel;
    public static String username = " ";

    public static boolean isLogged = false;

    public GameLauncher(String[] args) {
        this.args = args;

        gameInfo = new GameInfo();
        gamePanel = new GamePanel();

        File f = new File("config.txt");
        if (!f.exists() && f.isDirectory()) {
            gamePanel.config.saveConfig();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
            gamePanel.config.loadConfig();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        setTitle("Game Launcher");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon iconImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/boy/down_1.png")));
        setIconImage(iconImage.getImage());

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        if (isLogged) {
            title = new JLabel("Welcome back " + username + "!!!");
            startButton = new JButton("Start Game");
        } else {
            title = new JLabel("Welcome to Blue Boy 2D");
            startButton = new JButton("Login");
        }

        title.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(title, constraints);

        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(primaryColor);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(startButton, constraints);

        version = new JLabel("v" + gameInfo.version);
        version.setFont(new Font("Arial", Font.PLAIN, 13));
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(version, constraints);

        add(panel);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int windowX = (screenWidth - windowWidth) / 2;
        int windowY = (screenHeight - windowHeight) / 2;

        setLocation(windowX, windowY);
    }

    public void actionPerformed(ActionEvent e) {
        if (!isLogged) {
            UIManager.put("OptionPane.background", new Color(38, 38, 38));
            UIManager.put("Panel.background", new Color(38, 38, 38));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("TextField.background", Color.BLACK);
            UIManager.put("TextField.foreground", Color.WHITE);
            UIManager.put("Button.background", primaryColor);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/boy/down_1.png")));
            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(newImage);
            do {
                username = (String)JOptionPane.showInputDialog(this, "Enter your username (10 characters):", "Input", JOptionPane.PLAIN_MESSAGE, newIcon, null, null);

                if (username == null) {
                    username = " ";
                    break;
                }
            } while (username.isEmpty() || username.length() > 10);
            gamePanel.config.saveConfig();
        } else {
            Main.main(args);
        }

        dispose();
    }

    public static void main(String[] args) {
        new GameLauncher(args);
    }
}
