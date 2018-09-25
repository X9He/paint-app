import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScrubPanel extends JPanel {
    public JPanel getCanvas() {
        return canvas;
    }

    private JPanel canvas;
    private static JButton btStart;
    private static JButton btPlay;
    private static JButton btend;
    private static JButton bPlayBackward;
    private static boolean isSliderSet;
    private int lastTime;
    private Timer sliderTimer;
    private Timer sliderReverseTimer;
    private int curSIndex;
    private long timeElapsed;
    private long lastStamp;
    private int count;

    public static void setIsSliderSet(boolean isSliderSet) {
        ScrubPanel.isSliderSet = isSliderSet;
    }

    public static boolean isIsSliderSet() {
        return isSliderSet;
    }


    public static JSlider getScrollP() {
        return scrollP;
    }

    private static JSlider scrollP;

    public ScrubPanel(JPanel shapeCanvas){
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(1280, 75));
        this.setMinimumSize(new Dimension(320,75));



//        sliderReverseTimer = new Timer(30, new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
////                timeElapsed += System.currentTimeMillis() - lastStamp;
////                lastStamp = System.currentTimeMillis();
//                count += 1;
//                int max = Model.getMaxRenderAmt();
////                System.out.println("current count is " + count + " mode get arr shape size is " + Model.getArrShape().size() + " max is " + max) ;
//                System.out.println("count is " + count + " todraw length is  " + Model.getArrShape().get(max).toDraw.size() + " points is " + Model.getArrShape().get(max).points.size()) ;
//                if (count > 33){
//                    count = 0;
//                    System.out.println("timeElpased inside >1000 condition ");
//                    if (Model.getMaxRenderAmt() < 0) {
//                        Model.setSpecialPaint(false);
//                        Model.setMaxRenderAmt(0);
//                        scrollP.setValue(0);
//                        sliderTimer.stop();
//                    } else {
//                        Model.setMaxRenderAmt(Model.getMaxRenderAmt() - 1);
//                        scrollP.setValue(Model.getMaxRenderAmt());
//                    }
//                }
//                else {
//                    Model.getArrShape().get(Model.getMaxRenderAmt()).updatePointsRev(count);
////                    System.out.println("count is " + count);
////                    canvas.repaint();
//                }
//                canvas.repaint();
//            }
//        });
        isSliderSet = false;
        canvas = shapeCanvas;
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel1 = new JPanel();
        Border eBorder = BorderFactory.createEtchedBorder();


        sliderTimer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
//                timeElapsed += System.currentTimeMillis() - lastStamp;
//                lastStamp = System.currentTimeMillis();
                count += 1;
                int max = Model.getMaxRenderAmt();
//                System.out.println("current count is " + count + " mode get arr shape size is " + Model.getArrShape().size() + " max is " + max) ;
//                System.out.println("count is " + count + " todraw length is  " + Model.getArrShape().get(max).toDraw.size() + " points is " + Model.getArrShape().get(max).points.size()) ;
                if (count > 33){
                    count = 0;
//                    System.out.println("timeElpased inside >1000 condition ");
                    if (Model.getMaxRenderAmt() == Model.getArrShape().size() - 1) {
                        Model.setSpecialPaint(false);
                        Model.setMaxRenderAmt(Model.getMaxRenderAmt() + 1);
                        scrollP.setValue(Model.getMaxRenderAmt());
                        sliderTimer.stop();
                    } else {
                        Model.setMaxRenderAmt(Model.getMaxRenderAmt() + 1);
                        scrollP.setValue(Model.getMaxRenderAmt());
                    }
                }
                 else {
                    Model.getArrShape().get(Model.getMaxRenderAmt()).updatePoints(count);
//                    System.out.println("count is " + count);
//                    canvas.repaint();
                }
                canvas.repaint();
            }
        });

        // creating buttons and slider
        btStart = new JButton();

        btPlay = new JButton();
        btend = new JButton();
        bPlayBackward = new JButton();

        scrollP = new JSlider(JSlider.HORIZONTAL, 0, 1, 1);

        // configure slider
        scrollP.setPreferredSize(new Dimension(500,50));
        scrollP.setMinorTickSpacing(1);
        scrollP.setPaintTicks(true);
        scrollP.setPaintLabels(true);
        scrollP.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(!isSliderSet){
                    isSliderSet = true;
                    return;
                }
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int curVal = (int) source.getValue();
                    Model.setMaxRenderAmt(curVal);
                } else {
                    double curVal = source.getValue();
                    double valFloor = Math.floor(curVal);
                    int perCent = (int)((curVal - valFloor) * 100);
//                    System.out.println("curVal is " + curVal + ", valFloor is " + valFloor + ", percent is " + perCent);
                }
                canvas.repaint();
            }
        });

        this.add(btPlay);
//        this.add(bPlayBackward);
        this.add(scrollP);
        this.add(btStart);
        this.add(btend);

        // adding listeners to buttons
        btend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scrollP.setValue(Model.getArrShape().size());
                Model.setMaxRenderAmt(Model.getArrShape().size());
                canvas.repaint();
            }
        });

        btStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scrollP.setValue(0);
                Model.setMaxRenderAmt(0);
                canvas.repaint();
            }
        });

        btPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.setSpecialPaint(true);
                count = 0;
                for(int i = Model.getMaxRenderAmt(); i < Model.getArrShape().size(); ++i){
                    Model.getArrShape().get(i).prepareDraw();
                }
                sliderTimer.start();
            }
        });

        ImageIcon img = new ImageIcon("resources/play.png");
        btPlay.setIcon(img);
        btPlay.setBackground(Color.white);
        btPlay.setPreferredSize(new Dimension(50,50));
        ImageIcon img2 = new ImageIcon("resources/backward.png");
        btStart.setIcon(img2);
        btStart.setPreferredSize(new Dimension(50,50));
        btStart.setBackground(Color.white);
        ImageIcon img3 = new ImageIcon("resources/forward.png");
        btend.setIcon(img3);
        btend.setPreferredSize(new Dimension(50,50));
        btend.setBackground(Color.white);


//        bPlayBackward.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Model.setSpecialPaint(true);
//                count = 0;
//                for(int i = Model.getMaxRenderAmt(); i < Model.getArrShape().size(); ++i){
//                    Model.getArrShape().get(i).prepareDrawRev();
//                }
//                Model.setCurRenderAmt(Model.getMaxRenderAmt() - 1);
//                sliderReverseTimer.start();
//                for(int i = Model.getMaxRenderAmt(); i < Model.getArrShape().size(); ++i){
//                    Model.getArrShape().get(i).afterDrawRev();
//                }
//            }
//        });

    }
}

