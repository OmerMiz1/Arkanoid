import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    /**
     * The constant BG_COLOR.
     */
    public static final Color BG_COLOR = new Color(0, 9, 154);
    /**
     * The constant MAIN_TITLE_COLOR.
     */
    public static final Color MAIN_TITLE_COLOR = new Color(255, 251, 9);
    /**
     * The constant SUB_TITLE_COLOR.
     */
    public static final Color SUB_TITLE_COLOR = new Color(28, 156, 12);
    /**
     * The constant USER_SCORE_COLOR.
     */
    public static final Color USER_SCORE_COLOR = new Color(138, 193, 5);
    /**
     * The constant CONTINUE_COLOR.
     */
    public static final Color CONTINUE_COLOR = new Color(226, 80, 21);
    /**
     * The constant TEXT_SIZE.
     */
    public static final int TEXT_SIZE = GameLevel.SCREEN_WID / 30;
    private SpriteCollection sprites = new SpriteCollection();

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        genSprites(scores);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

    /**
     * Gen sprites.
     *
     * @param scores the scores
     */
    public void genSprites(HighScoresTable scores) {
        int dWid, dHei, nameX, nameY, scoreX, scoreY, lineY, lineWid, lineHei, yGap, infoY;
        Rectangle rTemp;
        Block bTemp;
        Image bgImg = null;
        URL bgUrl;
        File bgFile;
        dWid = GameLevel.SCREEN_WID;
        dHei = GameLevel.SCREEN_HEI;
        nameX = (dWid * 11) / 20;
        nameY = (dHei * 17) / 20;
        scoreX = (dWid * 17) / 20;
        scoreY = (dHei * 17) / 20;
        lineWid = (dWid * 4) / 10;
        lineHei = 3;
        lineY = nameY - lineHei * 3;
        yGap = dHei / 12;
        infoY = nameY - yGap;

        //BG
        bTemp = new Block(0, dHei, dWid, dHei);
        bgUrl = getClass().getResource("resources/background_images/one_punch_man_ok.png");
        bgFile = new File(bgUrl.getPath());

        if (bgUrl != null) {
            try {
                bgImg = ImageIO.read(bgFile);
                bTemp.setCurrImg(bgImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bTemp.setCurrCol(Color.BLUE);
        }
        this.sprites.addSprite(bTemp);

        // High Score title
        rTemp = new Rectangle((dWid * 1) / 10, (dHei * 9) / 10, dWid / 8, dHei / 10);
        this.sprites.addSprite(new TextBox(rTemp, MAIN_TITLE_COLOR, "High Scores", ((int) (TEXT_SIZE * 1.25))));

        // Player Name title
        rTemp = new Rectangle(nameX, nameY, lineWid / 2, dHei / 9);
        this.sprites.addSprite(new TextBox(rTemp, SUB_TITLE_COLOR, "Player Name", TEXT_SIZE));

        // Score title
        rTemp = new Rectangle(scoreX, scoreY, lineWid / 2, dHei / 9);
        this.sprites.addSprite(new TextBox(rTemp, SUB_TITLE_COLOR, "Score", TEXT_SIZE));

        // Line below Player Name and Score
        rTemp = new Rectangle(nameX, lineY, lineWid, lineHei);
        rTemp.setColor(SUB_TITLE_COLOR);
        this.sprites.addSprite(rTemp);

        // Press space to continue message
        rTemp = new Rectangle(dWid / 4, dHei / 8, dWid / 3, dHei / 6);
        this.sprites.addSprite(
                new TextBox(rTemp, CONTINUE_COLOR, "Press space to coninue", ((int) (TEXT_SIZE * 1.25))));

        for (ScoreInfo si : scores.getHighScores()) {
            if (scores.getHighScores().indexOf(si) >= scores.size()) {
                break;
            }
            Rectangle uName = new Rectangle(nameX, infoY, lineWid / 2, dHei / 9);
            this.sprites.addSprite(new TextBox(uName, USER_SCORE_COLOR, si.getName(), TEXT_SIZE));

            Rectangle uScore = new Rectangle(scoreX, infoY, lineWid / 2, dHei / 9);
            this.sprites.addSprite(new TextBox(uScore, USER_SCORE_COLOR, Integer.toString(si.getScore()), TEXT_SIZE));

            infoY -= yGap;
        }
    }
}