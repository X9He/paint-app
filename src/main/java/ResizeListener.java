import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class ResizeListener extends ComponentAdapter {

    public void componentResized(ComponentEvent e) {
        double oldW = Model.getDimension().x;
        double oldH = Model.getDimension().y;
        double width = e.getComponent().getWidth();
        double length = e.getComponent().getHeight();


//        System.out.println(e.getComponent().getWidth());
//        System.out.println(e.getComponent().getHeight());
    }
}