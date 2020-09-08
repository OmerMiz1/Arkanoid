import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Ball.
 *
 * @author Omer Mizrachi.
 */
public class Ball implements Sprite, Cloneable {
    private Point center;
    private int r;
    private int speed;
    private Color color;
    private Velocity v;
    private GameEnvironment env;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Instantiates a new Ball (constructor #1).
     *
     * @param c      the center point of the ball (circle)
     * @param radius the radius of the ball
     * @param col    the color of the ball
     */
    public Ball(Point c, int radius, java.awt.Color col) {
        this.center = c;
        this.r = radius;
        this.color = col;
        this.env = new GameEnvironment();
    }

    /**
     * Instantiates a new Ball (constructor #2).
     *
     * @param x      the x of center point of the ball
     * @param y      the y of center point of the ball
     * @param radius the radius of the ball
     * @param col    the color of the ball
     */
    public Ball(double x, double y, int radius, java.awt.Color col) {
        this(new Point(x, y), radius, col);
    }

    /**
     * Instantiates a new Ball.
     *
     * @param toClone the to clone
     */
    public Ball(Ball toClone) {
        this.r = toClone.getSize();
        this.center = toClone.getCenter().clone();
        this.v = toClone.getVelocity().clone();
        this.color = toClone.getColor();
        this.env = toClone.getEnvironment().clone();
        this.hitListeners = toClone.hitListeners;
    }

    /**
     * Generates a random color.
     *
     * @return a random color.
     */
    public static Color randColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        return new Color(r, g, b);
    }

    /**
     * Gets x of the center point.
     *
     * @return the x of the center
     */
    public int getX() {
        return this.center.getXInt();
    }

    /**
     * Gets y of the center point.
     *
     * @return the y of the center
     */
    public int getY() {
        return this.center.getYInt();
    }

    /**
     * Gets size (radius).
     *
     * @return the size (radius) of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean isPaddle() {
        return false;
    }

    @Override
    public boolean isBall() {
        return true;
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Draws the ball on given surface.
     *
     * @param d the surface to draw the ball on.
     */
    public void drawOn(DrawSurface d) {
        int x = this.getCenter().getXInt();
        int y = this.getCenter().getYRev(d).intValue();

        d.setColor(this.getColor());
        d.fillCircle(x, y, this.getSize());
        d.setColor(Color.BLACK);
        d.drawCircle(x, y, this.getSize());
        // draws the center of the ball (RED)
        d.setColor(Color.RED);
        d.fillCircle(x, y, 2);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        setEnvironment(gameLevel.getEnvironment());
    }

    /**
     * Sets the velocity (method #2).
     *
     * @param dx the x differential
     * @param dy the y differential
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    /**
     * Gets the velocity of the ball (object).
     *
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Sets the velocity of the ball (method #1).
     *
     * @param velocity the Velocity object
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }

    /**
     * Move one step on the surface, in relation to the frame's limitations
     * and the velocity of the ball.
     */
    public void moveOneStep() {
        CollisionInfo info = this.env.getClosestCollision(getTrajectory());
        Collidable obj;
        Point colP;
        Velocity newV = this.getVelocity();

        // case no hit move to the end of the trajectory.
        if (info == null) {
            this.center = newV.applyToPoint(this.center);
            return;
        }

        // otherwise there is a hit so first: set the new center point of the ball
        // to be very close to the hit point. second: set the new velocity of the
        // ball. (the vars are mainly for readability....)

        obj = info.collisionObject();
        colP = info.collisionPoint();

        newV = obj.hit(this, colP, newV);
        this.center.setPoint(obj.getPointCloseToHit(colP, this.getVelocity()));
        this.setVelocity(newV);
    }

    /**
     * Gets trajectory.
     *
     * @return the trajectory
     */
    public Line getTrajectory() {
        Point p1 = this.getCenter();
        Point p2 = new Point(p1.getX() + this.getVelocity().getDx(), p1.getY() + this.getVelocity().getDy());
        return (new Line(p1, p2));
    }

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public GameEnvironment getEnvironment() {
        return this.env;
    }

    /**
     * Sets environment.
     *
     * @param environment the environment
     */
    public void setEnvironment(GameEnvironment environment) {
        this.env = new GameEnvironment(environment);
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Sets speed.
     *
     * @param spd the speed
     */
    public void setSpeed(int spd) {
        this.speed = spd;
    }

    /**
     * Returns a copy of ball.
     *
     * @return A new Ball object
     **/
    public Ball clone() {
        return new Ball(this);
    }

}