package pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators;

public class ExponentialMovingAverageCalculator<T extends Number> {
    private final double alpha;
    private Double previousEMA;

    public ExponentialMovingAverageCalculator(double alpha) {
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in the range (0, 1]");
        }
        this.alpha = alpha;
        this.previousEMA = null;
    }

    public Double calculateEMA(T newValue) {
        if (previousEMA == null) {
            previousEMA = newValue.doubleValue();
        } else {
            previousEMA = alpha * newValue.doubleValue()
                    + (1 - alpha) * previousEMA;
        }
        return previousEMA;
    }
}
