/**
 * The interface Collidable.
 *
 * @author Omer Mizrachi.
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify an object it was hit and return the modified velocity on the object.
     *
     * @param hitter          the (Ball) object that hits the collidable.
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Gets point close to hit.
     *
     * @param collisionPoint the collision point
     * @param curVel         the cur vel
     * @return the point close to hit
     */
    Point getPointCloseToHit(Point collisionPoint, Velocity curVel);
}
