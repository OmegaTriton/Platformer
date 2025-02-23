import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Platform extends JPanel {
    public Platform(int x, int y){
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
        setLocation(x,y);
        int width = 125;
        int height = 10;
        setBounds(x,y,width,height);
        setSize(width,height);
        setPreferredSize(new Dimension(width,height));
    }
}
