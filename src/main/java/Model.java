import javax.vecmath.Point2d;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private static ArrayList<Shape> arrShape;
    private ArrayList<Color> arrColor;
    private static Color currentColor;
    private static Shape currentShape;
    private static float currentThickness = 1.0f;
    private static float currentScale;
    private static int maxRenderAmt;
    private static int curRenderAmt;
    private static Point2d curDimension;
    private static long maxRenderTime;
    private static boolean paintByTime;
    private static boolean isSaved;

    public static boolean isIsSaved() {
        return isSaved;
    }

    public static void setIsSaved(boolean isSaved) {
        Model.isSaved = isSaved;
    }

    public static boolean getPaintByTime() {
        return paintByTime;
    }

    public static void setSpecialPaint(boolean paintByTime) {
        Model.paintByTime = paintByTime;
    }

    public static long getMaxRenderTime() {
        return maxRenderTime;
    }

    public static void setMaxRenderTime(long maxRenderTime) {
        Model.maxRenderTime = maxRenderTime;
    }

    public static void setDimension(Point2d newDimension) {
        curDimension = newDimension;
    }

    public static Point2d getDimension() {
        return curDimension;
    }

    public static void setCurRenderAmt(int curRenderAmt) {
        Model.curRenderAmt = curRenderAmt;
    }

    public static int getCurRenderAmt() {
        return curRenderAmt;
    }


    public static int getMaxRenderAmt() {
        return maxRenderAmt;
    }

    public static void setMaxRenderAmt(int maxRenderAmt) {
        Model.maxRenderAmt = maxRenderAmt;
    }

    public static float getCurrentScale() {
        return currentScale;
    }


    public static float getCurrentThickness() {
        return currentThickness;
    }

    public static void setCurrentThickness(float currentThickness) {
        Model.currentThickness = currentThickness;
    }


    public static ArrayList<Shape> getArrShape() {
        return arrShape;
    }

    public static void setArrShape(ArrayList<Shape> arrShape) {
        Model.arrShape = arrShape;
    }

    public static Shape getCurrentShape() {
        return currentShape;
    }

    public static void setCurrentShape(Shape currentShape) {
        Model.currentShape = currentShape;
    }


    public static void setCurrentColor(Color c) {
        Model.currentColor = c;
    }

    public static Color getCurrentColor() {
        return currentColor;
    }


    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
        this.arrShape = new ArrayList();
        this.currentColor = new Color(0,0,0);
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }
}
