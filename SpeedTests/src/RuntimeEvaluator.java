import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by s-gheldd on 3/26/16.
 */
public class RuntimeEvaluator {
    private static final String WARM_UP_START = "warm up started";
    private static final String WARM_UP_END = "warm up ended";
    private static final String EVALUATION_START = "evaluation stared";
    private static final String EVALUATION_END = "evaluation ended";
    private static final String EVALUATION_ROUND_FORMAT_STRING = "evaluation round %d";
    private static final String WARM_UP_ROUND_FORMAT_STRING = "warm up round %d";

    final private Runnable batchAlgorithm;
    final private int repetitions;
    final private boolean verbose;
    final private TestCase testCase;
    private long[] rawMeasurements = null;

    private void logTime(final String message) {
        final StringBuilder builder = new StringBuilder();
        builder.append(LocalDateTime.now()).append("\t").append(testCase.getName()).append(": ").append(message);
        System.out.println(builder.toString());
    }

    RuntimeEvaluator(final TestCase testCase, final boolean verbose) {
        final Runnable algorithm = testCase.getRunnable();
        this.verbose = verbose;
        this.repetitions = testCase.getRepetitions();
        this.testCase = testCase;
        this.batchAlgorithm = () -> {
            for (int i = 0; i <testCase.getBatchSize(); i++) {
                algorithm.run();
            }
        };
    }

    public long[] evaluate() {

        final long[] meassurements = new long[repetitions];
        if (verbose) {
            logTime(WARM_UP_START);
        }
        for (int i = 0; i < repetitions/10; i++) {
            if (verbose) {
                logTime(String.format(WARM_UP_ROUND_FORMAT_STRING,i));
            }
            batchAlgorithm.run();
        }
        if (verbose) {
            logTime(WARM_UP_END);
        }
        logTime(EVALUATION_START);
        for (int i = 0; i < repetitions; i++) {
            if (verbose) {
                logTime(String.format(EVALUATION_ROUND_FORMAT_STRING,i));
            }
            final long startTime = System.currentTimeMillis();
            batchAlgorithm.run();
            final long endTime = System.currentTimeMillis();
            meassurements[i] = endTime - startTime;
        }
        logTime(EVALUATION_END);
        this.rawMeasurements = meassurements;
        return Arrays.copyOf(rawMeasurements, rawMeasurements.length);
    }

    public Statistics getCleanedMeassurements(final double threshold) {
        if (rawMeasurements == null) {
            this.evaluate();
        }

        double mean = 0.0;
        double m2 = 0.0;
        int n = 0;
        for (final long x : rawMeasurements) {
            n++;
            final double delta = (double)x - mean;
            mean += delta/(double)n;
            m2 += delta * ((double)x-mean);
        }
        if (n < 2){
            return null;
        }

        double sigma = Math.sqrt(m2/(n-1));
        final long[] tmp = new long[rawMeasurements.length];
        int index = 0;
        for (final long x: rawMeasurements) {
            if (Math.abs((double)x-mean) <= threshold*sigma){
                tmp[index] = x;
                index++;
            }
        }

        mean = 0.0;
        m2 = 0.0;
        n = 0;
        for (final long x : tmp) {
            n++;
            final double delta = (double)x - mean;
            mean += delta/(double)n;
            m2 += delta * ((double)x-mean);
        }
        if (n < 2){
            return null;
        }
        sigma = Math.sqrt(m2/(n-1));

        final long[] tmp2 = new long[rawMeasurements.length];
        index = 0;
        for (final long x: rawMeasurements) {
            if (Math.abs((double)x-mean) <= 2*threshold*sigma){
                tmp2[index] = x;
                index++;
            }
        }

        mean = 0.0;
        m2 = 0.0;
        n = 0;
        for (final long x : tmp2) {
            n++;
            final double delta = (double)x - mean;
            mean += delta/(double)n;
            m2 += delta * ((double)x-mean);
        }
        if (n < 2){
            return null;
        }
        sigma = Math.sqrt(m2/(n-1));

        final long[] erg = new long[index];
        System.arraycopy(tmp2,0,erg,0,index);

        return new Statistics(erg,mean,sigma);
    }

    public final static class Statistics {
        final private long[] cleanData;
        final private double mean;
        final private double sigma;

        protected Statistics(final  long[] cleanData, final double mean, final double sigma) {
            this.cleanData = Arrays.copyOf(cleanData, cleanData.length);
            this.mean = mean;
            this.sigma = sigma;
        }

        public long[] getCleanData() {
            return Arrays.copyOf(cleanData, cleanData.length);
        }

        public double getMean() {
            return mean;
        }

        public double getSigma() {
            return sigma;
        }
    }
}
