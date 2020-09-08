import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * The type Paddle.
 *
 * @author Omer Mizrachi.
 */
public class Paddle implements Sprite, Collidable {
    /**
     * The constant PADDLE_COLOR.
     */
    public static final Color PADDLE_COLOR = Color.ORANGE;

    private Block padBlock;
    private biuoop.KeyboardSensor keyboard;
    private int padSpeed;

    /**
     * Instantiates a new Paddle.
     *
     * @param block the block
     * @param speed the speed
     */
    public Paddle(Block block, int speed) {
        this.padBlock = block;
        this.padSpeed = speed;
        block.setHp(-1);
    }

    /**
     * Instantiates a new Paddle.
     *
     * @param block the block
     */
    public Paddle(Block block) {
        this(block, 10);
    }

    /**
     * Instantiates a new Paddle.
     *
     * @param toClone the to clone
     */
    public Paddle(Paddle toClone) {
        this.padBlock = toClone.padBlock.clone();
        this.keyboard = toClone.keyboard;
        this.padSpeed = toClone.padSpeed;
    }

    /**
     * Sets keyboard.
     *
     * @param ks the keyboard sensor.
     */
    public void setKeyboard(KeyboardSensor ks) {
        this.keyboard = ks;
    }

    /**
     * Sets speed.
     *
     * @param speed the speed
     */
    public void setSpeed(int speed) {
        this.padSpeed = speed;
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        double currXVal = this.padBlock.getCollisionRectangle().getUpperLeft().getX();

        // In case the padBlock is getting out of the left frame.
        if (currXVal - this.padSpeed < GameLevel.VER_FRAME_WID) {
            this.padBlock.moveHorizontally(GameLevel.VER_FRAME_WID + 1);
            return;
        }
        this.padBlock.moveHorizontally(currXVal - this.padSpeed);
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        double currXVal = this.padBlock.getCollisionRectangle().getUpperLeft().getX();
        double padWid = this.padBlock.getCollisionRectangle().getWidth();

        // In case the padBlock is getting out of the right frame.
        if (currXVal + padWid + this.padSpeed > GameLevel.SCREEN_WID - GameLevel.VER_FRAME_WID) {
            this.padBlock.moveHorizontally(GameLevel.SCREEN_WID - GameLevel.VER_FRAME_WID - padWid - 1);
            return;
        }
        this.padBlock.moveHorizontally(currXVal + this.padSpeed);
    }

    /**
     * Notify the paddle (sprite) time has passed - reading the key from the user.
     */
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addCollidable(this);
        gameLevel.addSprite(this);
    }

    /**
     * Draws the paddle of te given surface.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(PADDLE_COLOR);
        this.getCollisionRectangle().drawOn(d);
        d.setColor(Color.BLACK);
        this.padBlock.getCollisionRectangle().drawStrokeOn(d);
    }

    /**
     * Returns the Rectangle (object) property of the paddle.
     *
     * @return The object.
     */
    public Rectangle getCollisionRectangle() {
        return this.padBlock.getCollisionRectangle();
    }

    /**
     * Start the hit procedure for the paddle.
     * <p>
     * Depending on the location of the hit, and the wall determine how the colliding object should move next.
     *
     * @param hitter          the ball object that initiated the hit.
     * @param collisionPoint  the point where the hit was made.
     * @param currentVelocity the current velocity of the object collided.
     * @return The new velocity value for the colliding object.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Get the walls of the paddle.
        Line padTop = this.getCollisionRectangle().getTopEdge();
        // Case hit the top - behave as needed, other was just bounce the ball like any other block.
        if (padTop.containsPoint(collisionPoint)) {
            return this.hitTop(hitter, collisionPoint, padTop);
        } else {
            return this.padBlock.hit(hitter, collisionPoint, currentVelocity);
        }
    }

    /**
     * Determine the new velocity of the object collided with paddle's top wall.
     * <p>
     * The top wall is divided into 5 regions, each region returns a different velocity.
     *
     * @param hitter         the ball object initiated the hit.
     * @param collisionPoint the collision point
     * @param upperEdge      the upper wall line
     * @return the new modified velocity
     */
    public Velocity hitTop(Ball hitter, Point collisionPoint, Line upperEdge) {
        // Distance between collision and paddle's starting point
        double distance = upperEdge.start().distance(collisionPoint);
        Velocity newV;

        //First region to fifth region conditions
        if (distance < (upperEdge.length() / 5)) {
            newV = Velocity.fromAngleAndSpeed(150, hitter.getSpeed());
        } else if (distance < 2 * (upperEdge.length() / 5)) {
            newV = Velocity.fromAngleAndSpeed(120, hitter.getSpeed());
        } else if (distance < 3 * (upperEdge.length() / 5)) {
            // just changes the vertical direction of the ball (DY)
            newV = hitter.getVelocity();
            newV.setVelocity(newV.getDx(), (-1) * newV.getDy());
        } else if (distance < 4 * (upperEdge.length() / 5)) {
            newV = Velocity.fromAngleAndSpeed(30, hitter.getSpeed());
        } else {
            newV = Velocity.fromAngleAndSpeed(60, hitter.getSpeed());
        }
        return newV;
    }

    /**
     * Gets a point which is very close to the hit point, but not on it.
     *
     * @param collisionPoint the collision point.
     * @param curVel         the current velocity.
     * @return A close point to the collision.
     */
    @Override
    public Point getPointCloseToHit(Point collisionPoint, Velocity curVel) {
        return this.padBlock.getPointCloseToHit(collisionPoint, curVel);
    }

    @Override
    public Color getColor() {
        return PADDLE_COLOR;
    }

    /**
     * Returns a copy of the paddle.
     *
     * @return a new Paddle object.
     */
    public Paddle clone() {
        return new Paddle(this);
    }

    @Override
    public boolean isPaddle() {
        return true;
    }

    @Override
    public boolean isBall() {
        return false;
    }

}