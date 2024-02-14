package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Board extends JPanel implements ActionListener{


   
    int applesEaten = 0;
   
    


    private boolean ingame = true;

    private Timer timer;

    private int apple_x;
    private int apple_y;

   private final int All_dots = 900;
   private final int Dot_size = 10;
   private final int Random_position = 29;

    int [] x = new int [All_dots];
    int [] y = new int[All_dots];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private Image apple;
    private Image dot;
    private Image head;
    int score = 0;

 private int dots;

  public  Board (){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);


        loadimages();
        initGame();
    }

    public void loadimages(){

        ImageIcon h1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/dotimages/apple.png"));
        apple = h1.getImage();
        ImageIcon h2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/dotimages/dot.png"));
        dot = h2.getImage();
        ImageIcon h3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/dotimages/head.png"));
        head = h3.getImage();


    }
    public void initGame (){
        dots = 3;

        for(int i = 0; i<dots; i++){

            y [i]= 50;

            x [i]= 50 - i * Dot_size;
             
            


        }

        locateApple();


        timer = new Timer(140, this);
        timer.start();
    }


   public void restartGame() {
        System.out.println("debug");
        applesEaten = 0;
        dots = 3;
       rightDirection = true;
        repaint();
        initGame();
    }
     

    public void locateApple(){

        int r = (int)(Math.random() * Random_position);
        apple_x = r * Dot_size;

         r = (int)(Math.random() * Random_position);
        apple_y = r*Dot_size;
    }


    public void paintComponent(Graphics g){

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
   if(ingame) {
       g.drawImage(apple, apple_x, apple_y, this);

       for (int i = 0; i < dots; i++) {

           if (i == 0) {

               g.drawImage(head, x[i], y[i], this);
           }
           else
           {

               g.drawImage(dot, x[i], y[i], this);
           }
       }

       Toolkit.getDefaultToolkit().sync();
   }
   else{

       gameOver(g);
   }
    }

    public void gameOver(Graphics g ){

        String msg = "Game Over!";
         
          String sc = "Score = "+Integer.toString(score);
        Font font = new Font("SAN SERIF", Font.BOLD, 14);

        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);

        g.drawString(msg, (300-metrices.stringWidth(msg))/2, 300/2);
        g.drawString(sc, (300-metrices.stringWidth(msg))/2, 300/3);
       
    }

    public void move(){
        for(int i = dots; i>0; i--){

            x[i]= x[i-1];
            y[i]= y[i-1];

        }

        if(leftDirection){

            x[0]=x[0]-Dot_size;
        }
        if(rightDirection){

            x[0]=x[0]+Dot_size;
        }
        if(upDirection){

            y[0]=y[0]-Dot_size;
        }
        if(downDirection){

            y[0]=y[0]+Dot_size;
        }

//        x [0]+=Dot_size;
//        y[0] += Dot_size;
    }

    public void checkApple(){

        if(x[0]==apple_x && y[0]==apple_y){

            dots++;
            score++;
            applesEaten++;

            locateApple();
        }
    }

    public void checkCollision (){
        for(int i = dots; i>0; i--){

            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){

                ingame = false;
               
            }
        }


        if(y[0]>=300){
            ingame = false;
        }

        if(x[0]>=300){
            ingame = false;
        }

        if(y[0]< 0){
            ingame = false;
        }


        if(x[0]< 0){
            ingame = false;
        }

        if(!ingame){

            timer.stop();
        }

    }

    public void actionPerformed(ActionEvent e){
    if(ingame) {
    checkCollision();
    checkApple();
    move();
    }
        repaint();

    }

    public class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection)){

                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){

                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;

            }

        }
    }
}
