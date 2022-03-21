import enum
import pygame
import random
import sys


# final values
WIDTH = 800
HEIGHT = 600

NODE_WIDTH = 20
NODE_HEIGHT = 20

BACKGROUND_COLOR = pygame.Color(32, 32, 32)
NODE_COLOR= pygame.Color(0, 255, 255)
FOOD_COLOR = pygame.Color(255, 51, 51)


# Helper Objects
class Direction(enum.Enum):
    UP = 1
    LEFT = 2
    DOWN = 3
    RIGHT = 4


# Main Objects
class Node():

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.xMem = self.x
        self.yMem = self.y
        self.nextNode = None
        self.direction = Direction.UP
        self.speed = 15  
        return
    
    def setX(self, x):
        self.x = x
        return

    def setY(self, y):
        self.y = y
        return
    
    def increaseSpeed(self):
        self.speed += 1
        return
    
    def setNextNode(self, nextNode):
        self.nextNode = nextNode
        return

    def changeDirection(self, nextDirection):
        self.direction = nextDirection
        return
    
    def tick(self):
        if (self.x != self.xMem or self.y != self.yMem):
            if (self.nextNode != None):
                self.nextNode.x = self.xMem
                self.nextNode.y = self.yMem
            self.xMem = self.x
            self.yMem = self.y
        return
    
    def draw(self, game_window):
        rect = pygame.Rect(self.x, self.y, NODE_WIDTH, NODE_HEIGHT)
        pygame.draw.rect(game_window, NODE_COLOR, rect)
        return
    
    def getSpeed(self):
        return self.speed

    def getX(self):
        return int(self.x)
    
    def getY(self):
        return int(self.y)
    
    def getDirection(self):
        return self.direction

    def checkDead(self, node):
        if (node.getX() == self.x and node.getY() == self.y):
            return True
        elif (
            node.getX() < 0 or 
            node.getY() < 0 or 
            node.getX() > WIDTH - NODE_WIDTH or 
            node.getY() > HEIGHT - NODE_HEIGHT):
            return True
        return False


class HeadNode(Node):

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.xMem = self.x
        self.yMem = self.y
        self.nextNode = None
        self.direction = Direction.UP
        self.speed = 10  
        self.timer = 0
        return
    
    def tick(self):
        if (self.direction == Direction.UP):
            self.y -= NODE_HEIGHT
        elif (self.direction == Direction.DOWN):
            self.y += NODE_HEIGHT
        elif (self.direction == Direction.LEFT):
            self.x -= NODE_WIDTH
        elif (self.direction == Direction.RIGHT):
            self.x += NODE_WIDTH
        
        if (self.x != self.xMem or self.y != self.yMem):
            if (self.nextNode != None):
                self.nextNode.x = self.xMem
                self.nextNode.y = self.yMem
            self.xMem = self.x
            self.yMem = self.y
        return


class Food():

    def __init__(self, x, y):
        self.x = x
        self.y = y

        if (self.x == 0):
            self.x += NODE_WIDTH
        elif (self.x == WIDTH):
            self.x -= NODE_WIDTH

        if (self.y == 0):
            self.y += NODE_HEIGHT
        elif (self.y == HEIGHT):
            self.y -= NODE_HEIGHT

        self.eaten = False
        return
    
    def draw(self, game_window):
        pygame.draw.circle(game_window, FOOD_COLOR, (self.x, self.y), NODE_WIDTH / 2)
        return

    def getEaten(self):
        return self.eaten
    
    def checkEaten(self, headnode):
        if (headnode.getX() == self.x - NODE_WIDTH / 2 and headnode.getY() == self.y - NODE_HEIGHT / 2):
            self.eaten = True
        return

        
# Game Init
pygame.init()

pygame.display.set_caption("贪吃蛇")
game_window = pygame.display.set_mode((WIDTH, HEIGHT))

fps = pygame.time.Clock()

font = pygame.font.SysFont('arial', 50)
smallFont = pygame.font.SysFont('arial', 10)

nodeList = []
headNode = HeadNode(WIDTH / 2, HEIGHT / 2)
nodeList.append(headNode)

nodeList.append(Node(headNode.getX(), headNode.getY() + NODE_HEIGHT))
headNode.setNextNode(nodeList[1])

food = Food(
    random.randint(1, WIDTH / NODE_WIDTH - 2) * NODE_WIDTH + NODE_WIDTH / 2,
    random.randint(1, HEIGHT / NODE_HEIGHT - 2) * NODE_HEIGHT + NODE_HEIGHT / 2
)


# Helper Functions          
def addNode():
    lastNode = nodeList[len(nodeList) - 1]
    nodeList.append(
        Node(
            lastNode.getX(),
            lastNode.getY()
        )
    )
    lastNode.setNextNode(nodeList[len(nodeList) - 1])
    return


def restart():
    global nodeList, headNode, food

    nodeList = []
    headNode = HeadNode(WIDTH / 2, HEIGHT / 2)
    nodeList.append(headNode)

    nodeList.append(Node(headNode.getX(), headNode.getY() + NODE_HEIGHT))
    headNode.setNextNode(nodeList[1])

    food = Food(
        random.randint(1, WIDTH / NODE_WIDTH - 2) * NODE_WIDTH + NODE_WIDTH / 2,
        random.randint(1, HEIGHT / NODE_HEIGHT - 2) * NODE_HEIGHT + NODE_HEIGHT / 2
    )
    return


def keyPressed():
    for event in pygame.event.get():
        if (event.type == pygame.KEYDOWN):
            if ((event.key == pygame.K_UP or event.key == pygame.K_w) and headNode.getDirection() != Direction.DOWN):
                headNode.changeDirection(Direction.UP)
            elif ((event.key == pygame.K_DOWN or event.key == pygame.K_s) and headNode.getDirection() != Direction.UP):
                headNode.changeDirection(Direction.DOWN)
            elif ((event.key == pygame.K_LEFT or event.key == pygame.K_a) and headNode.getDirection() != Direction.RIGHT):
                headNode.changeDirection(Direction.LEFT)
            elif ((event.key == pygame.K_RIGHT or event.key == pygame.K_d) and headNode.getDirection() != Direction.LEFT):
               headNode.changeDirection(Direction.RIGHT) 
            elif (event.key == pygame.K_SPACE):
               headNode.changeDirection(None)
            elif (event.key == pygame.K_r):
                restart()
            elif (event.key == pygame.K_k):
                pygame.quit()
                sys.exit()
    return


# Game over
def gameOver():
    con = True
    while con:
        game_window.fill(BACKGROUND_COLOR)

        text = font.render("Game Over", True, NODE_COLOR, BACKGROUND_COLOR)
        textRect = text.get_rect()
        textRect.center = (WIDTH // 2, HEIGHT // 2 - 30)
        game_window.blit(text, textRect)

        pygame.display.update()

        for event in pygame.event.get():
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    con = False
                    break
                elif (event.key == pygame.K_k):
                    pygame.quit()
                    sys.exit()
    restart()
    return
        

# Startup
con = True
while con:
    game_window.fill(BACKGROUND_COLOR)

    text = font.render("Welcome to GreedySnake", True, NODE_COLOR, BACKGROUND_COLOR)
    textRect = text.get_rect()
    textRect.center = (WIDTH // 2, HEIGHT // 2 - 30)
    game_window.blit(text, textRect)

    author = smallFont.render("developed by Dahao Tang", True, NODE_COLOR, BACKGROUND_COLOR)
    authorRect = author.get_rect()
    authorRect.bottomright = (WIDTH - 5, HEIGHT - 10)
    game_window.blit(author, authorRect)

    pygame.display.update()

    for event in pygame.event.get():
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                con = False
                break
            elif (event.key == pygame.K_k):
                    pygame.quit()
                    sys.exit()


# Main Function
while True:

    keyPressed()
    
    background_rect = pygame.Rect(0, 0, WIDTH, HEIGHT)
    pygame.draw.rect(game_window, BACKGROUND_COLOR, background_rect)

    if (not food.getEaten()):
        food.draw(game_window)
    else:
        food = Food(
            random.randint(1, WIDTH / NODE_WIDTH - 2) * NODE_WIDTH + NODE_WIDTH / 2,
            random.randint(1, HEIGHT / NODE_HEIGHT - 2) * NODE_HEIGHT + NODE_HEIGHT / 2
        )
        food.draw(game_window)
        addNode()
    
    for node in nodeList:
        if (node != headNode):
            if (headNode.checkDead(node)):
                headNode.changeDirection(None)
                gameOver()
        else:
            food.checkEaten(node)
        node.tick()
        node.draw(game_window)

    pygame.display.update()

    fps.tick(headNode.getSpeed())
