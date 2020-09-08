import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Text box.
 */
public class TextBox implements Sprite {
    private Rectangle box;
    private Color textCol;
    private String text;
    private int size;

    /**
     * Instantiates a new Text box.
     *
     * @param r        the r
     * @param txtCol   the txt col
     * @param txt      the txt
     * @param textSize the text size
     */
    public TextBox(Rectangle r, Color txtCol, String txt, int textSize) {
        this.box = r;
        this.textCol = txtCol;
        this.text = txt;
        this.size = textSize;
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = box.getUpperLeft().getXInt(), y = box.getUpperLeft().getYRev(d).intValue();

        // text's frame / outline
        d.setColor(Color.BLACK);
        d.drawText(x - 1, y - 1, this.text, this.size);
        d.drawText(x + 1, y + 1, this.text, this.size);

        // text's color main color
        d.setColor(this.textCol);
        d.drawText(x, y, this.text, this.size);
    }

    @Override
    public void timePassed() {

    }


    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    @Override
    public Color getColor() {
        return this.textCol;
    }

    @Override
    public boolean isPaddle() {
        return false;
    }

    @Override
    public boolean isBall() {
        return false;
    }
}
