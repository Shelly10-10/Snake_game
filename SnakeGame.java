
package snake.game;
import javax.swing.*;
public class SnakeGame extends JFrame{
    SnakeGame(){
        super("Snake Game");
        add(new Board());
        pack();//refresh
        setLocation(700,300);
        setSize(300,300);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
       new SnakeGame();
    }
    
}
