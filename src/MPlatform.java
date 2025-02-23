import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.lang.*;

public class MPlatform extends Platform {
    private boolean horiz;
    private Point defaultPosition;
    private int amplitude;
    private double time_per_period = 2.0; //seconds
    private int ticks = 0;

    public MPlatform(int x, int y, boolean leftright, int amplitude){
        super(x,y);
        double start = 0;
        double end = 2 * Math.PI;
        int steps = 1000; // Adjust for desired granularity
        double stepSize = (end - start) / steps;
        setSize(125,22);
        setPreferredSize(new Dimension(125,5));
        defaultPosition = new Point(x, y);
        setBounds(x, y, 125, 5);
        horiz = leftright;
        this.amplitude = amplitude;
    }

    public Point getDefaultPosition(){return defaultPosition;}
    public void setDefaultPosition(Point pos){defaultPosition = pos;}

    public void tick(){
        ticks++;
        if (horiz){
            setLocation((int)(defaultPosition.x + Math.cos(ticks/time_per_period/3) * amplitude), defaultPosition.y);
        }
        else {
            setLocation(defaultPosition.x, (int)(defaultPosition.y + Math.sin(ticks/time_per_period/3) * amplitude));
        }
    }
}
