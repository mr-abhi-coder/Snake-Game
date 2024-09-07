import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    //snake body's location
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    //snake size & food size;
    int tileSize = 20;
    //snake;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    //food
    Tile food;
    // food position
    Random random;
    // GameLoop which constantly draw frames or panel of updated games
    Timer gameLoop;
    int velocityX ;
    int velocityY ;
    boolean gameOver = false;

    // jPanel in window;
    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        // components object of snake
        snakeHead = new Tile(8,8);
        snakeBody = new ArrayList<Tile>();
        // object of food
        food = new Tile(30,15);
        //object random
        random = new Random();
        placeFood();


       gameLoop = new Timer(100, this);
       gameLoop.start();
    }

    //function paint
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        // grid lines for better understanding;
        // for(int i=0; i<boardWidth/tileSize; i++){
        //     //x1, y1, x2, y2;
        //     //vertical lines
        //     g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
        //     //horizontal lines
        //     g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        // }

        //food color;
        g.setColor(Color.yellow);
        //food default location reference to (0,0) axis;
        g.fill3DRect(food.x * 20, food.y * 20, tileSize, tileSize, true);

        //snake color
        g.setColor(Color.white);
        //snake default location reference to (0,0) axis;
        g.fill3DRect(snakeHead.x * 20, snakeHead.y * 20, tileSize, tileSize, true);

        //snakeBody
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //score
        g.setFont(new Font ("Arial", Font.PLAIN, 25));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over : " + String.valueOf(snakeBody.size()),tileSize - 20,tileSize);
        }
        else{
            g.setColor(Color.green);
            g.drawString("Score :" + String.valueOf(snakeBody.size()), tileSize-20, tileSize);
        }
        g.setFont(new Font ("Arial", Font.PLAIN, 25));
    }

    //function for placing food at random positions;
    public void placeFood(){
        food.x = random.nextInt(boardWidth / tileSize); // 1100/20 = 50;
        food.y = random.nextInt(boardHeight / tileSize); // 800/20 = 40;
    }

    // detect collision between food and snake
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    
                //movemovent of snake;
    public void move(){
        //eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //snake body follow snakeHead
        for(int i=snakeBody.size()-1; i>=0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //snakeHead
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //gameOver condition
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collide with snakeHead
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight ){
            gameOver = true;
        }
    }

    // action listener
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    //KeyListener 1
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_UP && velocityY !=1){
        velocityX = 0;
        velocityY = -1;
       }
       else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY !=-1){
        velocityX = 0;
        velocityY = 1;
       }
       else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX !=1){
        velocityX = -1;
        velocityY = 0;
       }
       else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX !=-1){
        velocityX = 1;
        velocityY = 0;
       }

       if(gameOver){
        //restart
        snakeHead = new Tile(8,8);
        velocityX = 0;
        velocityY = 0;
        placeFood();
        snakeBody.clear();
        gameOver = false;
        gameLoop.start();
       }
       
    }

    //keyListener 2 not required
    public void keyTyped(KeyEvent e) {
    }
    //KeyListener 3 not required
    public void keyReleased(KeyEvent e) {
    }
}
