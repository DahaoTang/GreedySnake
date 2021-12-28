package GreedySnake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

public class App extends PApplet {

    public static final int NodeHeight = 20;
    public static final int NodeWidth = 20;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int FPS = 60;

    private int screen;

    private Node headNode;

    private ArrayList<Node> nodeList;

    private Random random;

    private Food food;

    public App() {

        this.screen = 1; // switch between game window and game over window

        nodeList = new ArrayList<Node>();

        this.headNode = new HeadNode(WIDTH / 2, HEIGHT / 2, Direction.UP, null);

        this.nodeList.add(headNode);

        // The snake starts the game with the length 2
        this.nodeList.add(new Node(this.headNode.getX(), this.headNode.getY() + NodeHeight, null));
        this.headNode.setNextNode(nodeList.get(1));

        // Init the first random food
        this.random = new Random();
        this.food = new Food(
            this.random.nextInt(WIDTH / NodeWidth) * NodeWidth + NodeWidth / 2, 
            this.random.nextInt(HEIGHT / NodeHeight) * NodeHeight + NodeHeight / 2
            );
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);
    }

    public void draw() {
        rect(0, 0, 800, 600); // emptry window; prepare for redrawing
        background(32, 32, 32);

        // Game starts
        if (this.screen == 1) {
            // One and only one food shows on screen.
            // Once the food is eaten, a new food appears at a random position.
            if (!this.food.getEaten()) {
                this.food.draw(this);
            } else {
                this.food = new Food(
                    this.random.nextInt(WIDTH / NodeWidth) * NodeWidth + NodeWidth / 2, 
                    this.random.nextInt(HEIGHT / NodeHeight) * NodeHeight + NodeHeight / 2
                    );
                // The snake increases its length by 1 if ate a food.
                addNode();
                // The speed of the snake gets faster as the snake gets longer.
                // There is a max speed.
                if (this.headNode.getSpeed() > 0.02 ) {
                    this.headNode.increaseSpeed();
                }
            }
    
            // Loop through the snake
            for (Node node: this.nodeList) {
                if (node != this.headNode) {
                    // If the snake is dead, switch to game over window.
                    if (this.headNode.checkDead(node)) {
                        this.headNode.changeDirection(null);
                        this.screen++;
                    }
                }
                this.food.checkEaten(node);
                node.tick();
                node.draw(this);
            }
        }

        // Game over window
        else if (this.screen == 2) {
            textSize(60);
            text("Game Over", WIDTH / 2 - 160, HEIGHT / 2);
        }
    }

    public void keyPressed() {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40
        // Space: 32
        if (this.keyCode == 37 || this.keyCode == KeyEvent.VK_A) {
            this.headNode.changeDirection(Direction.LEFT);
        } else if (this.keyCode == 38 || this.keyCode == KeyEvent.VK_W) {
            this.headNode.changeDirection(Direction.UP);
        } else if (this.keyCode == 39 || this.keyCode == KeyEvent.VK_D) {
            this.headNode.changeDirection(Direction.RIGHT);
        } else if (this.keyCode == 40 || this.keyCode == KeyEvent.VK_S) {
            this.headNode.changeDirection(Direction.DOWN);
        } else if (this.keyCode == 32) {
            this.headNode.changeDirection(null); // stop
        } else if (this.keyCode == KeyEvent.VK_R) {
            restart(); // restart at anytime
        }
    }

    private void addNode() {
        Node lastNode = this.nodeList.get(this.nodeList.size() - 1);
        this.nodeList.add(new Node(
            lastNode.getX(), 
            lastNode.getY(), 
            null));
        lastNode.setNextNode(this.nodeList.get(this.nodeList.size() - 1));
    }

    // Reset the game
    private void restart() {
        rect(0, 0, 800, 600);

        this.screen = 1;

        nodeList = new ArrayList<Node>();

        this.headNode = new HeadNode(WIDTH / 2, HEIGHT / 2, Direction.UP, null);

        this.nodeList.add(headNode);
        this.nodeList.add(new Node(this.headNode.getX(), this.headNode.getY() + NodeHeight, null));
        this.headNode.setNextNode(nodeList.get(1));

        this.random = new Random();
        this.food = new Food(
            this.random.nextInt(WIDTH / NodeWidth) * NodeWidth + NodeWidth / 2, 
            this.random.nextInt(HEIGHT / NodeHeight) * NodeHeight + NodeHeight / 2
            );
    }

    public static void main(String[] args) {
        PApplet.main("GreedySnake.App");
    }
}
