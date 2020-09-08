import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    /**
     * The constant MAIN_TITLE_COLOR.
     */
    public static final Color MAIN_TITLE_COLOR = new Color(255, 251, 9);
    /**
     * The constant SUB_TITLE_COLOR.
     */
    public static final Color SUB_TITLE_COLOR = new Color(28, 156, 12);
    /**
     * The constant TEXT_SIZE.
     */
    public static final int TEXT_SIZE = GameLevel.SCREEN_WID / 30;

    private AnimationRunner runner;
    private KeyboardSensor ks;
    private String title;
    private SpriteCollection sprites = new SpriteCollection();
    private List<String> optionKeys = new ArrayList<>();
    private List<String> optionTxt = new ArrayList<>();
    private List<T> optionRetVal = new ArrayList<>();
    private String subMenuKey = null;
    private String subMenuTxt = null;
    private Menu<T> subMenu;
    private T status;
    private boolean taskDone = false;

    /**
     * Instantiates a new Menu animation.
     *
     * @param ar       the ar
     * @param keyboard the keyboard
     * @param menuName the menu name
     */
    public MenuAnimation(AnimationRunner ar, KeyboardSensor keyboard, String menuName) {
        this.runner = ar;
        this.ks = keyboard;
        this.title = menuName;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.optionKeys.add(key);
        this.optionTxt.add(message);
        this.optionRetVal.add(returnVal);
        this.sprites.clear();
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> menu) {
        this.optionKeys.add(key);
        this.subMenuKey = key;
        this.optionTxt.add(message);
        this.subMenuTxt = message;
        this.subMenu = menu;
        this.optionRetVal.add(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);

        for (String key : this.optionKeys) {
            if (this.ks.isPressed(key)) {
                if (key.equals(this.subMenuKey)) {
                    this.runner.run(this.subMenu);

                    T task = this.subMenu.getStatus();
                    ((Task<Void>) task).run();
                    ((MenuAnimation<Task<Void>>) this.subMenu).resetStatus();
                    this.status = this.subMenu.getStatus();
                    break;
                } else {
                    this.status = this.optionRetVal.get(this.optionKeys.indexOf(key));
                    break;
                }
            }
        }
    }

    /**
     * Reset status.
     */
    public void resetStatus() {
        this.taskDone = false;
        this.status = null;
    }

    @Override
    public boolean shouldStop() {
        return (this.status != null);
    }

    /**
     * Gen sprites.
     */
    public void genSprites() {
        int dHei, dWid, optionX, optionY, optionWid, optionHei, yGap;
        String optionText;
        Rectangle rTemp;
        Block bTemp;
        Image bgImage = null;
        URL imgUrl;
        File imgFile;

        dWid = GameLevel.SCREEN_WID;
        dHei = GameLevel.SCREEN_HEI;
        optionX = (dWid * 2) / 10;
        optionY = (dHei * 8) / 10;
        optionWid = (dWid * 6) / 20;
        optionHei = dHei / 9;
        yGap = dHei / 12;

        //BG
        bTemp = new Block(0, dHei, dWid, dHei);
        imgUrl = getClass().getResource("resources/background_images/hidden_leaf.jpg");
        imgFile = new File(imgUrl.getPath());
        if (imgUrl != null) {
            try {
                bgImage = ImageIO.read(imgFile);
                bTemp.setCurrImg(bgImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bTemp.setCurrCol(Color.BLUE);
        }
        this.sprites.addSprite(bTemp);

        rTemp = new Rectangle((dWid / 10), ((dHei * 9) / 10), (dWid / 8), (dHei / 10));
        this.sprites.addSprite(new TextBox(rTemp, MAIN_TITLE_COLOR, this.title, ((int) (TEXT_SIZE * 1.25))));

        for (String key : this.optionKeys) {
            optionText = "(" + key + ") " + this.optionTxt.get(this.optionKeys.indexOf(key));
            rTemp = new Rectangle(optionX, optionY, optionWid, optionHei);
            this.sprites.addSprite(new TextBox(rTemp, SUB_TITLE_COLOR, optionText, TEXT_SIZE));
            optionY -= yGap;
        }
    }

    /**
     * Gen sub menu sprites.
     *
     * @param sets the sets
     */
    public void genSubMenuSprites(List<LevelSet> sets) {
        int dWid, dHei, keyX, keyY, descX, descY, yGap;
        Rectangle temp;
        Block bTemp;
        Image bgImage = null;
        URL imgUrl;
        File imgFile;
        this.sprites.clear();
        dWid = GameLevel.SCREEN_WID;
        dHei = GameLevel.SCREEN_HEI;
        keyX = dWid / 5;
        keyY = (dHei * 4) / 5;
        descX = (dWid * 3) / 10;
        descY = (dHei * 4) / 5;
        yGap = dHei / 12;

        // BG
        bTemp = new Block(0, dHei, dWid, dHei);
        imgUrl = getClass().getResource("resources/background_images/naruto_sasuke_death_valley.jpg");
        imgFile = new File(imgUrl.getPath());
        if (imgUrl != null) {
            try {
                bgImage = ImageIO.read(imgFile);
                bTemp.setCurrImg(bgImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bTemp.setCurrCol(Color.BLUE);
        }
        this.sprites.addSprite(bTemp);

        // Level Set title
        temp = new Rectangle((dWid / 10), ((dHei * 9) / 10), (dWid / 8), (dHei / 10));
        this.sprites.addSprite(new TextBox(temp, MAIN_TITLE_COLOR, this.title, ((int) (TEXT_SIZE * 1.25))));

        for (LevelSet ls : sets) {
            Rectangle key = new Rectangle(keyX, keyY, (dWid / 5), (dHei / 10));
            this.sprites.addSprite(new TextBox(key, SUB_TITLE_COLOR, "(" + ls.key() + ")", TEXT_SIZE));
            Rectangle desc = new Rectangle(descX, descY, (dWid / 5), (dHei / 10));
            this.sprites.addSprite(new TextBox(desc, SUB_TITLE_COLOR, ls.description(), TEXT_SIZE));
            keyY -= yGap;
            descY -= yGap;
        }
    }
}
