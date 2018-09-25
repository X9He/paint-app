import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShapeCanvas extends JPanel implements Observer{
    public static Shape shape;
//    public static ArrayList<Point2d> theList;
    public static boolean isMouseDown;
//    private Point2d curXY;
//    private Timer mouseTimer;

    ShapeCanvas() {
        this.setBackground(new Color(255,255,255));
        isMouseDown = false;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Model.setIsSaved(false);
                Shape tempShape = new Shape(Model.getCurrentThickness(),Model.getCurrentScale());
                shape = tempShape;
                shape.addPoint(e.getX(), e.getY());
                Model.setCurrentShape(tempShape);
                ArrayList<Shape> tempArr = Model.getArrShape();
                for(int x = tempArr.size() - 1; x >= Model.getMaxRenderAmt(); x--)
                {
                    tempArr.remove(x);
                }
                tempArr.add(tempShape);
                tempShape.setColour(Model.getCurrentColor());

                ScrubPanel.getScrollP().setMaximum(Model.getArrShape().size());
                ScrubPanel.getScrollP().setValue(Model.getArrShape().size());
                ScrubPanel.setIsSliderSet(false);
                Model.setMaxRenderAmt(Model.getArrShape().size());

                // try setting scale to something other than 1
                tempShape.setScale(1.0f);
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                isMouseDown = true;
//                curXY = new Point2d(e.getX(), e.getY());
                shape.addPoint(e.getX(), e.getY());
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseDown = false;
            }
        });
    }

//    public void timerStop(){
//        mouseTimer.stop();
//    }

    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (Model.getArrShape().size()!= 0){
            if(Model.getPaintByTime()){
                for(int i = 0; i <= Model.getMaxRenderAmt(); ++i) {
                    Model.getArrShape().get(i).draw(g2);
                }
            } else {
                for(int i = 0; i < Model.getMaxRenderAmt(); ++i) {
                    Model.getArrShape().get(i).draw(g2);
                }
            }
        }
    }

    @Override
    public void update(Object observable) {
        repaint();
    }
}
