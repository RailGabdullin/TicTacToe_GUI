package rail.gabdullin;

public class Game {

    private GameBoard gameBoard;

    private Player [] players = new Player[2];

    private Player currentPlayer;

    private boolean gotWinner;
    Player winner;
    Game (){
        players[0] = new Player('X');
        players[1] = new PlayerAI('O', this);
        gameBoard = new GameBoard(this);
        refreshGame();
    }

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

    Boolean checkWin(){

        int numberOfButtons = getGameBoard().getMapSize() * getGameBoard().getMapSize();
        int mapSize = getGameBoard().getMapSize();

        int countOfSameSymbolsHorizontal = 0;
        int countOfSameSymbolsVertical;
        int countOfSameSymbolsDiagonal1 = 0;
        int countOfSameSymbolsDiagonal2 = 0;
        char defoultSymbol = gameBoard.getButtonsPane().getDefaultSymbol();

        //Проверяем
        //Горизонтали
        for (int i = 0; i < numberOfButtons - 1; i++) {
            if(i % mapSize == 0){
                countOfSameSymbolsHorizontal = 0;
            }
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defoultSymbol && (i + 1)%mapSize != 0) {
                countOfSameSymbolsHorizontal++;
                if (countOfSameSymbolsHorizontal == mapSize - 1) {
                    gotWinner = true;
                    winner = getCurrentPlayer();
                    break;
                }
            }

        }

        //Вертикали
        for (int i = 0; i < mapSize; i++) {
            countOfSameSymbolsVertical = 0;
            for (int j = i; j < numberOfButtons - mapSize; j = j + mapSize) {

                if (gameBoard.getButtonsPane().getButtonSymbol(j) == gameBoard.getButtonsPane().getButtonSymbol(j + mapSize) && gameBoard.getButtonsPane().getButtonSymbol(j) != defoultSymbol){
                    countOfSameSymbolsVertical++;
                    if (countOfSameSymbolsVertical == mapSize - 1) {
                        gotWinner = true;
                        winner = getCurrentPlayer();
                        break;
                    }
                }
            }
        }

        //Прямая диагональ
        for (int i = 0; i < numberOfButtons - 1; i = i + mapSize + 1) {
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + mapSize + 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defoultSymbol){
                countOfSameSymbolsDiagonal1++;
                if (countOfSameSymbolsDiagonal1 == mapSize - 1) {
                    gotWinner = true;
                    winner = getCurrentPlayer();
                    break;
                }
            }
        }

        //Обратная диагональ
        for (int i = mapSize - 1; i < numberOfButtons - mapSize; i = i + mapSize - 1) {
            if (gameBoard.getButtonsPane().getButtonSymbol(i) == gameBoard.getButtonsPane().getButtonSymbol(i + mapSize - 1) && gameBoard.getButtonsPane().getButtonSymbol(i) != defoultSymbol){
                countOfSameSymbolsDiagonal2++;
                if (countOfSameSymbolsDiagonal2 == mapSize - 1) {
                    gotWinner = true;
                    winner = getCurrentPlayer();
                    break;
                }
            }
        }
        return gotWinner;
    }

    Boolean checkTie(){
        boolean result = true;
        for (int i = 0; i < getGameBoard().getMapSize() * getGameBoard().getMapSize(); i++) {
            if (buttonIsAvailable(i)){
                result = false;
            }
        }
        return result;
    }

    void changeCurrentPlayer (){
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    private void dropCoin() {
        if (Math.random() > Math.random()){
            currentPlayer = players [0];
        } else {
            currentPlayer = players [1];
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean buttonIsAvailable(int buttonNumber) {
        return buttonNumber < gameBoard.getMapSize()* gameBoard.getMapSize() && buttonNumber >= 0 && gameBoard.getButtonsPane().getButtonSymbol(buttonNumber) == gameBoard.getButtonsPane().getDefaultSymbol();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void refreshGame() {
        gameBoard.getButtonsPane().initNewGameBoard();
        dropCoin();
        gotWinner = false;
        winner = null;
        nextStep();
    }

    public Player[] getPlayers() {
        return players;
    }
}
