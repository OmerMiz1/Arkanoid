import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type GameLevel.
 *
 * @author Omer Mizrachi.
 */
public class GameLevel implements Animation {
    /**
     * The constant SCREEN_WID.
     */
    public static final int SCREEN_WID = 800;
    /**
     * The constant SCREEN_HEI.
     */
    public static final int SCREEN_HEI = 600;
    /**
     * The constant HOR_FRAME_HEI.
     */
    public static final int HOR_FRAME_HEI = 30;
    /**
     * The constant VER_FRAME_WID.
     */
    public static final int VER_FRAME_WID = 30;
    /**
     * The constant BALL_SIZE.
     */
    public static final int BALL_SIZE = 7;
    /**
     * The constant BALL_DEFAULT_COLOR.
     */
    public static final Color BALL_DEFAULT_COLOR = Color.WHITE;
    /**
     * The constant PADDLE_HEI.
     */
    public static final int PADDLE_HEI = 2 * HOR_FRAME_HEI / 3;
    /**
     * The constant PADDLE_Y_POS.
     */
    public static final int PADDLE_Y_POS = HOR_FRAME_HEI;
    /**
     * The constant STATS_BOARD_HEI.
     */
    public static final int STATS_BOARD_HEI = HOR_FRAME_HEI;
    /**
     * The constant HOR_FRAME_WID.
     */
    public static final int HOR_FRAME_WID = SCREEN_WID - (2 * VER_FRAME_WID);
    /**
     * The constant VER_FRAME_HEI.
     */
    public static final int VER_FRAME_HEI = SCREEN_HEI - STATS_BOARD_HEI;

    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private LevelInformation level;
    private Counter blocksCount = new Counter();
    private Counter ballsCount = new Counter();
    private Counter score;
    private Counter lives;
    private Counter currLevel = new Counter();
    private boolean running;

    /**
     * Instantiates a new GameLevel.
     *
     * @param li the li
     * @param ks the ks
     * @param ar the ar
     * @param s  the s
     * @param l  the l
     */
    public GameLevel(LevelInformation li, KeyboardSensor ks, AnimationRunner ar, Counter s, Counter l) {
        this.level = li;
        this.keyboard = ks;
        this.runner = ar;
        this.score = s;
        this.lives = l;
    }

    /**
     * Add collidable to the game's environment.
     *
     * @param c the collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add sprite to game.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove collidable.
     *
     * @param c the collidable.
     */
    public void removeCollidable(Collidable c) {
        if (c == null) {
            return;
        }
        this.environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite.
     */
    public void removeSprite(Sprite s) {
        if (s == null) {
            return;
        }
        this.sprites.removeSprite(s);
    }

    /**
     * Gets the environment object of the game.
     *
     * @return the environment object
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Gets the sprite collection of the game.
     *
     * @return Sprites collection.
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public LevelInformation getLevel() {
        return this.level;
    }

    /**
     * Gets animation runner.
     *
     * @return the animation runner
     */
    private AnimationRunner getAnimationRunner() {
        return this.runner;
    }

    /**
     * Generates corner points of the screen.
     * <p>
     * 0: Bottom
     * 1: Right
     * 2: Top
     * 3: Left
     *
     * @return the points in an array
     */
    private Point[] getTopLefts() {
        Point[] topLefts = new Point[4];
        // bottom, right, top, left
        topLefts[0] = new Point(VER_FRAME_WID, 0);
        topLefts[1] = new Point(SCREEN_WID - VER_FRAME_WID, VER_FRAME_HEI);
        topLefts[2] = new Point(VER_FRAME_WID, SCREEN_HEI - STATS_BOARD_HEI);
        topLefts[3] = new Point(0, VER_FRAME_HEI);
        return topLefts;
    }

    /**
     * Generates the frame blocks.
     * <p>
     * Doing that by using getTopLefts and creating -
     * 0: Bottom line
     * 1: Right line
     * 2: Top line
     * 3: Left line
     */
    public void generateFrame() {
        // set the width and heights of the frame's blocks
        Point[] topLefts = getTopLefts();
        Block b;
        BallRemover br = new BallRemover(this);

        // BOTTOM -- is a "killing block"
        b = new FrameBlock(topLefts[0], HOR_FRAME_WID, HOR_FRAME_HEI);
        b.addHitListener(br);
        b.addToGame(this);
        // RIGHT, TOP, LEFT
        new FrameBlock(topLefts[1], VER_FRAME_WID, VER_FRAME_HEI).addToGame(this);
        new FrameBlock(topLefts[2], HOR_FRAME_WID, HOR_FRAME_HEI).addToGame(this);
        new FrameBlock(topLefts[3], VER_FRAME_WID, VER_FRAME_HEI).addToGame(this);
    }

    /**
     * Generate blocks.
     */
    public void generateBlocks() {
        List<Block> levelBlocks = this.level.blocks();
        BlockRemover blockRemover = new BlockRemover(this);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);
        for (Block b : levelBlocks) {
            Block clone = b.clone();
            clone.addHitListener(blockRemover);
            clone.addHitListener(scoreTracker);
            clone.addToGame(this);
        }
        this.blocksCount.increase(this.level.numberOfBlocksToRemove());
    }

    /**
     * Generate balls.
     */
    protected void generateBalls() {
        List<Integer> ballsSpeed = this.level.getBallsSpeed();
        List<Velocity> velocities = this.level.initialBallVelocities();
        // the balls are initiated equally spread on top of the paddle, around its center.
        Point padCenter = this.getPaddle().getCollisionRectangle().getTopEdge().middle();
        double xPos = padCenter.getX() + (BALL_SIZE * (this.level.numberOfBalls() / 2));
        double yPos = padCenter.getY() + BALL_SIZE;

        for (int i = 0; i < velocities.size(); i++, xPos -= BALL_SIZE * 2) {
            Ball b = new Ball(xPos, yPos, BALL_SIZE, BALL_DEFAULT_COLOR);
            b.setVelocity(velocities.get(i));
            b.setSpeed(ballsSpeed.get(i));
            b.addToGame(this);
        }
        this.ballsCount.increase(this.level.numberOfBalls());
    }

    /**
     * Generate paddle.
     */
    public void generatePaddle() {
        Point upLeft = new Point((SCREEN_WID / 2) - (this.level.paddleWidth() / 2), PADDLE_Y_POS);
        Rectangle rectangle = new Rectangle(upLeft, this.level.paddleWidth(), PADDLE_HEI);
        Block block = new Block(rectangle);
        Paddle pad = new Paddle(block, this.level.paddleSpeed());
        pad.setKeyboard(this.keyboard);
        pad.addToGame(this);
    }

    /**
     * Generate score board.
     */
    public void generateStatsBoard() {
        Point p = new Point(0, SCREEN_HEI);
        Block b = new Block(p, SCREEN_WID, STATS_BOARD_HEI);
        StatsIndicator scoreBoard = new StatsIndicator(this.score, this.lives, this.level.levelName(), b);
        scoreBoard.addToGame(this);
    }

    /**
     * Initialize.
     */
    public void initialize() {
        this.currLevel.increase(1);
        initializeLevel();
    }

    /**
     * Remove balls.
     */
    public void removeBalls() {
        List<Sprite> sc = new ArrayList<>(this.sprites.getCollection());
        for (Sprite s : sc) {
            if (s.isBall()) {
                removeSprite(s);
                this.ballsCount.decrease(1);
            }
        }
        this.ballsCount.clear();
    }

    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    public Paddle getPaddle() {
        for (Sprite s : this.sprites.getCollection()) {
            if (s.isPaddle()) {
                return (Paddle) s;
            }
        }
        return null;
    }

    /**
     * Remove paddle.
     */
    public void removePaddle() {
        Paddle pad = getPaddle();
        removeSprite(pad);
        removeCollidable(pad);
    }

    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks
     */
    public Counter getRemainingBlocks() {
        return this.blocksCount;
    }

    /**
     * Gets remaining balls.
     *
     * @return the remaining balls
     */
    public Counter getRemainingBalls() {
        return this.ballsCount;
    }

    /**
     * Reset paddle position.
     */
    public void resetPaddle() {
        removePaddle();
        generatePaddle();
    }

    /**
     * Reset balls.
     */
    public void resetBalls() {
        removeBalls();
        generateBalls();
    }

    /**
     * Initialize level.
     */
    public void initializeLevel() {
        // Make sure everything is clear first
        this.ballsCount.clear();
        this.blocksCount.clear();
        this.sprites.clear();
        this.environment.clear();

        addSprite(this.level.getBackground());
        generateFrame();
        generateStatsBoard();
        generateBlocks();
        generatePaddle();
        generateBalls();
    }

    /**
     * Runs the game (starts the animation).
     */
    public void playOneTurn() {
        this.resetPaddle();
        this.resetBalls();
        this.running = true;
        this.runner.run(new CountdownAnimation(this.getSprites()));
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
        if (this.ballsCount.getValue() == 0) {
            this.lives.decrease(1);
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(new PauseScreen(this.sprites), this.keyboard, "space"));
            this.runner.run(new CountdownAnimation(this.getSprites()));
        } /*else if (this.keyboard.isPressed("space")) {
            this.blocksCount.clear();
        } else if (this.keyboard.isPressed("up")) {
            this.score.increase(100);
        }*/
        // Draw all sprites and notify
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        // Case no blocks or no Balls left, draw 1 more picture and stop the round.
        if (this.blocksCount.getValue() == 0 || this.ballsCount.getValue() == 0) {
            this.sprites.notifyAllTimePassed();
            this.getSprites().drawAllOn(d);
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return (!this.running);
    }
}