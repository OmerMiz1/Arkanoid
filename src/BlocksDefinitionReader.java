import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {
    /**
     * The constant DEFAULT_DEFINITIONS.
     */
    public static final String DEFAULT_DEFINITIONS = "default";
    /**
     * The constant BLOCK_DEFINITION.
     */
    public static final String BLOCK_DEFINITION = "bdef";
    /**
     * The constant SYMBOL_DEFINITION.
     */
    public static final String SYMBOL_DEFINITION = "sdef";
    /**
     * The constant SYMBOL_PROPERTY.
     */
    public static final String SYMBOL_PROPERTY = "symbol";
    /**
     * The constant WIDTH_PROPERTY.
     */
    public static final String WIDTH_PROPERTY = "width";
    /**
     * The constant HEIGHT_PROPERTY.
     */
    public static final String HEIGHT_PROPERTY = "height";
    /**
     * The constant HP_PROPERTY.
     */
    public static final String HP_PROPERTY = "hit_points";
    /**
     * The constant FILL_PROPERTY.
     */
    public static final String FILL_PROPERTY = "fill";
    /**
     * The constant STROKE_PROPERTY.
     */
    public static final String STROKE_PROPERTY = "stroke";

    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        String currLine = null, definition = null, spacerSymbol = null, key = null, value = null;
        BufferedReader bufReader = new BufferedReader(reader);
        Map<String, String> defaultValues = new TreeMap<>();
        FillParser fp = new FillParser();
        DynamicBlock db;

        while (true) {
            // read until reader is null.
            try {
                currLine = bufReader.readLine();
                if (currLine == null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // case of a blank row or comment row just continue reading.
            if (currLine.length() == 0 || currLine.startsWith("#")) {
                continue;
            } else if (!currLine.toLowerCase().contains("symbol") || !currLine.toLowerCase().contains("image")) {
                currLine = currLine.toLowerCase();
            }

            // first word is the definition name.
            definition = currLine.split(" ")[0];

            if (definition.equals(DEFAULT_DEFINITIONS)) {
                // save map for default values
                defaultValues = getDefValues(currLine.split(" "));
            } else if (definition.equals(BLOCK_DEFINITION)) {
                // instantiate a new block object.
                db = new DynamicBlock();

                // iterate line (current block) properties and add them to the block
                for (String property : currLine.split(" ")) {
                    // skip definition string.
                    if (property.equals(definition)) {
                        continue;
                    }
                    // save key and value to temp vars
                    key = property.split(":")[0];
                    value = property.split(":")[1];

                    // determine property type and update block accordingly
                    if (key.equals(SYMBOL_PROPERTY)) {
                        db.setSymbol(value);
                    } else if (key.equals(HP_PROPERTY)) {
                        db.setHp(Integer.parseInt(value));
                    } else if (fp.isFill(key)) {
                        if (fp.isImage(property)) {
                            db.addFillImg(fp.parseFillNumFromKey(key), fp.parseImgFromVal(value));
                        } else {
                            db.addFillCol(fp.parseFillNumFromKey(key), fp.parseCol(value));
                        }
                    } else if (key.equals(STROKE_PROPERTY)) {
                        db.setStroke(fp.parseCol(value));
                    } else if (key.contains(HEIGHT_PROPERTY)) {
                        db.setBlockHeight(Integer.parseInt(value));
                    } else if (key.contains(WIDTH_PROPERTY)) {
                        db.setBlockWidth(Integer.parseInt(value));
                    }
                }

                // eventually add default values.
                // if a required property is still missing - throw exception.
                try {
                    db.setDefaults(defaultValues);
                    factory.addBlockCreator(db.getSymbol(), db);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (definition.equals(SYMBOL_DEFINITION)) {
                // iterate line's properties
                for (String property : currLine.split(" ")) {
                    // skip the first string which is sdef.
                    if (property.equals(SYMBOL_DEFINITION)) {
                        continue;
                    }

                    // save key and value to temp vars
                    key = property.split(":")[0];
                    value = property.split(":")[1];

                    // save spacer symbol and then add symbol and value to factory.
                    if (key.equals(SYMBOL_PROPERTY)) {
                        spacerSymbol = value;
                    } else if (key.contains(WIDTH_PROPERTY)) {
                        factory.addSpacer(spacerSymbol, Integer.parseInt(value));
                    }
                }
            }
        }
        return factory;
    }

    /**
     * Gets def values.
     *
     * @param values the values
     * @return the def values
     */
    private static Map<String, String> getDefValues(String[] values) {
        // first create a new map with null values.
        Map<String, String> map = new TreeMap<>();
        String[] temp;

        // insert each key:value (property name: property's value)
        for (String property : values) {
            if (property.equals(DEFAULT_DEFINITIONS)) {
                continue;
            }
            temp = property.split(":");

            if (property.contains(SYMBOL_PROPERTY)) {
                map.put(SYMBOL_PROPERTY, temp[1]);
            } else if (property.contains(WIDTH_PROPERTY)) {
                map.put(WIDTH_PROPERTY, temp[1]);
            } else if (property.contains(HEIGHT_PROPERTY)) {
                map.put(HEIGHT_PROPERTY, temp[1]);
            } else if (property.contains(HP_PROPERTY)) {
                map.put(HP_PROPERTY, temp[1]);
            } else if (property.contains(FILL_PROPERTY)) {
                map.put(FILL_PROPERTY, temp[1]);
            } else if (property.contains(STROKE_PROPERTY)) {
                map.put(STROKE_PROPERTY, temp[1]);
            }
            map.put(temp[0], temp[1]);
        }
        // remove first key entered which is irrelevant.
        map.remove(DEFAULT_DEFINITIONS);
        return map;
    }
}
