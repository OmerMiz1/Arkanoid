import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Block.
 *
 * @author Omer Mizrachi.
 */
public class Block implements Collidable, Sprite, HitNotifier, Cloneable {
    /**
     * The constant FONT_SIZE.
     */
    public static final int FONT_SIZE = 20;
    /**
     * The constant DEFAULT_FILL_KEY.
     */
    public static final Integer DEFAULT_FILL_KEY = FillParser.DEFAULT_FILL_KEY;

    /**
     * The Rect.
     */
    private Rectangle rect = null;
    private Color stroke = null;
    private Color currCol = null;
    private Image currImg = null;
    private Map<Integer, Color> colorsMap;
    private Map<Integer, Image> imagesMap;
    private List<HitListener> hitListeners = new ArrayList<>();
    private int hp;

    /**
     * Instantiates a new Block.
     *
     * @param rect   the rect
     * @param stroke the stroke
     * @param cols   the cols
     * @param imgs   the imgs
     * @param hp     the hp
     */
    public Block(Rectangle rect, Color stroke, Map<Integer, Color> cols, Map<Integer, Image> imgs, int hp) {
        this.rect = rect;
        this.stroke = stroke;
        this.colorsMap = cols;
        this.imagesMap = imgs;
        this.hp = hp;
    }

    /**
     * Instantiates a new Block.
     *
     * @param rect the rect
     */
    public Block(Rectangle rect) {
        this(rect, null, null, null, -1);
    }

    /**
     * Instantiates a new Block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public Block(Point upperLeft, int width, int height) {
        this(new Rectangle(upperLeft, width, height));
    }

    /**
     * Instantiates a new Block.
     *
     * @param xUpLeft the x up left
     * @param yUpLeft the y up left
     * @param width   the width
     * @param height  the height
     */
    public Block(int xUpLeft, int yUpLeft, int width, int height) {
        this(new Rectangle(new Point(xUpLeft, yUpLeft), width, height));
    }

    /**
     * Instantiates a new Block.
     *
     * @param toClone the to clone
     */
    public Block(Block toClone) {
        this.rect = toClone.rect.clone();
        this.colorsMap = new TreeMap<>(toClone.colorsMap);
        this.imagesMap = new TreeMap<>(toClone.imagesMap);
        this.currImg = toClone.currImg;
        this.currCol = toClone.currCol;
        this.stroke = toClone.stroke;
        this.hp = toClone.hp;
        this.hitListeners = toClone.hitListeners;
    }

    /**
     * Sets curr col.
     *
     * @param column the curr col
     */
    public void setCurrCol(Color column) {
        this.currCol = column;
    }

    /**
     * Sets curr img.
     *
     * @param image the curr img
     */
    public void setCurrImg(Image image) {
        this.currImg = image;
    }

    /**
     * Sets stroke col.
     *
     * @param col the col
     */
    public void setStrokeCol(Color col) {
        this.stroke = col;
    }

    /**
     * Gets the center point of te rectangle.
     *
     * @return The center point of the rectangle.
     */
    protected Point getCenterPoint() {
        Rectangle temp = this.getCollisionRectangle();
        double x = temp.getTopEdge().middle().getX();
        double y = temp.getRightEdge().middle().getY();
/*        double x = temp.getUpperLeft().getX() + (temp.getBlockWidth() / 2);
        double y = temp.getUpperLeft().getY() - (temp.getBlockHeight() / 2);*/
        return (new Point(x, y));
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Sets hp.
     *
     * @param hitpoints the hp
     */
    public void setHp(int hitpoints) {
        this.hp = hitpoints;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        Line[] frame = this.rect.getFrame();

        // case BOTTOM or TOP walls were hit - change direction of y change
        if (frame[0].containsPoint(collisionPoint) || frame[2].containsPoint(collisionPoint)) {
            dy *= -1;
        }
        // case RIGHT or LEFT walls were hit - change direction of y change
        if (frame[1].containsPoint(collisionPoint) || frame[3].containsPoint(collisionPoint)) {
            dx *= -1;
        }

        notifyHit(hitter);

        if (this.hp > 0) {
            this.hp--;
        }
        return (new Velocity(dx, dy));
    }

    /**
     * Update fill.
     */
    public void updateFill() {
        if (this.colorsMap.containsKey(DEFAULT_FILL_KEY)) {
            this.currCol = this.colorsMap.get(DEFAULT_FILL_KEY);
            this.currImg = null;
        } else if (this.imagesMap.containsKey(DEFAULT_FILL_KEY)) {
            this.currImg = this.imagesMap.get(DEFAULT_FILL_KEY);
            this.currCol = null;
        }

        if (this.colorsMap.containsKey(this.hp)) {
            this.currCol = this.colorsMap.get(this.hp);
            this.currImg = null;
        } else if (this.imagesMap.containsKey(this.hp)) {
            this.currImg = this.imagesMap.get(this.hp);
            this.currCol = null;
        }
    }

    @Override
    public Point getPointCloseToHit(Point colP, Velocity curVel) {
        Double newX = colP.getX(), newY = colP.getY();

        // BOTTOM \ TOP
        if (this.rect.getBottomEdge().containsPoint(colP)) {
            newY = colP.getY() - 1;
        } else if (this.rect.getTopEdge().containsPoint(colP)) {
            newY = colP.getY() + 1;
        }
        // RIGHT \ LEFT
        if (this.rect.getRightEdge().containsPoint(colP)) {
            newX = colP.getX() + 1;
        } else if (this.rect.getLeftEdge().containsPoint(colP)) {
            newX = colP.getX() - 1;
        }

        return new Point(newX, newY);
    }

    /**
     * Draws the rect on the given surface.
     *
     * @param d the surface.
     */
    public void drawOn(DrawSurface d) {
        int x, y;
        x = this.rect.getUpperLeft().getXInt();
        y = this.rect.getUpperLeft().getYRev(d).intValue();

        if (this.currImg != null) {
            d.drawImage(x, y, this.currImg);
        } else {
            d.setColor(this.currCol);
            this.rect.drawOn(d);
        }

        if (this.stroke != null) {
            d.setColor(this.stroke);
            this.rect.drawStrokeOn(d);
        }
    }

    @Override
    public void timePassed() {
        if (this.hp > 0) {
            updateFill();
        }
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    @Override
    public Color getColor() {
        return currCol;
    }

    /**
     * Move horizontally.
     *
     * @param newXVal the new x val
     */
    public void moveHorizontally(double newXVal) {
        double newYVal = this.rect.getUpperLeft().getY();
        this.rect.setUpLeft(newXVal, newYVal);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.rect.getWidth();
    }

    /**
     * Notify hit.
     *
     * @param hitter the hitter.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public boolean isPaddle() {
        return false;
    }

    @Override
    public boolean isBall() {
        return false;
    }

    /**
     * Returns a copy of ball.
     *
     * @return A new Block object
     **/
    public Block clone() {
        return new Block(this);
    }
}
