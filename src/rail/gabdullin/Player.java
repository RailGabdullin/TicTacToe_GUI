package rail.gabdullin;

public class Player {

    private char playerSymbol;

    String name;

    Player(char playerSymbol){
        this.playerSymbol = playerSymbol;
    }


    public char getPlayerSymbol() {
        return playerSymbol;
    }


    public void makeStep() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
