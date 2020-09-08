/**
 * The interface Block creator.
 */
public interface BlockCreator {
    /**
     * Create block.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    Block create(int xpos, int ypos);
}
