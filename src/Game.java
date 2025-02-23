import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener, ActionListener{
  private Player[] players;
  private JFrame frame;
  private int TARGET_FPS = 60;
  private JPanel panel;

  private Runnable gameStart = new Runnable(){
    public void run(){
      long optimal_time = 1000000000/TARGET_FPS;
      int i = 0;
      while(i<100){
        long now = System.nanoTime();


        long update_time = System.nanoTime()-now;
        long wait = (optimal_time-update_time)/100000;//nano -> milli
        i+=1;
        try{
          Thread.sleep(wait);
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }
  };

  public Game(){
    initializeGraphics();
  }

  private void initializeGraphics(){
    frame = new JFrame();
    frame.setTitle("Platformer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,400);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setUndecorated(true);
    frame.addKeyListener(this);
    frame.setLayout(null);

    panel = new Platform(100,300);

    frame.add(panel);
    frame.setVisible(true);
  }


  public void keyTyped(KeyEvent e){}//unused
  public void keyPressed(KeyEvent e){
    switch(e.getKeyCode()){
      case KeyEvent.VK_W: players[0].input(KeyEvent.VK_W); break;
      case KeyEvent.VK_A: players[0].input(KeyEvent.VK_A); break;
      case KeyEvent.VK_S: players[0].input(KeyEvent.VK_S); break;
      case KeyEvent.VK_D: players[0].input(KeyEvent.VK_D); break;
    }
  }
  public void keyReleased(KeyEvent e){
    switch(e.getKeyCode()){
      case KeyEvent.VK_W: players[0].uninput(KeyEvent.VK_W); break;
      case KeyEvent.VK_A: players[0].uninput(KeyEvent.VK_A); break;
      case KeyEvent.VK_S: players[0].uninput(KeyEvent.VK_S); break;
      case KeyEvent.VK_D: players[0].uninput(KeyEvent.VK_D); break;
    }
  }
  public void actionPerformed(ActionEvent e){

  }
}