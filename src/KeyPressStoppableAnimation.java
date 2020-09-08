import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private boolean stop = false;
    private Animation decorated;
    private String key;
    private KeyboardSensor ks;
    private boolean isAlreadyPressed = true;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param animation the animation
     * @param keyboard  the keyboard
     * @param actionKey the action key
     */
    public KeyPressStoppableAnimation(Animation animation, KeyboardSensor keyboard, String actionKey) {
        this.decorated = animation;
        this.ks = keyboard;
        this.key = actionKey;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.decorated.doOneFrame(d);

        while (this.ks.isPressed(this.key) && this.isAlreadyPressed) {
            continue;
        }
        this.isAlreadyPressed = false;

        if (this.ks.isPressed(this.key)) {
            this.stop = true;
        }
        this.decorated.doOneFrame(d);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
