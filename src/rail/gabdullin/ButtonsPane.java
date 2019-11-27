package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс игрового поля.
 * Создает и управляет массивом кнопок игрового поля buttons , хранит его состояние, а также сообщает доступность кнопок и
 * текущее состояние (label) кнопки.
 * Хранит ссылки на текущий объект игры game, текущее окно игры gameBoard и размер игрового поля.
 */

public class ButtonsPane extends JPanel {

    private Button [] buttons;
    private Game game;
    private GameBoard gameBoard;
    private int mapSize;

    private char defaultSymbol = '*';

    /**
     * В цикле заполняет массив кнопок новыми кнопками. Каждая кнопка хранит свой порядкоый номер в buttonNumber.
     * Также цикл навешивает на кнопки слушатель, который позволяет реализовать
     * игру за человека. Слушатель проверяет доступность кнопки, и, если она доступна, то обновляет поле передавая в метод
     * updateGameBoard() символ текущего игрока и номер выбранной им кнопки.
     * @param game - ссылка на текущий объект игры Game
     * @param gameBoard - ссылка на текущее игровое окно gameBoard
     * @param mapSize - размер игрового поля
     */
    ButtonsPane(Game game, GameBoard gameBoard, int mapSize){
        this.game = game;
        this.gameBoard = gameBoard;
        this.mapSize = mapSize;

        setLayout(new GridLayout(mapSize, mapSize));

        buttons = new Button[mapSize*mapSize];

        for (int i = 0; i < mapSize * mapSize; i++) {
            buttons[i] = new Button(Character.toString(defaultSymbol));
            add(buttons[i]);
            int finalI = i;
            ActionListener buttonListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int buttonNumber = finalI;
                    if (game.buttonIsAvailable(buttonNumber)) {
                        updateGameBoard(game.getCurrentPlayer().getPlayerSymbol(), buttonNumber);
                        game.nextStep();
                    } else {
                        gameBoard.showMessage("Это клетка недоступна. Выберите другую");
                    }
                }
            };
            buttons[i].addActionListener(buttonListener);
        }
    }

    /**
     * Метод сбрасиывающий игровое поле. В цикле проходит по кнопкам и заменяет Label на знак пустой клетки - defaultSymbol
     */
    void initNewGameBoard(){
        for (int i = 0; i < gameBoard.getMapSize() * gameBoard.getMapSize(); i++) {
            updateGameBoard(defaultSymbol, i);
        }
    }

    /**
     * Метод, устанавливающий переданный ему символ в клетку с переданным номером.
     * Используется для обноления поля игроками PlayerAI и для сброса поля в методе initNewGameBoard() этого класса
     * @param settingSymbol - Символ, который нужно установить в кнопку
     * @param buttonNumber - Номер кнопки, в которую нужно установить символ
     */
    void updateGameBoard(char settingSymbol, int buttonNumber){
        buttons[buttonNumber].setLabel(Character.toString(settingSymbol));
    }

    /**
     * Возвращает defaultSymbol. Используется для проверки ничьи checkTie() в классе Game и проверки выигрыша в checkWin()
     * в классе Game
     * @return символ пустой ячейки
     */
    public char getDefaultSymbol() {
        return defaultSymbol;
    }

    /**
     * Возвращает текст Label, приведенный к типу char, который содердится в ячейке с запрошенным номером. Используется для проверки
     * ничьи checkTie() в классе Game, проверки выигрыша в checkWin() и для проверки доступности кнопки для хода
     * в методе buttonIsAvailable класса Game.
     * @param numberOfButton - номер ячейки, символ который нужно получить
     * @return - возвращает текст кнопки Label, приведенный к типу char
     */
    public char getButtonSymbol(int numberOfButton){
        return buttons[numberOfButton].getLabel().charAt(0);
    }
}
