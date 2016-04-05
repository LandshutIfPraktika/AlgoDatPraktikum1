/**
 * Created by s-gheldd on 3/27/16.
 */
public class TestCase {
    final private String name;
    final private Runnable runnable;
    final private int batchSize;
    final private int repetitions;

    public TestCase(String name, Runnable runnable, int batchSize, int repetitions) {
        this.name = name;
        this.runnable = runnable;
        this.batchSize = batchSize;
        this.repetitions = repetitions;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public String getName() {
        return name;
    }
}
