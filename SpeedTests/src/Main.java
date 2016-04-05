import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by s-gheldd on 3/26/16.
 */
public class Main {
    final static int SIGMA_THRESHOLD = 1;
    final static int BATCH_SIZE = 1;
    final static int REPETITIONS = 20;
    final static boolean VERBOSE = true;

    private static TestCase ZERO_TO_MAX_INT_INTEGER = new TestCase("ZERO_TO_MAX_INT_INTEGER", new Runnable() {
        @Override
        public void run() {
            int i = 0;
            final int end = Integer.MAX_VALUE;
            while (++i < end) {
            }
        }
    }, BATCH_SIZE, REPETITIONS);

    private static TestCase ZERO_TO_MAX_INT_LONG = new TestCase("ZERO_TO_MAX_INT_LONG", new Runnable() {
        @Override
        public void run() {
            long i = 0;
            final long end = Integer.MAX_VALUE;
            while (++i < end) {
            }
        }
    }, BATCH_SIZE, REPETITIONS);

    private static TestCase ZERO_TO_MAX_LONG = new TestCase("ZERO_TO_MAX_LONG", new Runnable() {
        @Override
        public void run() {
            long i = 0;
            final long end = Long.MAX_VALUE ;
            while (++i < end) {
            }
        }
    }, BATCH_SIZE, REPETITIONS);

    private static TestCase MAX_INT_TO_MAX_LONG = new TestCase("MAX_INT_TO_MAX_LONG", new Runnable() {
        @Override
        public void run() {
            long i = Integer.MAX_VALUE;
            final long end = Long.MAX_VALUE;
            while (++i < end) {
            }
        }
    }, BATCH_SIZE, REPETITIONS);

    public static void main(String[] args) {
        final List<TestCase> testCases = new LinkedList<>();
        Collections.addAll(testCases, ZERO_TO_MAX_INT_INTEGER, ZERO_TO_MAX_INT_LONG, ZERO_TO_MAX_LONG, MAX_INT_TO_MAX_LONG);
/*
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
*/

/*
        new RuntimeEvaluator(ZERO_TO_MAX_INT_INTEGER).evaluate();
        System.out.println(Arrays.toString(new RuntimeEvaluator(ZERO_TO_MAX_INT_LONG).evaluate()));
*/


        testCases.stream().map(s -> {
            final StringBuilder builder = new StringBuilder();
            builder.append("Test name: ").append(s.getName()).append("\n");
            final RuntimeEvaluator evaluator = new RuntimeEvaluator(s,VERBOSE);
            evaluator.evaluate();
            final RuntimeEvaluator.Statistics statistics = evaluator.getCleanedMeassurements(SIGMA_THRESHOLD);
            builder.append("Mean: ").append(statistics.getMean()).append(", Sigma: ").append(statistics.getSigma()).append("\n");
            return builder.toString();
        }).forEach(System.out::println);
    }
}
