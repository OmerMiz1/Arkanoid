import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type High scores table.
 */
class HighScoresTable implements Serializable {
    public static final int DEFAULT_SIZE = 6;

    private int size = DEFAULT_SIZE;
    private List<ScoreInfo> highScores = new ArrayList<>();

    /**
     * Read a table from file and return it.
     * <p>
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable hst = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            hst = (HighScoresTable) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hst;
    }

    /**
     * Add a high-score in a sorted way.
     *
     * @param score the new high-score
     */
    public void add(ScoreInfo score) {
        this.highScores.add(score);
        int listSize = this.highScores.size();
        Collections.sort(this.highScores);

        if (getRank(score.getScore()) > size()) {
            this.highScores.remove(score);
        }
    }

    /**
     * Returns the table's size.
     *
     * @return the size.
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores list.
     * <p>
     * The list is sorted such that the highest scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * Sets high scores.
     *
     * @param scores the high scores
     */
    public void setHighScores(List<ScoreInfo> scores) {
        this.highScores = scores;
    }

    /**
     * Return the rank of the current score: where will it be on the list if added?:
     * <p>
     * Rank == 1 means the score will be highest on the list.
     * Rank == `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        if (this.highScores.isEmpty()) {
            return 1;
        }
        for (ScoreInfo si : this.highScores) {
            if (score > si.getScore()) {
                return this.highScores.indexOf(si) + 1;
            }
        }
        // Case of an error or no such score.
        return -1;
    }

    /**
     * Sets size.
     *
     * @param newSize the size
     */
    public void setSize(int newSize) {
        this.size = newSize;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file.
     * <p>
     * Current table data is cleared
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        this.clear();
        HighScoresTable hst = null;
        hst = loadFromFile(filename);
        this.highScores = hst.getHighScores();
        this.size = hst.size();
    }

    /**
     * Save table data to a specific file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        try {
            // clear the file first (if exists) before saving new content;
            if (filename.exists()) {
                filename.delete();
            }
            filename.createNewFile();
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutput oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}