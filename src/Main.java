import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main.
 */
public class Main {
    /**
     * The constant LEVEL_SETS_DEFAULT_PATH.
     */
    public static final String LEVEL_SETS_DEFAULT_PATH = "resources/level_sets.txt";


    /**
     * Instantiates a new Main.
     */
    public Main() {
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public void main(String[] args) {
        List<LevelSet> sets = new ArrayList<>();
        AnimationRunner ar = new AnimationRunner();
        KeyboardSensor ks = ar.getGui().getKeyboardSensor();
        GameFlow gf = new GameFlow(ar, ks);
        HighScoresTable hst;
        MenuAnimation<Task<Void>> menu = new MenuAnimation<>(ar, ks, "Arkanoid");
        MenuAnimation<Task<Void>> levelSetsMenu = new MenuAnimation<>(ar, ks, "Level Sets");
        DialogManager dm = ar.getGui().getDialogManager();
        LevelSpecificationReader lsr = new LevelSpecificationReader();
        InputStream is;
        File highscores = new File("highscores");
        if (highscores.exists()) {
            hst = HighScoresTable.loadFromFile(highscores);
        } else {
            hst = new HighScoresTable();
            try {
                hst.save(highscores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sets = loadLevelsSets(args);

        // generate sub menu
        for (LevelSet set : sets) {
            levelSetsMenu.addSelection(set.key(), set.description(), () -> {
                gf.runLevels(set.getLevels());
                updateHighScores(hst, highscores, gf.getScore(), ar);
                EndScreen es = new EndScreen(gf.lastScreenSprites(), gf.getScore(), gf.getLives());
                ar.run(new KeyPressStoppableAnimation(es, ks, "space"));
                ar.run(new KeyPressStoppableAnimation(new HighScoresAnimation(hst), ks, "space"));
                gf.reset();
                return null;
            });
        }
        levelSetsMenu.genSubMenuSprites(sets);

        // generate main menu
        menu.addSubMenu("s", "Start Game", levelSetsMenu);
        menu.addSelection("h", "High Scores", () -> {
            ar.run(new KeyPressStoppableAnimation(new HighScoresAnimation(hst), ks, "space"));
            return null;
        });
        menu.addSelection("q", "Quit", () -> {
            try {
                hst.save(highscores);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ar.closeGame();
                System.exit(0);
            }
            return null;
        });
        menu.genSprites();

        while (true) {
            ar.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.resetStatus();
        }
    }

    /**
     * Load levels sets.
     *
     * @param args the args.
     * @return A list of level sets.
     */
    private List<LevelSet> loadLevelsSets(String[] args) {
        InputStream  setStream;
        Reader setListReader = null, setReader;
        BufferedReader bufReader;
        LineNumberReader lineReader;
        List<LevelSet> sets = new ArrayList<>();
        LevelSpecificationReader levelParser = null;
        String setKey = null, setDescription = null, currLine = null;
        LevelSet currSet;
        String path;
        URL pathUrl;

        if (args.length == 0 || args == null) {
            path = LEVEL_SETS_DEFAULT_PATH;
        } else {
            path = args[0];
        }

        pathUrl = getClass().getResource(path);
        try {
            setListReader = new InputStreamReader(pathUrl.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        lineReader = new LineNumberReader(setListReader);

        // generate the sets at LEVEL_SETS_PATH
        while (true) {
            try {
                currLine = lineReader.readLine();
                if (currLine == null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // odd numbered lines are key:description lines.
            if (lineReader.getLineNumber() % 2 != 0) {
                levelParser = new LevelSpecificationReader();
                setKey = currLine.split(":")[0];
                setDescription = currLine.split(":")[1];
                continue;
            }

            // OTHERWISE: even numbered lines are file paths.
            setStream = ClassLoader.getSystemClassLoader().getResourceAsStream("resources/" + currLine);
            setReader = new InputStreamReader(setStream);
            bufReader = new BufferedReader(setReader);
            currSet = new LevelSet(setKey, setDescription, levelParser.fromReader(bufReader));
            sets.add(currSet);
        }
        return sets;
    }

    /**
     * Update high scores.
     *
     * @param scores     the scores
     * @param scoresPath the scores path
     * @param currScore  the curr score
     * @param ar the animation runner.
     */
    private void updateHighScores(HighScoresTable scores, File scoresPath, int currScore, AnimationRunner ar) {
        DialogManager dm = ar.getGui().getDialogManager();
        boolean shouldSave = false;

        // #1-if: case table is not full, add the score
        if (scores.getHighScores().size() < scores.size()) {
            scores.add(new ScoreInfo(dm.showQuestionDialog("Name", "What is your name?", ""), currScore));
            shouldSave = true;
        } else if (scores.getRank(currScore) <= scores.getHighScores().size()) {
            scores.add(new ScoreInfo(dm.showQuestionDialog("Name", "What is your name?", ""), currScore));
            shouldSave = true;
        }

        if (shouldSave) {
            try {
                scores.save(scoresPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

