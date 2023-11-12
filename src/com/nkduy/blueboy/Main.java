package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static JFrame window;
    public static GameInfo gameInfo;
    public static String username = " ";

    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        window = new JFrame();
        gameInfo = new GameInfo();

        // This lets the window properly close when the user clicks the close ("X") button.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

//      gameInfo.setShowVersion(true);
        gameInfo.setWindowWithTitle();
        new Main().setIcon();

        window.add(gamePanel);

//      gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        // Cause this Window to be sized to fix the preferred size and layout of its subcomponents (= GamePanel).
        window.pack();

        // This does not specify the location of the window.
        // Instead, the window will be displayed at the center of the screen.
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/boy/down_1.png")));
        window.setIconImage(icon.getImage());
    }
}
