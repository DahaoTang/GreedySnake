package GreedySnake;

public class GameRunner {
    public static void main(String[] args) {
        App game = new App();
        game.settings();
        game.setup();
        while (true) {
            game.draw();
        }
    }
}
