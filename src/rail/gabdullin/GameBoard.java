package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {

    private int mapSize;

    private Game game;

    private ButtonsPane buttonsPane;

    private Container container;

    GameBoard (Game game) {
        mapSize = 3;
        this.game = game;


        setBounds(400,250, 400,470);
        setTitle("Крестики-Нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = getContentPane();

        buttonsPane = new ButtonsPane(game, this);

        JPanel controlPanel = new JPanel();
        container.setLayout(new BorderLayout());

        JButton newGame = new JButton("Новая игра");
        ActionListener newGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.refreshGame();
            }
        };
        newGame.addActionListener(newGameListener);

        JButton settings = new JButton("Настройки");
        ActionListener settingsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(game.getGameBoard(), game, game.getPlayers());
            }
        };
        settings.addActionListener(settingsListener);

        controlPanel.add(newGame, BorderLayout.WEST);
        controlPanel.add(settings, BorderLayout.CENTER);

        container.add(controlPanel, BorderLayout.NORTH);
        container.add(buttonsPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public int getMapSize() {
        return mapSize;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public ButtonsPane getButtonsPane() {
        return buttonsPane;
    }
}
