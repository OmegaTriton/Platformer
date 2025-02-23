import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Game implements KeyListener, ActionListener{
  private Player[] players;
  private JFrame frame;
  private int TARGET_FPS = 165;
  private ArrayList<Platform> level1, level2, level3;
  private Player player;
  private boolean running;
  private int x_velo, speed = 20;
  private ArrayList<Integer> inputs;
  private ArrayList<Integer> uninputs;

  private Runnable gameStart = new Runnable(){
    public void run(){
      long optimal_time = 1000000000/TARGET_FPS;
      ArrayList<Platform> currentLevel = level1;
      while(running){
        long now = System.nanoTime();
        player.tick(currentLevel);
        //game tick
        for(Iterator<Integer> i = inputs.iterator(); i.hasNext();){
          int key = i.next();
          if(uninputs.contains(key)){
            uninputs.remove(findRemovedKey(key));
            i.remove();
            x_velo = 0;
          }
          else{
            if(key == KeyEvent.VK_D)
              x_velo = speed;
            else if(key == KeyEvent.VK_A)
              x_velo = -speed;
          }
        }
        for(Platform platform : currentLevel){
          platform.setLocation(platform.getX()-x_velo, platform.getY());
          if (platform instanceof MPlatform mPlatform)
          {
            mPlatform.setDefaultPosition(new Point(mPlatform.getDefaultPosition().x-x_velo, mPlatform.getDefaultPosition().y));
            mPlatform.tick();
          }
        }
        long update_time = System.nanoTime()-now;
        long wait = (optimal_time-update_time)/100000;//nano -> milli
        try{
          Thread.sleep(wait);
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    }
  };

  private int findRemovedKey(int key){
    for(int i = 0; i < uninputs.size(); i++)
      if(uninputs.get(i) == key)
        return i;
    return -1;
  }

  public Game(){
    inputs = new ArrayList<Integer>();
    uninputs = new ArrayList<Integer>();
    initializeGraphics();
    new Thread(gameStart).start();
    running = true;
  }

  private void initializeGraphics(){
    //set up frame
    frame = new JFrame();
    frame.setTitle("Platformer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,400);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setUndecorated(true);
    frame.addKeyListener(this);
    frame.setLayout(null);

    //set up platforms
    int sf = 25;
    level1 = new ArrayList<Platform>();
    level1.add(new MPlatform(50,360, false, 30));
    level1.add(new Platform(200-sf,300));
    level1.add(new Platform(350+3*sf,240));
    level1.add(new Platform(500+5*sf, 180));
    level1.add(new Platform(650+4*sf, 140));
    level1.add(new Platform(800+8*sf, 180));
    level1.add(new Platform(950+7*sf, 240));
    level1.add(new Platform(1100+9*sf, 300));
    level1.add(new Platform(1250+10*sf, 360));

    //Optional
    level1.add(new Platform(275+sf, 270));
    level1.add(new Platform(425+4*sf, 210));
    level1.add(new MPlatform(1075+8*sf, 270, false, 20));


    //add platforms
    for(Platform platform : level1)
      frame.add(platform);

    //set up player
    player = new Player(frame, "src/images/players temp.png", 4, 50, 20);
    player.setLocation(122, 0);
    frame.setVisible(true);
  }

  public void input(int key){
    if(!inputs.contains(key)){
      switch(key){
        case KeyEvent.VK_A: inputs.add(key); break;
        case KeyEvent.VK_D: inputs.add(key); break;
        default: System.out.print("Not WASD"); break;
      }
    }
  }

  public void uninput(int key){
    if(inputs.contains(key) && !uninputs.contains(key)){
      uninputs.add(key);
    }
  }

  public void keyTyped(KeyEvent e){}//unused
  public void keyPressed(KeyEvent e){
    switch(e.getKeyCode()){
      case KeyEvent.VK_W: player.input(KeyEvent.VK_W); break;
      case KeyEvent.VK_A: input(KeyEvent.VK_A); break;
      case KeyEvent.VK_S: player.input(KeyEvent.VK_S); break;
      case KeyEvent.VK_D: input(KeyEvent.VK_D); break;
    }
  }
  public void keyReleased(KeyEvent e){
    switch(e.getKeyCode()){
      case KeyEvent.VK_W: player.uninput(KeyEvent.VK_W); break;
      case KeyEvent.VK_A: uninput(KeyEvent.VK_A); break;
      case KeyEvent.VK_S: player.uninput(KeyEvent.VK_S); break;
      case KeyEvent.VK_D: uninput(KeyEvent.VK_D); break;
    }
  }
  public void actionPerformed(ActionEvent e){

  }
}