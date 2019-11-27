package rail.gabdullin;

/**
 * Класс реализует логику игры компьютера и наследует класс Player.
 * Дополнительным полем класса является ссылка на объект игры game, так как генерация хода происходит в зависимости от размера игрового поля.
 */
public class PlayerAI extends Player {

    private Game game;
/**
 * В конструкторе заполняем поля, устанавливаем имя игрока
 */
    PlayerAI(char playerSymbol, Game game) {
        super(playerSymbol);
        setName("Компьютер");
        this.game = game;
    }

    /**
     * Метод реализует логику игры компьютера - случайным образом генерирует номер кнопки пока не найдет кнопку в которую можно сходить
     * (возвращает true game.buttonIsAvailable(buttonNumber)) и инициирует движение игрового цикла.
     */
    public void makeStep(){
        int buttonNumber;
        do{
            buttonNumber = (int) (Math.random() * (game.getGameBoard().getMapSize() * game.getGameBoard().getMapSize() - 1));
        } while (!game.buttonIsAvailable(buttonNumber));
        game.getGameBoard().getButtonsPane().updateGameBoard('O', buttonNumber);
        game.nextStep();
    }
}
