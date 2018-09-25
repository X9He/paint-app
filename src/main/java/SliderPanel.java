import javax.swing.*;

/*
 * SliderDemo.java requires all the files in the images/doggy
 * directory.
 */
public class SliderPanel extends JPanel{
//    //Set up animation parameters.
//    static final int FPS_MIN = 0;
//    static final int FPS_MAX = 30;
//    static final int FPS_INIT = 15;    //initial frames per second
//    int frameNumber = 0;
//    int NUM_FRAMES = 14;
//    ImageIcon[] images = new ImageIcon[NUM_FRAMES];
//    int delay;
//    Timer timer;
//    boolean frozen = false;
//
//    //This label uses ImageIcon to show the doggy pictures.
//    JLabel picture;
//
//    public SliderPanel() {
//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//
//        delay = 1000 / FPS_INIT;
//
//        //Create the label.
//        JLabel sliderLabel = new JLabel("Frames Per Second", JLabel.CENTER);
//        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        //Create the slider.
//        JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL,
//                FPS_MIN, FPS_MAX, FPS_INIT);
//
//
//        framesPerSecond.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                JSlider source = (JSlider) e.getSource();
//                if (!source.getValueIsAdjusting()) {
//                    int currentPos = (int) source.getValue();
//                    System.out.println(currentPos);
//                    if (currentPos == 0) {
//                    } else {
//                    }
//                }
//            }
//        });
//
//        //Turn on labels at major tick marks.
//
////        framesPerSecond.setMajorTickSpacing(10);
//        framesPerSecond.setMinorTickSpacing(1);
//        framesPerSecond.setPaintTicks(true);
//        framesPerSecond.setPaintLabels(true);
//        framesPerSecond.setBorder(
//                BorderFactory.createEmptyBorder(0, 0, 10, 0));
//        Font font = new Font("Serif", Font.ITALIC, 15);
//        framesPerSecond.setFont(font);
//
//        //Create the label that displays the animation.
//        picture = new JLabel();
//        picture.setHorizontalAlignment(JLabel.CENTER);
//        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
//        picture.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLoweredBevelBorder(),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//
//        //Put everything together.
//        add(sliderLabel);
//        add(framesPerSecond);
//        add(picture);
//        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        //Set up a timer that calls this object's action handler.
//        timer = new Timer(delay, this);
//        timer.setInitialDelay(delay * 7); //We pause animation twice per cycle
//        //by restarting the timer
//        timer.setCoalesce(true);
//    }
//
//
//
//    public void windowOpened(WindowEvent e) {
//    }
//
//    public void windowClosing(WindowEvent e) {
//    }
//
//    public void windowClosed(WindowEvent e) {
//    }
//
//    public void windowActivated(WindowEvent e) {
//    }
//
//    public void windowDeactivated(WindowEvent e) {
//    }
//
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("SliderDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        SliderPanel animator = new SliderPanel();
//
//        //Add content to the window.
//        frame.add(animator, BorderLayout.CENTER);
//
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void mainHX(String[] args) {
//        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//
//
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}
