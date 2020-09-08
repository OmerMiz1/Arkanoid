import java.util.ArrayList;
import java.util.List;

/**
 * The type GameLevel environment.
 *
 * @author Omer Mizrachi.
 */
public class GameEnvironment implements Cloneable {
    private List<Collidable> collidables;

    /**
     * Instantiates a new GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Instantiates a new GameLevel environment.
     *
     * @param environment the environment
     */
    public GameEnvironment(GameEnvironment environment) {
        this.collidables = environment.collidables;
    }

    /**
     * Adds a collidable to the environment's list.
     *
     * @param c the collidable to be added
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Add all colidables.
     *
     * @param ge the ge
     */
    public void addAllColidables(GameEnvironment ge) {
        for (Collidable c : ge.getCollidables()) {
            addCollidable(c);
        }
    }

    /**
     * Remove collidable.
     *
     * @param c the collidable.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Clear.
     */
    public void clear() {
        this.collidables.clear();
    }

    /**
     * Gets collidables.
     *
     * @return the collidables list
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Gets closest collision.
     * <p>
     * Given a line, if there's a collision with a collidable from the environment, return a collision info
     * object specifying the collision point and the object collided with.
     *
     * @param trajectory the path of the object that is tested to see if is going to collide
     * @return the closest collision's information specified above
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // Initialize to null, if vars remain null its a flag that no collision was found.
        Collidable closestObj = null;
        Point closestP = null;

        for (Collidable c : this.getCollidables()) {
            // Get closest point (if there is such) to the start of trajectory line
            // on the collidable's rectangle
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());

            /*1. Not intersecting
             * 2. First intersection found
             * 3. Closer intersection than what previously found */
            if (p == null) {
                continue;
            } else if (closestObj == null) {
                closestObj = c;
                closestP = p;
            } else if (p.distance(trajectory.start()) < closestP.distance(trajectory.start())) {
                closestObj = c;
                closestP = p;
            }
        }
        // If there was intersection, return the collision's info.
        if (closestObj != null) {
            return (new CollisionInfo(closestP, closestObj));
        }
        // Other wise return null.
        return null;
    }

    @Override
    public GameEnvironment clone() {
        return new GameEnvironment(this);
    }
}
