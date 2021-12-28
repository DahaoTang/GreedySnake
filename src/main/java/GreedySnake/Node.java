package GreedySnake;

import processing.core.*;

public class Node {

    protected double SECONDS_BETWEEN_MOVES = 0.12;

    protected int x;
    protected int y;
    protected int xMem;
    protected int yMem;

    protected Node nextNode;

    protected Direction direction;

    protected int timer;

    public Node(int x, int y, Node nextNode) {

        this.x = x;
        this.y = y;
        this.xMem = this.x;
        this.yMem = this.y;

        this.nextNode = nextNode;

        this.direction = null;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseSpeed() {
        this.SECONDS_BETWEEN_MOVES -= 0.01;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void changeDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public void tick() {
        if (this.x != this.xMem || this.y != this.yMem) {
            if (this.nextNode != null) {
                this.nextNode.setX(this.xMem);
                this.nextNode.setY(this.yMem);
            }
            this.xMem = this.x;
            this.yMem = this.y;
        }
    }

    public void draw(PApplet app) {
        app.noStroke();
        app.fill(0, 255, 255);
        app.pushMatrix();
        app.beginShape();
        app.rect(this.x, this.y, App.NodeWidth, App.NodeHeight);
        app.endShape();
        app.popMatrix();
    }

    public double getSpeed() {
        return this.SECONDS_BETWEEN_MOVES;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean checkDead(Node node) {
        if (node.getX() == this.x && node.getY() == this.y) {
            return true;
        } else if (
                node.getX() < 0 || 
                node.getX() > App.WIDTH - App.NodeWidth || 
                node.getY() < 0 || 
                node.getY() > App.HEIGHT - App.NodeHeight) {
            return true;
        }
        return false;
    }

}
