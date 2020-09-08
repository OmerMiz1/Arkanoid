import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Color parser.
 */
public class ColorParser {
    /**
     * Colors map map.
     *
     * @return the map
     */
    public Map<String, Color> colorsMap() {
        Map<String, Color> colors = new TreeMap<>();
        colors.put("black", Color.BLACK);
        colors.put("blue", Color.BLUE);
        colors.put("cyan", Color.CYAN);
        colors.put("gray", Color.GRAY);
        colors.put("lightgray", Color.LIGHT_GRAY);
        colors.put("green", Color.GREEN);
        colors.put("orange", Color.ORANGE);
        colors.put("pink", Color.PINK);
        colors.put("red", Color.RED);
        colors.put("white", Color.WHITE);
        colors.put("yellow", Color.YELLOW);
        return colors;
    }

    /**
     * Parse color color.
     *
     * @param s the s
     * @return the color
     */
    public Color parseColor(String s) {
        String[] rgb;
        s = s.toLowerCase();
        if (s.contains("color")) {
            s = s.replaceAll("color", "").replaceAll("[()]", "");
        }
        if (s.contains("rgb")) {
            s = s.replaceAll("rgb", "");
        }
        if (colorsMap().containsKey(s)) {
            return colorsMap().get(s);
        }
        rgb = s.split(",");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

}
