import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StrokePallet extends JPanel {
    public StrokePallet(){
        super(new GridLayout(3,2));
        JButton btUT = new JButton("Ultra Thick");
        btUT.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentThickness(6.0f);
            }
        });
        this.add(btUT,0);

        JButton btT = new JButton("Thick");
        btT.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentThickness(3.0f);
            }
        });
        this.add(btT,1);

        JButton btM = new JButton("Normal");
        btM.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentThickness(2.0f);
            }
        });
        this.add(btM,2);

        JButton btThin = new JButton("Thin");
        btThin.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentThickness(1.0f);
            }
        });
        this.add(btThin,3);


        JButton btUThin = new JButton("Ultra Thin");
        btUThin.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentThickness(0.5f);
            }
        });
        this.add(btUThin,4);
//        btBlue.

    }
}
