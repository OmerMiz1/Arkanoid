/**
 * The interface Hit listener.
 *
 * @author Omer Mizrachi
 */
public interface HitListener {
    /**
     * Hit event when object is being hit.
     *
     * @param beingHit the object being hit.
     * @param hitter   the hitter (ball).
     */
    void hitEvent(Block beingHit, Ball hitter);
}