package pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators;

import java.util.ArrayList;
import java.util.List;

public class MovingAverageCalculator<T extends Number> implements ICalculator<T> {

    private final List<T> buffer;
    private final int windowSize;
    private int head;
    private int count;

    public MovingAverageCalculator(int windowSize) {
        this.windowSize = windowSize;
        this.buffer = new ArrayList<>(windowSize);
    }

    @Override
    public double calculate(T newValue) {
        add(newValue);
        return getMovingAverage();
    }

    private void add(T value) {
        if (buffer.size() < windowSize) {
            buffer.add(value);
        } else {
            buffer.set(head, value);
        }

        head = (head + 1) % buffer.size();
        if (count < buffer.size()) {
            count++;
        }
    }

    private double getMovingAverage() {
        if (count == 0) {
            return Double.NaN;
        }
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += buffer.get(i).doubleValue();
        }
        return sum / count;
    }
}
