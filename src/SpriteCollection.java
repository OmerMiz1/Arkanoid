import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sprite collection.
 *
 * @author Omer Mizrachi.
 */
public class SpriteCollection {
    private java.util.List<Sprite> sprites = new java.util.ArrayList<>();


    /**
     * Gets collection.
     *
     * @return The sprites collection
     */
    public java.util.ArrayList<Sprite> getCollection() {
        return (ArrayList<Sprite>) this.sprites;
    }

    /**
     * Add sprite to collection.
     *
     * @param s The sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Add all sprites.
     *
     * @param sc the sc
     */
    public void addAllSprites(SpriteCollection sc) {
        for (Sprite s : sc.getCollection()) {
            addSprite(s);
        }
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Notify all sprites in the collection that time passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sList = new ArrayList<>(this.getCollection());
        for (Sprite obj : sList) {
            obj.timePassed();
        }
    }

    /**
     * Draw all sprites on given surface.
     *
     * @param d the surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite obj : this.getCollection()) {
            if (obj.getColor() != null) {
                d.setColor(obj.getColor());
            }
            obj.drawOn(d);
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        this.sprites.clear();
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        if (this.sprites.isEmpty()) {
            return true;
        }
        return false;
    }
}