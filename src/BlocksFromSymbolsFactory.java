import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new TreeMap<>();
    private Map<String, BlockCreator> blockCreators = new TreeMap<>();

    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Gets a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     *
     * @param s    the block type symbol
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        Block b = this.blockCreators.get(s).create(xpos, ypos);
        b.updateFill();
        return b;
    }

    /**
     * Get space width int.
     *
     * @param s the spacer symbol
     * @return the int
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Add spacer.
     *
     * @param s          the s
     * @param spaceWidth the space width
     */
    public void addSpacer(String s, Integer spaceWidth) {
        this.spacerWidths.put(s, spaceWidth);
    }

    /**
     * Add block creator.
     *
     * @param s       the s
     * @param creator the creator
     */
    public void addBlockCreator(String s, BlockCreator creator) {
        this.blockCreators.put(s, creator);
    }
}