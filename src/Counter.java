/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Instantiates a new Counter.
     *
     * @param countVal the count value
     */
    public Counter(int countVal) {
        this.count = countVal;
    }

    /**
     * Instantiates a new Counter with default value of 0.
     */
    public Counter() {
        this(0);
    }

    /**
     * Increase.
     *
     * @param number the number
     */
// add number to current count.
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
// subtract number from current count.
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
// get current count.
    public int getValue() {
        return this.count;
    }

    /**
     * Clear.
     */
    public void clear() {
        this.count = 0;
    }
}