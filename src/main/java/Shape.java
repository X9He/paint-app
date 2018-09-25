/*
 *  Shape: See ShapeDemo for an example how to use this class.
 *
 */
import javax.vecmath.Point2d;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Stack;

// simple shape model class
class Shape implements Serializable {

    // shape points
    Stack<Point2d> points;
    Stack<Point2d> toDraw;
    // shape is polyline or polygon
    Boolean isClosed = false;
    // if polygon is filled or not
    Boolean isFilled = false;
    // drawing attributes
    Color colour = Color.BLACK;
    float strokeThickness;
    // shape's transform
    float scale;
//    long drawTill;
    int lastP;

    public Shape(float thick, float scale){
        this.strokeThickness = thick;
        this.scale = scale;
    }

    public void clearPoints() {
        points = new Stack<Point2d>();
        pointsChanged = true;
    }

    // add a point to end of shape
    public void addPoint(Point2d p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }

    // add a point to end of shape
    public void addPoint(double x, double y) {
        if (points == null) clearPoints();
        addPoint(new Point2d(x, y));
    }

    public int npoints() {
        return points.size();
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public float getStrokeThickness() {
        return strokeThickness;
    }

    public void setStrokeThickness(float strokeThickness) {
        this.strokeThickness = strokeThickness;
    }

    public float getScale(){
        return scale;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    // some optimization to cache points for drawing
    Boolean pointsChanged = false; // dirty bit
    Boolean drawChanged = false;
    int[] xpoints, ypoints;
    int npoints = 0;

    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
//            System.out.println(points.get(i));
            xpoints[i] = (int)points.get(i).x;
            ypoints[i] = (int)points.get(i).y;
        }
        npoints = points.size();
        pointsChanged = false;
    }

    public void prepareDraw(){
        toDraw = points;
        points = new Stack<>();
        lastP = 0;
        pointsChanged = true;
        xpoints = new int[0];
        ypoints = new int[0];
        npoints = 0;
    }

    public void prepareDrawRev(){
        toDraw.clear();
        toDraw = new Stack<>();
        lastP = 0;
        pointsChanged = true;
        xpoints = new int[0];
        ypoints = new int[0];
    }

    public void afterDrawRev(){
        points = toDraw;
        toDraw = null;
        lastP = 0;
        pointsChanged = true;
    }

    public void updatePoints(int count){
        double p;
        if(count < 33){
            double tempP = (count/33.0) * toDraw.size();
            p = tempP;
        } else {
            p = toDraw.size();
        }
        for(float i = lastP; i < p; ++i){
            int cur = (int) i;
            points.add(toDraw.get(cur));
        }
        lastP = (int)p;
        pointsChanged = true;
//        System.out.println("toDraw length is " + toDraw.size() + ", count is " + count + ", p is " + p);
    }

    public void updatePointsRev(int count){
        double p;
        if(count < 33){
            double tempP = ((33.0 - count)/33.0) * toDraw.size();
            p = tempP;
        } else {
            p = 0;
        }
        for(double i = points.size() - 1; i >= p; --i){
            toDraw.push(points.pop());
        }
        pointsChanged = true;
//        System.out.println("toDraw length is " + toDraw.size() + ", count is " + count + ", p is " + p);
    }

    // let the shape draw itself
    // (note this isn't good separation of shape View from shape Model)
    public void draw(Graphics2D g2) {

        // don't draw if points are empty (not shape)
        if (points == null) return;

        // see if we need to update the cache
        if (pointsChanged) cachePointsArray();

        // save the current g2 transform matrix
        AffineTransform M = g2.getTransform();

        // multiply in this shape's transform
        // (uniform scale)
        g2.scale(scale, scale);

        // call drawing functions
        g2.setColor(colour);
        if (isFilled) {
            g2.fillPolygon(xpoints, ypoints, npoints);
        } else {
            // can adjust stroke size using scale
            g2.setStroke(new BasicStroke(strokeThickness / scale));
            if (isClosed)
                g2.drawPolygon(xpoints, ypoints, npoints);
            else
                g2.drawPolyline(xpoints, ypoints, npoints);
        }

        // reset the transform to what it was before we drew the shape
//        g2.setTransform(M);
    }


    // let shape handle its own hit testing
    // (x,y) is the point to test against
    // (x,y) needs to be in same coordinate frame as shape, you could add
    // a panel-to-shape transform as an extra parameter to this function
    // (note this isn't good separation of shape Controller from shape Model)
    public boolean hittest(double x, double y)
    {
        if (points != null) {

            // TODO Implement

        }

        return false;
    }
}