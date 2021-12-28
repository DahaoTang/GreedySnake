package GreedySnake;

import processing.core.*;

public class Food extends PApplet {

    private int x;
    private int y;

    private boolean eaten;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;

        if (this.x == 0) {
            this.x += App.NodeHeight;
        } else if (this.x == App.WIDTH) {
            this.x -= App.NodeHeight;
        }

        if (this.y == 0) {
            this.y += App.NodeWidth;
        } else if (this.x == App.WIDTH) {
            this.x -= App.NodeWidth;
        }
        
        this.eaten = false;
    }

    public void draw(PApplet app) {
        app.noStroke();
        app.fill(255, 51, 51);
        app.pushMatrix();
        app.beginShape();
        app.ellipse(this.x, this.y, App.NodeWidth, App.NodeHeight);
        app.endShape();
        app.popMatrix();
    }

    public boolean getEaten() {
        return this.eaten;
    }

    public void checkEaten(Node node) {
        if (node.getX() == this.x - App.NodeWidth / 2  && node.getY() == this.y - App.NodeHeight / 2 ) {
            this.eaten = true;
        }
        return;    
    }
    
}
