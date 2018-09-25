import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ColorPallet extends JPanel {
    ArrayList<JButton> bList;

    public ColorPallet(){
        super(new GridLayout(4,2));
        this.setSize(20,50);
        bList = new ArrayList<>();
        Dimension d = new Dimension(15,30);
        JButton btBlue = new JButton();
        JButton btRed = new JButton();
        JButton btGreen = new JButton();
        JButton btBlack = new JButton();
        JButton btBrown = new JButton();
        JButton btYellow = new JButton();
        JButton btjCC = new JButton("CUSTOM");
        bList.add(btBlack);
        bList.add(btBlue);
        bList.add(btBrown);
        bList.add(btGreen);
        bList.add(btRed);
        bList.add(btYellow);
        bList.add(btjCC);
        btBlue.setMaximumSize(d);

        btBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activateButton(btBlue);
                Model.setCurrentColor(new Color(0, 0, 255));
            }
        });
        btRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activateButton(btRed);
                Model.setCurrentColor(new Color(255, 0, 0));
            }
        });

        btGreen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentColor(new Color(0, 128, 0));
                activateButton(btGreen);
            }
        });
        btBlack.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentColor(new Color(0, 0, 0));
                activateButton(btBlack);
            }
        });
        btBrown.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentColor(new Color(165, 42, 42));
                activateButton(btBrown);
            }
        });
        btYellow.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setCurrentColor(new Color(255, 255, 0));
                activateButton(btYellow);
            }
        });

        btBlue.setBackground(new Color(0, 0, 255));
        btBlack.setBackground(new Color(0, 0, 0));
        btRed.setBackground(new Color(255, 0, 0));
        btGreen.setBackground(new Color(0, 128, 0));
        btBrown.setBackground(new Color(165, 42, 42));
        btYellow.setBackground(new Color(255, 255, 0));

        this.add(btBlack,0);
        this.add(btRed,1);
        this.add(btBlue,2);
        this.add(btGreen,3);
        this.add(btBrown,4);
        this.add(btYellow,5);
        this.add(btjCC, 6);
        JFrame newFrame = new JFrame("New Window");
//        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //add this line of code
        btjCC.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // open a new frame i.e window
                newFrame.pack();
                newFrame.setVisible(true);
            }
        });
        JColorChooser jCC = new JColorChooser();
        JButton confirm = new JButton("Finished Choosing");
        confirm.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color c = jCC.getColor();
                Model.setCurrentColor(c);
                btjCC.setBackground(c);
            }
        });
        JPanel jccPanel = new JPanel(new FlowLayout());
        JPanel buttonPanels = new JPanel(new GridLayout(7,1));
        buttonPanels.setPreferredSize(new Dimension(170,350));

        newFrame.getContentPane().add(jccPanel);

        jccPanel.add(jCC);
        jccPanel.add(buttonPanels);
        buttonPanels.add(confirm);
    }

    private void clearButtonBorder(){
        for(int i = 0; i < bList.size(); ++i){
            bList.get(i).setBorder(BorderFactory.createEmptyBorder());
        }
    }

    private void activateButton(JButton jb){
        clearButtonBorder();
        jb.setBorder(BorderFactory.createLoweredBevelBorder());
    }
}
