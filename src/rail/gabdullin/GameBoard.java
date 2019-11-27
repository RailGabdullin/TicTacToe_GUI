package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс расширяет JFrame библиотеки Swing.
 * Создает главное окно игры, заполняет его элементами, вызывает создание диалогового окна настроек игры и создает
 * окна с уведомлениями (выигрыш, ничья).
 * Хранит параметр размера игрового поля mapSize - количества ячеек на стороне, ссылку на текущий объект игры,
 * управляет созданием и пересозданием, а также хранит и отдает ссылку на игровое поле ButtonsPane.
 */

public class GameBoard extends JFrame {

    private int mapSize;

    private Game game;

    private ButtonsPane buttonsPane;

    private Container container;

    /**
     * Создает окно игры с двумя основными понелями:
     * controlPanel - содержит кнопки "Новая игра" newGame и "Настройки" settings.
     * buttonsPane - содержит кнопки игрового поля.
     * Обе панели добавляются в contentPane через контейнер container.
     * @param game принимает текущий объект игры классы Game.
     */
    GameBoard (Game game) {
        mapSize = 3;
        this.game = game;

        setBounds(400,250, 400,470);
        setTitle("Крестики-Нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Связываем контекнер с contentPane и устанавливаем менеджер размещения BorderLayout
        container = getContentPane();
        container.setLayout(new BorderLayout());

        //Создаем новое игровое поле
        buttonsPane = new ButtonsPane(game, this, mapSize);

        //Создаем панель с кнопками настроек игры
        JPanel controlPanel = new JPanel();

        //Создаем кнопку "Новая игра" и прикрепляем к ней слушатель. Слушатель вызывает метод refreshGame() класса Game
        JButton newGame = new JButton("Новая игра");
        ActionListener newGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.refreshGame();
            }
        };
        newGame.addActionListener(newGameListener);

        //Создаем кнопку "Настройки" и прикрепляем слушатель, который создает новое диалоговое окно настроек игры SettingsDialog
        JButton settings = new JButton("Настройки");
        ActionListener settingsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog(game.getGameBoard(), game, game.getPlayers());
            }
        };
        settings.addActionListener(settingsListener);

        //Добавляем кнопки "Новая игра" и "Настройки" на понель настроек
        controlPanel.add(newGame, BorderLayout.WEST);
        controlPanel.add(settings, BorderLayout.CENTER);

        //Добавляем понель настроек и понель кнопок в контейнер, связанный с contentPane
        container.add(controlPanel, BorderLayout.NORTH);
        container.add(buttonsPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Метод возарзает текущий размер игрового поля. Используется для создания игрового поля ButtonsPane, проверки
     * выигрыша в checkWin() класса Game и генерации хода makeStep() в PlayerAi
     * @return текущий размер - количество клиеток поля в одно стороне.
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Метод создает диалоговое окно для вывода информации об игре: объявления победителя и ничьи.
     * @param message Принимает в качестве параметра String сообщение, которое нужно вывести в окне.
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Возвращет текущее игровое поле buttonPane. Используется для испольщования методов ButtonPane для получения
     * текущего символа в кнопках для проверки выигрыша и др.
     * @return ссылка на текущее игровое поле buttonsPane
     */
    public ButtonsPane getButtonsPane() {
        return buttonsPane;
    }

    /**
     * Метод реализующий изменение размеров игрового поля.
     * Старое игровое поле исключается из container (и, соответсвенно, contentPane), поле текущего объектка класса
     * GameBoard mapSize обновляется, создается новое игровое поле с новым параметром mapSize и добавляется в container.
     * @param newMapSize новой размер игрового поля.
     */
    void resizeMap(int newMapSize){
        container.remove(buttonsPane);
        mapSize = newMapSize;
        buttonsPane = new ButtonsPane(game, this, mapSize);
        container.add(buttonsPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
