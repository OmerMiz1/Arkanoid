import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Dynamic block.
 */
public class DynamicBlock implements BlockCreator {
    /**
     * The Missing symbol val.
     */
    public static final Exception MISSING_SYMBOL_VAL = new Exception("missing symbol");
    /**
     * The Missing height val.
     */
    public static final Exception MISSING_HEIGHT_VAL = new Exception("missing blockHeight");
    /**
     * The Missing width val.
     */
    public static final Exception MISSING_WIDTH_VAL = new Exception("missing blockWidth");
    /**
     * The Missing hp val.
     */
    public static final Exception MISSING_HP_VAL = new Exception("missing hp");
    /**
     * The Missing fill val.
     */
    public static final Exception MISSING_FILL_VAL = new Exception("missing fill");

    /**
     * The Symbol property.
     */
    public static final String SYMBOL_PROPERTY = BlocksDefinitionReader.SYMBOL_PROPERTY;
    /**
     * The Block width property.
     */
    public static final String BLOCK_WIDTH_PROPERTY = "width";
    /**
     * The Block height property.
     */
    public static final String BLOCK_HEIGHT_PROPERTY = "height";
    /**
     * The Hp property.
     */
    public static final String HP_PROPERTY = BlocksDefinitionReader.HP_PROPERTY;
    /**
     * The Fill property.
     */
    public static final String FILL_PROPERTY = BlocksDefinitionReader.FILL_PROPERTY;
    /**
     * The Stroke property.
     */
    public static final String STROKE_PROPERTY = BlocksDefinitionReader.STROKE_PROPERTY;


    /**
     * The Img.
     */
    private Map<Integer, Image> img = null;
    private String symbol = null;
    private int blockHeight = -1;
    private int blockWidth = -1;
    private int hp = -1;
    private Map<Integer, Color> fillCol = new TreeMap<>();
    private Map<Integer, Image> fillImg = new TreeMap<>();
    private Color stroke = null;

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle rect = new Rectangle(xpos, ypos, this.blockWidth, this.blockHeight);
        return (new Block(rect, this.stroke, this.fillCol, this.fillImg, this.hp));
    }

    /**
     * Sets defaults.
     *
     * @param defVals the def vals
     * @throws Exception the exception
     */
    public void setDefaults(Map<String, String> defVals) throws Exception {
        FillParser fp = new FillParser();
        // check empty because method is iterating on map.
        if (!defVals.isEmpty()) {
            addFillFromDefaults(defVals);
        }

        if (this.symbol == null) {
            if (defVals.get(SYMBOL_PROPERTY) != null) {
                this.symbol = defVals.get(SYMBOL_PROPERTY);
            } else {
                throw MISSING_SYMBOL_VAL;
            }
        }
        if (this.fillCol == null && this.fillImg == null) {
            throw MISSING_FILL_VAL;
        }

        if (this.stroke == null) {
            if (defVals.get(STROKE_PROPERTY) != null) {
                setStroke(fp.parseCol(defVals.get(STROKE_PROPERTY)));
            }
        }

        if (this.blockHeight == -1) {
            if (defVals.get(BLOCK_HEIGHT_PROPERTY) != null) {
                setBlockHeight(Integer.parseInt(defVals.get(BLOCK_HEIGHT_PROPERTY)));
            } else {
                throw MISSING_HEIGHT_VAL;
            }
        }
        if (this.blockWidth == -1) {
            if (defVals.get(BLOCK_WIDTH_PROPERTY) != null) {
                setBlockWidth(Integer.parseInt(defVals.get(BLOCK_WIDTH_PROPERTY)));
            } else {
                throw MISSING_WIDTH_VAL;
            }
        }
        if (this.hp == -1) {
            if (defVals.get(HP_PROPERTY) != null) {
                setHp(Integer.parseInt(defVals.get(HP_PROPERTY)));
            } else {
                throw MISSING_HP_VAL;
            }
        }
    }

    /**
     * Add fill from defaults.
     *
     * @param defVals the def vals
     */
    public void addFillFromDefaults(Map<String, String> defVals) {
        FillParser fp = new FillParser();
        if (defVals.get("fill") == null) {
            return;
        }
        for (String key : defVals.keySet()) {
            if (fp.isFill(key)) {
                if (fp.isImage(defVals.get(key))) {
                    addFillImg(fp.parseFillNumFromKey(key), fp.parseImgFromVal(defVals.get(key)));
                } else {
                    addFillCol(fp.parseFillNumFromKey(key), fp.parseCol(defVals.get(key)));
                }
            }
        }
    }

    /**
     * Add fill img.
     *
     * @param filNum the fil num
     * @param image  the img
     */
    public void addFillImg(Integer filNum, Image image) {
        this.fillImg.put(filNum, image);
    }

    /**
     * Add fill col.
     *
     * @param fillNum   the fill num
     * @param fillColor the fill col
     */
    public void addFillCol(Integer fillNum, Color fillColor) {
        // case fillNum is null - consider color default.
        this.fillCol.put(fillNum, fillColor);
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symb the symbol
     */
    public void setSymbol(String symb) {
        this.symbol = symb;
    }

    /**
     * Gets block height.
     *
     * @return the block height
     */
    public int getBlockHeight() {
        return blockHeight;
    }

    /**
     * Sets block height.
     *
     * @param height the block height
     */
    public void setBlockHeight(int height) {
        this.blockHeight = height;
    }

    /**
     * Gets block width.
     *
     * @return the block width
     */
    public int getBlockWidth() {
        return blockWidth;
    }

    /**
     * Sets block width.
     *
     * @param width the block width
     */
    public void setBlockWidth(int width) {
        this.blockWidth = width;
    }

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets hp.
     *
     * @param hitpoints the hp
     */
    public void setHp(int hitpoints) {
        this.hp = hitpoints;
    }

    /**
     * Gets fill col.
     *
     * @return the fill col
     */
    public Map<Integer, Color> getFillCol() {
        return fillCol;
    }

    /**
     * Gets stroke.
     *
     * @return the stroke
     */
    public Color getStroke() {
        return stroke;
    }

    /**
     * Sets stroke.
     *
     * @param strokeColor the stroke
     */
    public void setStroke(Color strokeColor) {
        this.stroke = strokeColor;
    }

    /**
     * Gets img.
     *
     * @return the img
     */
    public Map<Integer, Image> getImg() {
        return img;
    }

    /**
     * Sets img.
     *
     * @param image the img
     */
    public void setImg(Map<Integer, Image> image) {
        this.img = image;
    }
}
