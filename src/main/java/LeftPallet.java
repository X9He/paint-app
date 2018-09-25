import javax.swing.*;
import java.awt.*;

public class LeftPallet extends JPanel {
    private int cols;
    private int rows;

    public LeftPallet(){
        super(new GridLayout(2,1));
        this.add(new ColorPallet());
        this.add(new StrokePallet());
    }
}
