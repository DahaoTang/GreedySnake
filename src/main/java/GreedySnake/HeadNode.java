package GreedySnake;

// // Auto Turn when hitting the wall
// import java.util.Random;

public class HeadNode extends Node {

    // // Auto Turn when hitting the wall
    // private Random random;

    public HeadNode(int x, int y, Direction direction, Node nextNode) {
        super(x, y, nextNode);
        this.direction = Direction.UP;

        // // Auto Turn when hitting the wall
        // this.random = new Random();
    }

    public void tick() {

        // // Auto Turn when hitting the wall
        // if (this.x == 0 && this.y == 0) {
        //     if (this.nextNode.getY() == this.y) {
        //         this.direction = Direction.DOWN;
        //     } else if (this.nextNode.getX() == this.x) {
        //         this.direction = Direction.RIGHT;
        //     }
        // } else if (this.x == App.WIDTH - App.NodeWidth && this.y == 0) {
        //     if (this.nextNode.getY() == this.y) {
        //         this.direction = Direction.DOWN;
        //     } else if (this.nextNode.getX() == this.x) {
        //         this.direction = Direction.LEFT;
        //     }
        // } else if (this.x == 0 && this.y == App.HEIGHT - App.NodeHeight) {
        //     if (this.nextNode.getY() == this.y) {
        //         this.direction = Direction.UP;
        //     } else if (this.nextNode.getX() == this.x) {
        //         this.direction = Direction.RIGHT;
        //     }
        // } else if (this.x ==App.WIDTH - App.NodeWidth && this.y == App.HEIGHT - App.NodeHeight) {
        //     if (this.nextNode.getY() == this.y) {
        //         this.direction = Direction.UP;
        //     } else if (this.nextNode.getX() == this.x) {
        //         this.direction = Direction.LEFT;
        //     }
        // }

        // int chooseDirection = random.nextInt(1);

        // if (this.x == 0 && this.nextNode.getY() == this.y) {
        //     if (chooseDirection == 0) {
        //         this.direction = Direction.UP;
        //     } else {
        //         this.direction = Direction.DOWN;
        //     }
        // }
        // if (this.x == App.WIDTH - App.NodeWidth && this.nextNode.getY() == this.y) {
        //     if (chooseDirection == 0) {
        //         this.direction = Direction.UP;
        //     } else {
        //         this.direction = Direction.DOWN;
        //     }
        // }
        // if (this.y == 0 && this.nextNode.getX() == this.x) {
        //     if (chooseDirection == 0) {
        //         this.direction = Direction.LEFT;
        //     } else {
        //         this.direction = Direction.RIGHT;
        //     }
        // }
        // if (this.y == App.HEIGHT - App.NodeHeight && this.nextNode.getX() == this.x) {
        //     if (chooseDirection == 0) {
        //         this.direction = Direction.LEFT;
        //     } else {
        //         this.direction = Direction.RIGHT;
        //     }
        // }

        this.timer++;
        if (this.timer > SECONDS_BETWEEN_MOVES * App.FPS) {

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
