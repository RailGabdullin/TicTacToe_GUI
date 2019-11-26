package rail.gabdullin;

public class PlayerAI extends Player {

    Game game;

    PlayerAI(char playerSymbol, Game game) {
        super(playerSymbol);
        this.game = game;
    }

    public void makeStep(){
        int buttonNumber;
        do{
            buttonNumber = (int) (Math.random() * (game.getGameBoard().getMapSize() * game.getGameBoard().getMapSize() - 1));
        } while (!game.buttonIsAvailable(buttonNumber));
        game.getGameBoard().getButtonsPane().updateGameBoard('O', buttonNumber);
        game.nextStep();
    }
}
