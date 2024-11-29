
package snake.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener{
    
    private Image dot;
    private Image apple;
    private Image head;
    
    private boolean inGame=true;
    
    private int dots;
    private final int dot_size=10;
    private final int all_dots=900;//10 is considered the size of each dot, 90000/10*10(Area of frame/Area of dot)
    private int random_pos=25;//take it anything
    
    private int x[]=new int[all_dots];//we took array since there are multiple dots
    private int y[]=new int[all_dots];//"  "
    
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    
    private Timer timer;
    Board(){
        
        addKeyListener(new TAdapter());
        
        setBackground(Color.black);
        setSize(300,300);
        setFocusable(true);
        
        
        loadImages();
        initGame();
    }
    public void loadImages(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/apple.png"));
        apple=i1.getImage();
        
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/dot.png"));
        dot=i2.getImage();
        
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/head.png"));
        head=i3.getImage();
        
    }
    public void initGame(){
        
        dots=3;
        for(int i=0;i<dots;i++){
           y[i]=50;//initially three dots will be placed horizontally that is their distace from y-distance will be same, thus y has const value
           x[i]=50-i*dot_size;//first dot placed at 50 when i=0, second dot at 40 which means just before that similarly the other one.
           //here dot width is getting subtracted so no two dots overlap each other, 50 is the initial x-distance .
           
        }
        locateApple();
        
        timer=new Timer(140,this);
        timer.start();
        
    }
    public void locateApple(){
        int r=(int)(Math.random()*random_pos);//random gets value btw 0-1,thus multiplied with any random position
        apple_x=r*dot_size;//dist from x-axis
        
       r=(int)(Math.random()*random_pos);
       apple_y=r*dot_size;
        
        
    }
    public void paintComponent(Graphics g){
        //It calls the parent compoment 
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
        g.drawImage(apple, apple_x, apple_y, this);
        
        for(int i=0;i<dots;i++){
            if(i==0){
                g.drawImage(head, x[i], y[i], this);
            }
            else{
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    } 
    public void gameOver(Graphics g){
        String msg="Game Over!";
        Font font=new Font("SAN SERIF",Font.BOLD,16);
        FontMetrics metrices=getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (300-metrices.stringWidth(msg))/2, 300/2);
    }
    public void move(){
        for(int i=dots;i>0;i--){
            //When three consecutive dots(i.e. the length of the snake) is moving forward the next dot is replacing the initial dot
            x[i]=x[i-1];
            y[i]=y[i-1];//since loop is running in reverse
                
        }
        if(leftDirection){
            x[0]=x[0]-dot_size;//x coordinate decreasing
        }
        else if(rightDirection){
            x[0]=x[0]+dot_size;//x coordinate increases
        }
        else if(upDirection){
            y[0]=y[0]-dot_size;//y coordinate decreases
        }
        else if(downDirection){
            y[0]=y[0]+dot_size;//y coordinate increases
        }
    }
    public void checkApple(){
        if((x[0]== apple_x)&&(y[0]== apple_y)){
            dots++;
            locateApple();
        }
        
        
        
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4)&&(x[0]==x[i])&&(y[0]==y[i])){//collision with itself
                inGame=false;
            }
        }
        //collision with wall
        if(y[0]>=300){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(inGame==false){
            timer.stop();
        }
        
    }
    public void actionPerformed(ActionEvent ae){
        if(inGame==true){
        checkApple();
        //collision:if the snake collides with itself or collides with the wall
        checkCollision();
        move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent ke){
            int key=ke.getKeyCode();
            if(key==KeyEvent.VK_LEFT && (!rightDirection)){//at once the snake cannot chnge its direction from right to left, it needs to move first up or down 
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
           
           if(key==KeyEvent.VK_RIGHT&& (!leftDirection)){
               rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
           if(key==KeyEvent.VK_UP && (!downDirection)){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
           if(key==KeyEvent.VK_DOWN && (!upDirection)){
               downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
             
        }
    }
}
