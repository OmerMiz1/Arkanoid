/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel the gameLevel
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.remainingBalls = gameLevel.getRemainingBalls();
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.gameLevel.removeSprite(hitter);
        this.remainingBalls.decrease(1);
    }
}
