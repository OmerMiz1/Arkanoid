import java.util.ArrayList;
import java.util.List;

/**
 * The type Dynamic level.
 */
public class DynamicLevel implements LevelInformation {
    private String title = null;
    private int padSpeed = -1;
    private int padWidth = -1;
    private int blockStartX = -1;
    private int blockStartY = -1;
    private int rowHei = -1;
    private int blocksNum = -1;
    private List<Velocity> velocities = new ArrayList<>();
    private List<Integer> ballsSpeed = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();
    private Block background;

    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.padSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.padWidth;
    }

    @Override
    public String levelName() {
        return this.title;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Sets background.
     *
     * @param s the s
     */
    public void setBackground(String s) {
        FillParser fp = new FillParser();
        this.background = new Block(0, GameLevel.SCREEN_HEI, GameLevel.SCREEN_WID, GameLevel.SCREEN_HEI);
        if (fp.isImage(s)) {
            this.background.setCurrImg(fp.parseImg(s));
            return;
        }
        this.background.setCurrCol(fp.parseCol(s.split(":")[1]));
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksNum;
    }

    /**
     * Gets block start x.
     *
     * @return the block start x
     */
    public int getBlockStartX() {
        return blockStartX;
    }

    /**
     * Sets block start x.
     *
     * @param blockStartingX the block starting x
     */
    public void setBlockStartX(int blockStartingX) {
        this.blockStartX = blockStartingX;
    }

    /**
     * Gets block start y.
     *
     * @return the block start y
     */
    public int getBlockStartY() {
        return blockStartY;
    }

    /**
     * Sets block start y.
     *
     * @param blockStartingY the block starting y
     */
    public void setBlockStartY(int blockStartingY) {
        this.blockStartY = blockStartingY;
    }

    /**
     * Gets row hei.
     *
     * @return the row hei
     */
    public int getRowHei() {
        return rowHei;
    }

    /**
     * Sets row hei.
     *
     * @param rowHeight the row height
     */
    public void setRowHei(int rowHeight) {
        this.rowHei = rowHeight;
    }

    /**
     * Sets title.
     *
     * @param levelName the level name
     */
    public void setTitle(String levelName) {
        this.title = levelName;
    }

    /**
     * Sets paddle speed.
     *
     * @param paddleSpeed the paddle speed
     */
    public void setPaddleSpeed(int paddleSpeed) {
        this.padSpeed = paddleSpeed;
    }

    /**
     * Sets paddle width.
     *
     * @param paddleWidth the paddle width
     */
    public void setPaddleWidth(int paddleWidth) {
        this.padWidth = paddleWidth;
    }

    /**
     * Sets blocks num.
     *
     * @param blocksNumber the blocks number
     */
    public void setBlocksNum(int blocksNumber) {
        this.blocksNum = blocksNumber;
    }

    /**
     * Add ball velocity.
     *
     * @param v the v
     */
    public void addBallVelocity(Velocity v) {
        this.velocities.add(v);
    }

    /**
     * Add block.
     *
     * @param b the b
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }

    /**
     * Add ball speed.
     *
     * @param ballSpeed the ball speed
     */
    public void addBallSpeed(Integer ballSpeed) {
        this.ballsSpeed.add(ballSpeed);
    }

    @Override
    public List<Integer> getBallsSpeed() {
        return this.ballsSpeed;
    }
}
