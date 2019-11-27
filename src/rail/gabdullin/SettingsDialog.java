package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


/**
 * Диалоговое окно настроек игры. Позволяет установить имена игроков, режим игры против человека / компьтера и размер
 * игрового поля.
 * Хранит ссылки на текущий объект игры Game и ссылку на текущий массив игроков players.
 */

class SettingsDialog extends JDialog {

    private Game game;
    private Player [] players = new Player[2];

    /**
     * Создает диалоговое окно "Настройки игры", добавляет текстовые поля для ввода имен игроков, переключатель для
     * установки режимов игры против человека / компьютера, спинер для управления размером поля. Также, доавбляет кнопку
     * "Сохранить настройки", котрая применят введенные настройки и сбрасывает текущее поле игры.
     * @param gameBoard - ссылка на текущее окно игры GameBoard
     * @param game - ссылка на текущую игру Game
     * @param players - ссылка на массив игроков Player
     */
    SettingsDialog(GameBoard gameBoard, Game game, Player [] players){
        super(gameBoard, "Настройки игры", true);

        //Заполняем поля класса
        this.game = game;
        this.players[0] = players[0];
        this.players[1] = players[1];

        //Устанавливаем настройки окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(350,250);
        setPreferredSize(new Dimension(300,300));

        //Создаем контейнер и связываем его с contentPane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        //Создаем понель настроек
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

        //Соперник человек / компьютер.
        //Получает текущее состояние игры из game с помощью метода isVsPC(), чтобы при переключении параметров и повторном
        //открытии окна настройки сохранялись.
        JRadioButton vsPC = new JRadioButton();
        if(game.isVsPC()){
            vsPC.setText("Соперник - компьютер");
            vsPC.setSelected(true);
            vsPC.setPreferredSize(new Dimension(300, 30));
            vsPC.addItemListener(new ItemListener() {
                                    @Override
                                    public void itemStateChanged(ItemEvent e) {
                                        game.setPlayer(1,false);
                                        //В зависимости от переключения кнопки меняет выводимый рядом с флагом текст про соперника
                                        String text = (vsPC.isSelected()) ? "Соперник - компьютер" : "Соперник - человек";
                                        vsPC.setText(text);
                                    }
        });} else {
            vsPC.setText("Соперник - человек");
            vsPC.setSelected(false);
            vsPC.setPreferredSize(new Dimension(300, 30));
            vsPC.addItemListener(new ItemListener() {
                                     @Override
                                     public void itemStateChanged(ItemEvent e) {
                                         game.setPlayer(1,true);
                                         //В зависимости от переключения кнопки меняет выводимый рядом с флагом текст про соперника
                                         String text = (vsPC.isSelected()) ? "Соперник - компьютер" : "Соперник - человек";
                                         vsPC.setText(text);
                                     }
        });}
        settingsPane.add(vsPC);

        //Размер поля
        settingsPane.add(new JLabel("Размер поля"));
        SpinnerModel mapSizeModel = new SpinnerNumberModel(3,1,20,1);
        JSpinner mapSize = new JSpinner(mapSizeModel);
        settingsPane.add(mapSize);

        //Кнопка сохранить
        JButton saveSettings = new JButton("Сохранить настройки");
        ActionListener saveActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                players[0].setName(firstPlayerName.getText());
                players[1].setName(secondPlayerName.getText());

                gameBoard.resizeMap((Integer) mapSize.getValue());

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
