/**
 * The type Collision info.
 *
 * @author Omer Mizrachi.
 */
public class CollisionInfo {
    private Point collision;
    private Collidable object;

    /**
     * Instantiates a new Collision info.
     *
     * @param collisionP the collision p
     * @param obj        the object
     */
    public CollisionInfo(Point collisionP, Collidable obj) {
        this.collision = collisionP;
        this.object = obj;
    }

    /**
     * Collision point point.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.collision;
    }

    /**
     * Collision object collidable.
     *
     * @return the collidable
     */
    public Collidable collisionObject() {
        return this.object;
    }
}
