import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int BoardWidth = 1100; //50 columns
        int BoardHeight = 800; // 40 rows
        
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BoardWidth, BoardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SnakeGame snakeGame = new SnakeGame(BoardWidth, BoardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
