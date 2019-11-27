package rail.gabdullin;

/**
 * Основной класс игры, отвечащий за ее ход.
 * Класс создает и хранит массив игроков players, ссылку на текущее игровое поле gameBoard,
 * ссылку на текущего игрока currentPlayer и флаг наличия победителя - gotWinner.
 * Также, предуставлены настройки игры при создании - символы игроков и противник-компьтер.
 *
 * Игровой цикл: запрос проверки выигрыша - запрос проверки ничьи - передача хода, реализован в методе nextStep
 */

public class Game {

    private Player [] players = new Player[2];
    private GameBoard gameBoard;

    private Player currentPlayer;
    private boolean gotWinner;

    private char firstPlayerSymbol = 'X';
    private char secondPlayerSymbol = 'O';
    private boolean vsPC = true;

    /**
     * Конструктор метода наполняет массив игроков players новыми игроками, создает новое игровое gameBoard и запускает
     * окно настроек.
     * Метод dropCoin случайным образом выбират игрока, который будет начинать игру.
     */
    Game (){
        players[0] = new Player(firstPlayerSymbol);
        players[1] = new PlayerAI(secondPlayerSymbol, this);
        gameBoard = new GameBoard(this);
        dropCoin();
        new SettingsDialog(getGameBoard(), this, getPlayers());
        nextStep();
    }

    /**
     * Метод nexStep реализует основной цикл игры: запрос проверки выигрыша - запрос проверки ничьи - передача хода, а также
     * инициирует ход компьютера, если текущий - компьютер.
     */
    void nextStep(){
        if (checkWin()){
            gameBoard.showMessage("Поздравляем, " + getCurrentPlayer().getName() + "! Победили " + getCurrentPlayer().getPlayerSymbol());
        } else if (checkTie()) {
            gameBoard.showMessage("Ничья!");
        } else {
            changeCurrentPlayer();
            if (getCurrentPlayer() == players [1]){
                players[1].makeStep();
            }
        }
    }

    /**
     * Метод проверяет наличие выигрыша. Для этого проходится циклом по одномерному массиву кнопок, размер которого
     * расчитывается как количество ячеек по одной стороне в квадрате минус один. Например, для размерности поля 3х3 количество
     * кнопок будет равно 3х3 = 9, а число элементов в массиве 3х3-1, так как нумерация начинается с 0 элемента.
     *
     * Для каждого варианта выигрыша - вертикалей, горизонталей и диагоналей сущестуют счетчики.
     * Во время прохождения цикла по массиву кнопок он запрашивает текущий символ (Label), который записан в кнопке с помощью метода
     * getButtonSymbol() класса gameBoard. Если символ в следующей клетке равен символу в текущей, то счетчик прибавляет +1.
     * Если счетчик достигат значения количеста клеток mapSize - 1, значит все клетки в данной линии были сравнены друг с другом и во всех находится
     * один символ. В этом случае фиксирукется победа установкой флага gotWinner в положение true, что дает сигнал методу nextStep()
     * объявить текущего игрока currentPlayer победителем.
     * Если цикл достигает конца проверяемой линии (вертикали или горизонтали), то счетчик обнуляется, чтобы его можно было использовать для
     * проверки следующей линии.
     *
     * @return флаг наличия победитея gotWinner, если победитель найдет - true.
     */

    private Boolean checkWin(){

        int numberOfButtons = getGameBoard().getMapSize() * getGameBoard().getMapSize();
        int mapSize = getGameBoard().getMapSize();

        int countOfSameSymbolsHorizontal = 0;
        int countOfSameSymbolsVertical;
        int countOfSameSymbolsDiagonal1 = 0;
        int countOfSameSymbolsDiagonal2 = 0;
        char defaultSymbol = gameBoard.getButtonsPane().getDefaultSymbol();

        //Проверяем
        //Горизонтали
        for (int i = 0; i < numberOfButtons - 1; i++) {
            if(i % mapSize == 0){
                countOfSameSymbolsHorizontal = 0;
            }
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defaultSymbol && (i + 1)%mapSize != 0) {
                countOfSameSymbolsHorizontal++;
                if (countOfSameSymbolsHorizontal == mapSize - 1) {
                    gotWinner = true;
                    break;
                }
            }

        }

        //Вертикали
        for (int i = 0; i < mapSize; i++) {
            countOfSameSymbolsVertical = 0;
            for (int j = i; j < numberOfButtons - mapSize; j = j + mapSize) {

                if (gameBoard.getButtonsPane().getButtonSymbol(j) == gameBoard.getButtonsPane().getButtonSymbol(j + mapSize) && gameBoard.getButtonsPane().getButtonSymbol(j) != defaultSymbol){
                    countOfSameSymbolsVertical++;
                    if (countOfSameSymbolsVertical == mapSize - 1) {
                        gotWinner = true;
                        break;
                    }
                }
            }
        }

        //Прямая диагональ
        for (int i = 0; i < numberOfButtons - 1; i = i + mapSize + 1) {
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + mapSize + 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defaultSymbol){
                countOfSameSymbolsDiagonal1++;
                if (countOfSameSymbolsDiagonal1 == mapSize - 1) {
                    gotWinner = true;
                    break;
                }
            }
        }

        //Обратная диагональ
        for (int i = mapSize - 1; i < numberOfButtons - mapSize; i = i + mapSize - 1) {
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + mapSize - 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defaultSymbol){
                countOfSameSymbolsDiagonal2++;
                if (countOfSameSymbolsDiagonal2 == mapSize - 1) {
                    gotWinner = true;
                    break;
                }
            }
        }
        return gotWinner;
    }

    /**
     * Метод определяет наличие ничьи.
     * Метод проходится циклом по всему массиву кнопок и, если находит свободную кнопку - кнопку с Label == смиволу для
     * пустых кнопок defaultSymbol, то возвращает false. Если таких кнопок не найдется, значит на поле нет пустых кнопок,
     * и по окончанию цикла метод вернет true.
     * @return наличие пустых кнопок на поле.
     */

    private Boolean checkTie(){
        boolean result = true;
        for (int i = 0; i < getGameBoard().getMapSize() * getGameBoard().getMapSize(); i++) {
            if (buttonIsAvailable(i)){
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Метод меняет текущего игрока на другого.
     */
    private void changeCurrentPlayer(){
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    /**
     * Метод "подбрасывает монетку" для определния случайным образом игрока, который будет ходить первым.
     * Для этого он генерирует два случайных числа, и его выбор зависит от того какое из них окажется больше.
     */
    private void dropCoin() {
        if (Math.random() > Math.random()){
            currentPlayer = players [0];
        } else {
            currentPlayer = players [1];
        }
    }

    /**
     * @return Возвращает ссылку на текущего игрока.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Метод проверяет доступность кнопки для хода.
     * Проверяет входил ли кнопка в диапазон массива кнопок, а заетм проверяет соответсвует ли ее label символу пустой кнопки defaultSymbol
     * @param buttonNumber - номер кнопки в массиве кнопок
     * @return - если кнопка доступна для хода - возвращет true
     */

    public boolean buttonIsAvailable(int buttonNumber) {
        return buttonNumber < gameBoard.getMapSize()* gameBoard.getMapSize() && buttonNumber >= 0 && gameBoard.getButtonsPane().getButtonSymbol(buttonNumber) == gameBoard.getButtonsPane().getDefaultSymbol();
    }

    /**
     * Геттер для игрового поля
     * @return возвращет ссылку на текущее игровое поле
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Сбрасывает игру к начальным настройкам. Применяется для начала новой игры.
     * Сначала символы Label всех кнопок заменяются на символы пустых кнопок defaultSymbol с помощью метода initNewGameBoard()
     * класса ButtonPane, ссылку на который мы получаем через текущий gameBoard.
     * Затем с помощью метода dropCoin() выбирается новый текущий игрок, флаг gotWinner сбрасывается и инициируется
     * игровой цикл nextStep().
     */
    public void refreshGame() {
        gameBoard.getButtonsPane().initNewGameBoard();
        dropCoin();
        gotWinner = false;
        nextStep();
    }

    /**
     * Геттер для массива игроков. Используется для настроек игроков в окне настроек игры SettingsDialog
     * @return возвращает массив игроков
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Метод позволяет менять игроков на человека/компьтера. Используется для настроек игроков в окне настроек игры SettingsDialog
     * @param numberOfPlayer - номер игрока в массиве игроков (либо 0 либо 1)
     * @param isPC - true - поставит игроком компьтер, false - человека.
     */
    public void setPlayer(int numberOfPlayer, boolean isPC) {
        if(isPC){
            players[numberOfPlayer] = new PlayerAI(secondPlayerSymbol, this);
        } else {
            players[numberOfPlayer] = new Player(secondPlayerSymbol);
            vsPC = false;
        }
    }

    /**
     * Геттер для флага игры против компьтера. Используется для хранения текущих настроек игроков в окне настроек игры SettingsDialog
     * @return true - игра против компьтера, false - игра против человека.
     */
    public boolean isVsPC() {
        return vsPC;
    }
}
