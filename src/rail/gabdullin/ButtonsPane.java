package rail.gabdullin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPane extends JPanel {

    private Button [] buttons;
    private Game game;
    private GameBoard gameBoard;

    private char defaultSymbol = '*';

    ButtonsPane(Game game, GameBoard gameBoard){
        this.game = game;
        this.gameBoard = gameBoard;

        setLayout(new GridLayout(gameBoard.getMapSize(), gameBoard.getMapSize()));

        buttons = new Button[gameBoard.getMapSize()*gameBoard.getMapSize()];
        for (int i = 0; i < gameBoard.getMapSize() * gameBoard.getMapSize(); i++) {
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

    /** Этот метод обнуляет поле игры. По факту заменяет все лейблы на кнопках на defaultSymbol.
     * Такая реализация не позволяет изменять настройки размера поля игры.
     * Для того, чтобы это стало возможным надо чтобы из GameBoard выпиливалось старое поле ButtonsPane,
     * а вместо него создавалось и вставало новое поле с ноыми размерами mapSize.
     * Переписать этот метод.
     */
    void initNewGameBoard(){
        for (int i = 0; i < gameBoard.getMapSize() * gameBoard.getMapSize(); i++) {
            buttons[i].setLabel(Character.toString(defaultSymbol));
        }
    }

    void updateGameBoard(char playerSymbol, int buttonNumber){
        getButtonByNumber(buttonNumber).setLabel(Character.toString(playerSymbol));
    }

    public Button getButtonByNumber (int numberOfButton){
        return buttons[numberOfButton];
    }

    public char getDefaultSymbol() {
        return defaultSymbol;
    }

    public char getButtonSymbol(int numberOfButton){
        return buttons[numberOfButton].getLabel().charAt(0);
    }
}
