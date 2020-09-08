import biuoop.DrawSurface;


import static java.lang.Float.MAX_VALUE;

/**
 * The type Line.
 *
 * @author Omer Mizrachi.
 */
public class Line {
    /**
     * CONFIGURATIONS FOR LINE INTERSECTIONS.
     */
    public static final double IS_VERTICAL = MAX_VALUE;
    /**
     * The constant COLLINEAR.
     */
    public static final int COLLINEAR = 0;
    /**
     * The constant CLOCKWISE.
     */
    public static final int CLOCKWISE = 1;
    /**
     * The constant COUNTER_CLOCKWISE.
     */
    public static final int COUNTER_CLOCKWISE = 2;

    private Point start;
    private Point end;
    private double slope;
    private double length;

    /**
     * Instantiates a new Line with 4 coordinates (constructor #2).
     * <p>
     * Also calculates the slope and the free variable of the line's equation
     * and stores them in its own fields. (y = mx + c: m = slope, c = free var)
     *
     * @param x1 the x of starting point
     * @param y1 the y of starting point
     * @param x2 the x of ending point
     * @param y2 the y of ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        this.slope = genSlope();
        this.length = genLength();
    }

    /**
     * Instantiates a new Line with 2 points (constructor #1).
     * <p>
     * Also calculates the slope and the free variable of the line's equation
     * and stores them in its own fields. (y = mx + c: m = slope, c = free var)
     *
     * @param start a point indicating the start of the line
     * @param end   a point indicating the end of the line
     */
    public Line(Point start, Point end) {
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Gets the length.
     *
     * @return the length value of the line segment (absolute value)
     */
    public double length() {
        return this.length;
    }

    /**
     * Gen length double.
     *
     * @return the double
     */
    public double genLength() {
        return Math.abs(this.start.distance(this.end));
    }

    /**
     * Middle point.
     *
     * @return the point
     */
    public Point middle() {
        double avgX = ((this.start.getX() + this.end.getX()) / 2);
        double avgY = ((this.start.getY() + this.end.getY()) / 2);
        return new Point(avgX, avgY);
    }

    /**
     * Start point.
     *
     * @return the point
     */
    public Point start() {
        return this.start;
    }

    /**
     * End point.
     *
     * @return the point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns a list with flags regarding the orientation between two lines.
     * <p>
     * Method is using an algorithm to determine whether two line segments intersects with each other.
     *
     * @param other the other line to check for intersection with
     * @return the orientations flags
     */
    private long[] getOrientationsList(Line other) {
        long[] ors = new long[4];
        ors[0] = getOrientation(this.start(), this.end(), other.start());
        ors[1] = getOrientation(this.start(), this.end(), other.end());
        ors[2] = getOrientation(other.start(), other.end(), this.start());
        ors[3] = getOrientation(other.start(), other.end(), this.end());
        return ors;
    }

    /**
     * Returns the orientation between 3 points in 2D space, using an algorithm from physics.
     *
     * @param s start point of line 1
     * @param e end line of line 1
     * @param c a point (is once start, and in the other end) on line 2
     * @return A flag indicating the type of orientation between 3 dots.
     */
    private int getOrientation(Point s, Point e, Point c) {
        // calculate the orientation's value (the "curve").
        double orValue = ((e.getY() - s.getY()) * (c.getX() - e.getX()))
                - ((e.getX() - s.getX()) * (c.getY() - e.getY()));
        // 0: points are parallel (collinear), 1: clockwise 2: counter clockwise
        if (orValue == 0) {
            return COLLINEAR;
        } else if (orValue > 0) {
            return CLOCKWISE;
        }
        return COUNTER_CLOCKWISE;
    }

    /**
     * Determines if a point is on a line segment or not.
     *
     * @param s start point of the line
     * @param e end line of the line
     * @param c the point to check if is on the line
     * @return True if it is, false other wise.
     */
    private boolean onLineSeg(Point s, Point e, Point c) {
        double xMax, xMin, yMax, yMin;
        xMax = Double.max(s.getX(), c.getX());
        xMin = Double.min(s.getX(), c.getX());
        yMax = Double.max(s.getY(), c.getY());
        yMin = Double.min(s.getY(), c.getY());

        return e.getX() <= xMax && e.getX() >= xMin && e.getY() <= yMax && e.getY() >= yMin;
    }

    /**
     * Checks if this line intersects with the other line.
     *
     * @param other the other line
     * @return true if intersecting, false if not.
     */
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    /**
     * If there is an intersection point - returns it.
     *
     * @param other the other line
     * @return the point of intersection, if not - returns null.
     */
    public Point intersectionWith(Line other) {
        long[] orients = getOrientationsList(other);
        double xVal = 0, yVal = 0;

        /* Cases :
         * 1. Lines are not collinear and intersect with each other.
         * 2. Lines are collinear and other line start point is on this line.
         * 3. Lines are collinear and other line end point is on this line.
         * 4. Lines are collinear and this line start point is on the other line.
         * 5. Lines are collinear and this line end point is on the other line.*/
        if (orients[0] != orients[1] && orients[2] != orients[3]) {
            return calcIntersection(other);
        } else if (orients[0] == 0 && onLineSeg(this.start(), other.start(), this.end())) {
            return other.start();
        } else if (orients[1] == 0 && onLineSeg(this.start(), other.end(), this.end())) {
            return other.end();
        } else if (orients[2] == 0 && onLineSeg(other.start(), this.start(), other.end())) {
            return this.start();
        } else if (orients[3] == 0 && onLineSeg(other.start(), this.end(), other.end())) {
            return this.end();
        }
        // No intersection.
        return null;
    }

    /**
     * Checks if this line is the same as the other line.
     *
     * @param other the other line
     * @return true if yes, false otherwise.
     */
    public boolean equals(Line other) {
        //if start1 == start2 and end1 == end2 return true.
        if ((this.start.equals(other.start)) && (this.end.equals(other.end))) {
            return true;
        }
        //else if start1 == end2 and end1 == start2 return true
        return (this.start.equals(other.end)) && (this.end.equals(other.start));
    }

    /**
     * Calculates the slope (aka 'm').
     *
     * @return the slope of the line
     */
    public double slope() {
        return this.slope;
    }

    /**
     * Gen slope double.
     *
     * @return the double
     */
    public double genSlope() {
        double xDif = (this.start.getX() - this.end.getX());
        double yDif = (this.start.getY() - this.end.getY());
        // Case xDif is 0 and yDif is not, meaning the line is vertical.
        // NOTE: IS_VERTICAL is the maximum double value.
        if (yDif != 0 && xDif == 0) {
            return IS_VERTICAL;
        }
        return (yDif / xDif);
    }

    /**
     * Calculates the free variable of the line's equation (aka 'c').
     *
     * @return the free variable's value
     */
    private double freeVar() {
        // Case line is vertical the equation is : X = C, so just return a known x value on that line.
        if (this.slope == IS_VERTICAL) {
            return this.start().getX();
        }
        return (this.start.getY() - (this.start.getX() * this.slope));
    }

    /**
     * Calculate the intersection point of the two lines.
     *
     * @param other the other line.
     * @return the intersection point
     */
    private Point calcIntersection(Line other) {
        double m1 = this.slope, m2 = other.slope, c1 = this.freeVar(), c2 = other.freeVar();
        double yVal, xVal;
        /* Cases:
         * 1. This line is vertical
         * 2. The other line is vertical
         * 3. None of the above */
        if (m1 == IS_VERTICAL) {
            xVal = c1;
            yVal = m2 * xVal + c2;
        } else if (m2 == IS_VERTICAL) {
            xVal = c2;
            yVal = m1 * xVal + c1;
        } else {
            xVal = ((c2 - c1) / (m1 - m2));
            yVal = m1 * xVal + c1;
        }
        return new Point(xVal, yVal);
    }

    /**
     * Closest intersection to the starting point.
     *
     * @param rect the rectangle to be checked if intersecting.
     * @return The closest intersection point, if no such point - return null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get the frame lines of the rectangle
        Line[] edges = rect.getFrame();
        Point intersection;
        Point currClosest = null;

        // Iterate each edge (wall) of the rectangle's frame to check for intersection.
        for (Line edge : edges) {
            intersection = this.intersectionWith(edge);

            /*Cases:
             * 1. No intersections.
             * 2. This is the first intersection found.
             * 3. Current intersection found is closer than the previous one */
            if (intersection == null) {
                continue;
            } else if (currClosest == null) {
                currClosest = intersection;
            } else if (intersection.distance(this.start()) < currClosest.distance(this.start())) {
                currClosest = intersection;
            }
        }
        // If no intersections just returns null.
        return currClosest;
    }

    /**
     * Draws the line on the given surface.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        int x1 = this.start().getXInt(), x2 = this.end().getXInt();
        int y1 = this.start().getYRev(d).intValue(), y2 = this.end().getYRev(d).intValue();
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * Tells if a line segment contains a point.
     *
     * @param p the point
     * @return True if it is, false other wise.
     */
    public boolean containsPoint(Point p) {
        // given line AB, check if point c is on it this way :
        // if Ac + cB == length --> than c is on line (distance is absolute)
        return this.length == this.start.distance(p) + this.end.distance(p);
    }

    /**
     * Shift right.
     *
     * @param dist the dist
     */
    public void shiftRight(double dist) {
        this.start.setPoint(this.start.getX() + dist, this.start.getY());
        this.end.setPoint(this.end.getX() + dist, this.end.getY());
    }
}