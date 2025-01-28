package pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators;

public interface ICalculator<T extends Number> {

    public double calculate(T newValue);
}
