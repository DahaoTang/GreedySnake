package GreedySnake;


public class HeadNode extends Node {

    

    public HeadNode(int x, int y, Direction direction, Node nextNode) {
        super(x, y, nextNode);
        this.direction = Direction.UP;
    }

    public void tick() {
        

        this.timer++;
        if (this.timer > SECONDS_BETWEEN_MOVES * App.FPS) {

            // Provent the snake from being hitting to its second node, 
            // and change the direction, and move.
            if (this.direction == Direction.UP) {
                if (!(this.nextNode.getX() == this.x && this.nextNode.getY() == this.y - App.NodeHeight)) {
                    this.y -= App.NodeHeight;
                } else {
                    this.y += App.NodeHeight;
                }
            } else if (this.direction == Direction.RIGHT) {
                if (!(this.nextNode.getX() == this.x + App.NodeWidth && this.nextNode.getY() == this.y)) {
                    this.x += App.NodeHeight;
                } else {
                    this.x -= App.NodeHeight;
                }
            } else if (this.direction == Direction.LEFT) {
                if (!(this.nextNode.getX() == this.x - App.NodeWidth && this.nextNode.getY() == this.y)) {
                    this.x -= App.NodeWidth;
                } else {
                    this.x += App.NodeWidth;
                }
            } else if (this.direction == Direction.DOWN) {
                if (!(this.nextNode.getX() == this.x&& this.nextNode.getY() == this.y + App.NodeHeight)) {
                    this.y += App.NodeWidth;
                } else {
                    this.y -= App.NodeWidth;
                }
            }

            if (this.x != this.xMem || this.y != this.yMem) {
                this.nextNode.setX(this.xMem);
                this.nextNode.setY(this.yMem);
                this.xMem = this.x;
                this.yMem = this.y;
            }
            this.timer = 0;
        }
    }
    
}
