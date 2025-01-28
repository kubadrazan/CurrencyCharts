package pl.edu.pw.mini.zpoif.currencychartsfullstack.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators.ICalculator;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRates {

    @JsonProperty("code")
    private String code;

    @JsonProperty("currency")
    private String name;

    @JsonProperty("rates")
    private List<Rate> rates;

    public void addRate(Rate rate){
        this.rates.add(rate);
    }

    public void addRates(List<Rate> rates){
        this.rates.addAll(rates);
    }

    public void calculateIndicator(ICalculator<BigDecimal> calculator){
        rates.forEach(rate -> {
            double ma = calculator.calculate(rate.getPrice());
            rate.setPrice(new BigDecimal(ma));
        });
    }
}