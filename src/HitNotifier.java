/**
 * The interface Hit notifier.
 *
 * @author Omer Mizachi.
 */
public interface HitNotifier {
    /**
     * Add hit listener.
     *
     * @param hl the hit listener obj.
     */
    void addHitListener(HitListener hl);

    /**
     * Removes hit listener.
     *
     * @param hl the hit listener.
     */
    void removeHitListener(HitListener hl);
}