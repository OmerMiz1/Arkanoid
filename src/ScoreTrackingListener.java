/**
 * The type Score tracking listener.
 *
 * @author Omer Mizrachi
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Updates score's counter according to the game's rules.
     * <p>
     * Current rules: hit = +5pts, block kill = +10pts.
     *
     * @param beingHit the block.
     * @param hitter   the ball.
     **/
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHp() == 1) {
            this.currentScore.increase(10);
        }
    }
}