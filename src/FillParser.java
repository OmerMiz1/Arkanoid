import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * The type Fill parser.
 */
public class FillParser {
    /**
     * The constant DEFAULT_FILL_KEY.
     */
    public static final Integer DEFAULT_FILL_KEY = 0;

    /**
     * Is fill boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isFill(String s) {
        if (s.startsWith("fill")) {
            return true;
        }
        return false;
    }

    /**
     * Is image boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isImage(String s) {
        if (s.contains("image")) {
            return true;
        }
        return false;
    }

    /**
     * Gets fill num.
     *
     * @param s the s
     * @return the fill num
     */
//given a couple property-K:value returns the K Integer object val
    public Integer getFillNum(String s) {
        return parseFillNumFromKey(s.split(":")[0]);
    }

    /**
     * Parse fill num from key integer.
     *
     * @param key the key
     * @return the integer
     */
    public Integer parseFillNumFromKey(String key) {
        if (key.startsWith("fill-")) {
            return Integer.parseInt(key.split("-")[1]);
        }
        // indicates default image / color
        return DEFAULT_FILL_KEY;
    }

    /**
     * Parse img image.
     *
     * @param s the s
     * @return the image
     */
//given a couple property:image(PATH) return the PATH Image object
    public Image parseImg(String s) {
        return parseImgFromVal(s.split(":")[1]);
    }

    /**
     * Parse img from val image.
     *
     * @param val the val
     * @return the image
     */
//given a key image(IMAGE_PATH), return new Image object with IMAGE_PATH value
    public Image parseImgFromVal(String val) {
        val = val.replaceFirst("image", "");
        val = val.replaceAll("[()]", "");
        URL url = getClass().getResource("resources/" + val);
        File f = new File(url.getPath());

        try {
            return (ImageIO.read(f));
            //return (ImageIO.read(new File("resources/" + val)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parse col color.
     *
     * @param s the s
     * @return the color
     */
    public Color parseCol(String s) {
        return (new ColorParser().parseColor(s));
    }
}
