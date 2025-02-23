import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.awt.Color;

public abstract class Player extends Entity {
    private int speed;
    private double y_velo = 0;
    private boolean can_move = true;
    private boolean jumped = false, reached_ground = false;
    private double jump_time; //seconds
    private double gravity_modifier = 1;
    private long time_of_last_jump;
    private int ticks_since_last_reached_ground = 0;
    private final long jump_delay = 50 * 1000000; // milli seconds -> nano seconds
    private ArrayList<Integer> inputs;
    private ArrayList<Integer> uninputs;

    public Player(JPanel p, JFrame f, String filePath, double scale, double jump_time_len, int speed){
        super(p, f, filePath, scale);
        time_of_last_jump = System.nanoTime() - jump_delay;
        jump_time = jump_time_len;
        this.speed = speed;
        inputs = new ArrayList<Integer>();
        uninputs = new ArrayList<Integer>();
    }

    public void jump() {
        if ((!jumped && System.nanoTime() - time_of_last_jump > jump_delay && reached_ground) || isA().equals("paper")) {
            jumped = true;
            y_velo += gravity_modifier*jump_time/2;
            time_of_last_jump = System.nanoTime() + jump_delay;
        }
    }

    public void setLocation(int x, int y){
        if (y >= getY_bound() - getHeight()){
            reached_ground = true;
            jumped = false;
            ticks_since_last_reached_ground = 0;
            y_velo = 0;
        }
        else
            reached_ground = false;
        super.setLocation(x, y);
    }

    public void tick(){
        int x = getX();
        int y = getY();
        if((jumped || !reached_ground)){
            y_velo -= gravity_modifier*ticks_since_last_reached_ground;
            ticks_since_last_reached_ground++;
        }
        if(can_move){for(Iterator<Integer> i = inputs.iterator(); i.hasNext();){
            int key = i.next();
            if(uninputs.contains(key)){
                uninputs.remove(findRemovedKey(key));
                i.remove();
            }
            else{
                if(key == KeyEvent.VK_S)
                    y_velo -= speed; break;
            }
        }}
        y -= y_velo;
        setLocation(x, y);
    }

    public void input(int key){
        if(!inputs.contains(key)){
            switch(key){
              case KeyEvent.VK_W: jump(); break;
              case KeyEvent.VK_A: inputs.add(key); break;
              case KeyEvent.VK_S: inputs.add(key); break;
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

    public boolean isMovable() {
        return can_move;
    }
    public void setMovable(boolean m) {
        can_move = m;
    }
    public void addY_velo(double amt){
        y_velo += amt;
    }
    public double getY_velo(){
        return y_velo;
    }
    private int findRemovedKey(int key){
        for(int i = 0; i < uninputs.size(); i++)
          if(uninputs.get(i) == key)
            return i;
        return -1;
    }
    public String isA(){
        return "player";
    }
    public boolean getReachedGround(){
        return reached_ground;
    }
}
