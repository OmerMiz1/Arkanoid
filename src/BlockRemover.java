/**
 * The type Block remover.
 *
 * @author Omer Mizrachi
 */
// a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel the gameLevel
     */
    public BlockRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = gameLevel.getRemainingBlocks();
    }

    /***
     * Determines if a block needs to be deleted upon hit.
     *
     * Upon deletion, also updates the gameLevel "remaining blocks" counter.
     *
     * @param beingHit the block beingHit
     * @param hitter the ball object hit the block
     * */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHp() == 1) {
            this.gameLevel.removeSprite(beingHit);
            this.gameLevel.removeCollidable(beingHit);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}