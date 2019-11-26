package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {

    Game game;
    Player [] players = new Player[2];

    SettingsDialog(GameBoard gameBoard, Game game, Player [] players){
        super(gameBoard, "Настройки игры", true);

        this.game = game;
        this.players[0] = players[0];
        this.players[1] = players[1];


        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(350,250);
        setPreferredSize(new Dimension(300,300));

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel settingsPane = new JPanel();
        settingsPane.setLayout(new FlowLayout());


        //Имя первого игрока
        settingsPane.add(new JLabel("Имя первого игрока"));
        JTextField firstPlayerName = new JTextField("Человек");
        firstPlayerName.setPreferredSize(new Dimension(300, 30));
        settingsPane.add(firstPlayerName);


        //Имя второго игрока
        settingsPane.add(new JLabel("Имя второго игрока"));
        JTextField secondPlayerName = new JTextField("Компьютер");
        secondPlayerName.setPreferredSize(new Dimension(300, 30));
        settingsPane.add(secondPlayerName);

        //Соперник человек / компьютер
        JRadioButton vsPS = new JRadioButton("Соперник - компьютер", true);
        vsPS.setPreferredSize(new Dimension(300, 30));
        settingsPane.add(vsPS);

        //Размер поля
        settingsPane.add(new JLabel("Размер поля"));
        SpinnerModel mapSizeModel = new SpinnerNumberModel(3,0,20,1);
        JSpinner mapSize = new JSpinner(mapSizeModel);
        settingsPane.add(mapSize);

        //Кнопка сохранить
        JButton saveSettings = new JButton("Сохранить настройки");
        ActionListener saveActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                players[0].setName(firstPlayerName.getText());
                players[1].setName(secondPlayerName.getText());

                game.getGameBoard().setMapSize((Integer) mapSize.getValue());

                game.refreshGame();
                dispose();
            }
        };
        saveSettings.addActionListener(saveActionListener);


        container.add(settingsPane, BorderLayout.CENTER);
        container.add(saveSettings, BorderLayout.SOUTH);


        pack();
        setVisible(true);
    }

}
