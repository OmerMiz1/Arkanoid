import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    /**
     * The Start level definition.
     */
    public static final String START_LEVEL_DEFINITION = "START_LEVEL";
    /**
     * The End level definition.
     */
    public static final String END_LEVEL_DEFINITION = "END_LEVEL";
    /**
     * The Level name property.
     */
    public static final String LEVEL_NAME_PROPERTY = "level_name";
    /**
     * The Balls velocities property.
     */
    public static final String BALLS_VELOCITIES_PROPERTY = "ball_velocities";
    /**
     * The Background property.
     */
    public static final String BACKGROUND_PROPERTY = "background";
    /**
     * The Paddle width property.
     */
    public static final String PADDLE_WIDTH_PROPERTY = "paddle_width";
    /**
     * The Paddle speed property.
     */
    public static final String PADDLE_SPEED_PROPERTY = "paddle_speed";
    /**
     * The Row height property.
     */
    public static final String ROW_HEIGHT_PROPERTY = "row_height";
    /**
     * The Blocks definition path.
     */
    public static final String BLOCKS_DEFINITION_PATH = "block_definitions";
    /**
     * The Blocks start x property.
     */
    public static final String BLOCKS_START_X_PROPERTY = "blocks_start_x";
    /**
     * The Block start y property.
     */
    public static final String BLOCK_START_Y_PROPERTY = "blocks_start_y";
    /**
     * The Num of blocks property.
     */
    public static final String NUM_OF_BLOCKS_PROPERTY = "num_blocks";
    /**
     * The Blocks start definition.
     */
    public static final String BLOCKS_START_DEFINITION = "START_BLOCKS";
    /**
     * The Blocks end definition.
     */
    public static final String BLOCKS_END_DEFINITION = "END_BLOCKS";


    private Map<String, Task<Void>> tasksCfg = null;
    private List<LevelInformation> levels = new ArrayList<>();
    private BlocksFromSymbolsFactory factory = null;
    private DynamicLevel currLvl = null;
    private boolean generatingBlocks = false;
    private String line = null;
    private int currRow = -1;

    /**
     * Instantiates a new Level specification reader.
     */
    public LevelSpecificationReader() {
        initTasksConfig();
    }

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        BufferedReader bufReader = new BufferedReader(reader);

        while (true) {
            // try reading next line, if null end of method - return levels.
            try {
                this.line = bufReader.readLine();
                if (this.line == null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // BLOCKS GENERATING MODE: case blocks generating task started
            if (this.generatingBlocks) {
                parseBlocksLayout(this.currRow);
                this.currRow++;
                continue;
            }

            // LEVEL PROPERTY READING MODE: check if line start with valid definition name.
            if (this.tasksCfg.containsKey(propertyKey())) {
                // run the definition's task
                this.tasksCfg.get(propertyKey()).run();
            }
        }
        return this.levels;
    }

    /**
     * Init tasks config.
     */
    private void initTasksConfig() {
        this.tasksCfg = new TreeMap<>();
        this.tasksCfg.put(START_LEVEL_DEFINITION, () -> {
            this.currLvl = new DynamicLevel();
            return null;
        });
        this.tasksCfg.put(LEVEL_NAME_PROPERTY, () -> {
            this.currLvl.setTitle(propertyValue());
            return null;
        });
        this.tasksCfg.put(BALLS_VELOCITIES_PROPERTY, () -> {
            this.line = this.line.toLowerCase();
            String[] speedsAndAngles = propertyValue().split(" ");
            int speed, angle;
            for (String property : speedsAndAngles) {
                speed = Integer.parseInt(property.split(",")[0]);
                angle = Integer.parseInt(property.split(",")[1]);
                this.currLvl.addBallSpeed(speed);
                this.currLvl.addBallVelocity((Velocity.fromAngleAndSpeed(angle, speed)));
            }
            return null;
        });
        this.tasksCfg.put(BACKGROUND_PROPERTY, () -> {
            this.currLvl.setBackground(this.line);
            return null;
        });
        this.tasksCfg.put(PADDLE_SPEED_PROPERTY, () -> {
            int speed = Integer.parseInt(propertyValue());
            this.currLvl.setPaddleSpeed(speed);
            return null;
        });
        this.tasksCfg.put(PADDLE_WIDTH_PROPERTY, () -> {
            int width = Integer.parseInt(propertyValue());
            this.currLvl.setPaddleWidth(width);
            return null;
        });
        this.tasksCfg.put(BLOCKS_DEFINITION_PATH, () -> {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("resources/" + propertyValue());
            InputStreamReader reader = new InputStreamReader(is);
            this.factory = BlocksDefinitionReader.fromReader(reader);
            return null;
        });
        this.tasksCfg.put(BLOCKS_START_X_PROPERTY, () -> {
            int cord = Integer.parseInt(propertyValue());
            this.currLvl.setBlockStartX(cord);
            return null;
        });
        this.tasksCfg.put(BLOCK_START_Y_PROPERTY, () -> {
            int cord = Integer.parseInt(propertyValue());
            this.currLvl.setBlockStartY(cord);
            return null;
        });
        this.tasksCfg.put(ROW_HEIGHT_PROPERTY, () -> {
            int height = Integer.parseInt(propertyValue());
            this.currLvl.setRowHei(height);
            return null;
        });
        this.tasksCfg.put(NUM_OF_BLOCKS_PROPERTY, () -> {
            int bNum = Integer.parseInt(propertyValue());
            this.currLvl.setBlocksNum(bNum);
            return null;
        });
        this.tasksCfg.put(BLOCKS_START_DEFINITION, () -> {
            this.generatingBlocks = true;
            this.currRow = 0;
            return null;
        });
        this.tasksCfg.put(BLOCKS_END_DEFINITION, () -> {
            this.generatingBlocks = false;
            this.currRow = -1;
            return null;
        });
        this.tasksCfg.put(END_LEVEL_DEFINITION, () -> {
            this.levels.add(this.currLvl);
            this.currLvl = null;
            return null;
        });
    }

    /**
     * Parse blocks layout.
     *
     * @param rowIndex the row index
     */
    private void parseBlocksLayout(int rowIndex) {
        String[] chars;
        int currX, currY;

        // check if didnt reach end of blocks definition.
        if (this.tasksCfg.containsKey(this.line)) {
            this.tasksCfg.get(this.line).run();
            return;
        }

        // split line to strings of chars
        // NOTE FOR currY : rowIndex == 0 is the first row.
        chars = this.line.split("");
        currX = this.currLvl.getBlockStartX();
        currY = this.currLvl.getBlockStartY() - (this.currLvl.getRowHei() * rowIndex);

        // iterate each char and produce the appropriate blocks and spacers.
        for (String c : chars) {
            if (this.factory.isSpaceSymbol(c)) {
                currX += this.factory.getSpaceWidth(c);
            } else if (this.factory.isBlockSymbol(c)) {
                Block b = this.factory.getBlock(c, currX, currY);
                this.currLvl.addBlock(b);
                currX += b.getWidth();
            }
        }
    }

    /**
     * Property value string.
     *
     * @return the string
     */
    private String propertyValue() {
        String value = this.line.split(":")[1];
        return this.line.split(":")[1];
    }

    /**
     * Property key string.
     *
     * @return the string
     */
    private String propertyKey() {
        return this.line.split(":")[0];
    }
}