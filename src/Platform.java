import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Platform extends JPanel {
    public Platform(int x, int y){
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK));
        setLocation(x,y);
        setBounds(x,y,125,22);
        setSize(125,22);
        setPreferredSize(new Dimension(125,22));
    }
}
