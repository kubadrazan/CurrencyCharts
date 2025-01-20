package pl.edu.pw.mini.zpoif.currencychartsfullstack.services.impl;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators.MovingAverageCalculator;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiClient;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NbpApiServiceImpl implements NbpApiService {

    private final int MAX_DAYS_RANGE = 367;

    private final NbpApiClient nbpApiClient;

    public NbpApiServiceImpl(final NbpApiClient nbpApiClient) {
        this.nbpApiClient = nbpApiClient;
    }

    @Override
    public List<CurrencyBean> getAllCurrencies() {
        return nbpApiClient.getAllCurrencies().getRates();
    }

    @Override
    public CurrencyRates getCurrencyPrice(String currencyCode,
                                          @Nullable Date startDate,
                                          @Nullable Date endDate) {
        // Default values for dates
        if (endDate == null) {
            endDate = Calendar.getInstance().getTime();
        }

        if (startDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2002);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 2);
            startDate = calendar.getTime();
        }

        long diffInMsec = Math.abs(startDate.getTime() - endDate.getTime());
        long dateDiff = TimeUnit.DAYS.convert(diffInMsec, TimeUnit.MILLISECONDS);
        if (dateDiff <= MAX_DAYS_RANGE) {
            return nbpApiClient.getCurrencyPrices(currencyCode, startDate, endDate);
        }
        else {
            CurrencyRates currencyRates = null;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while (calendar.getTime().before(endDate)) {
                diffInMsec = Math.abs(startDate.getTime() - endDate.getTime());
                dateDiff = TimeUnit.DAYS.convert(diffInMsec, TimeUnit.MILLISECONDS);
                calendar.add(Calendar.DATE, Math.min(MAX_DAYS_RANGE - 1, (int)dateDiff));

                CurrencyRates response = nbpApiClient.getCurrencyPrices(currencyCode, startDate, calendar.getTime());
                if (currencyRates == null) {
                    currencyRates = response;
                }
                else {
                    currencyRates.addRates(response.getRates());
                }

                calendar.add(Calendar.DATE, 1);
                startDate = calendar.getTime();
            }
            return currencyRates;
        }
    }

    @Override
    public CurrencyRates getCurrencyMovingAverage(String currencyCode,
                                                  @Nullable Date startDate,
                                                  @Nullable Date endDate,
                                                  int windowSize) {
        CurrencyRates currencyRates = getCurrencyPrice(currencyCode, startDate, endDate);
        MovingAverageCalculator<BigDecimal> movingAverageCalculator = new MovingAverageCalculator<>(windowSize);
        currencyRates.getRates().forEach(currencyBean -> {
            movingAverageCalculator.add(currencyBean.getPrice());
            currencyBean.setPrice(new BigDecimal(movingAverageCalculator.getMovingAverage()));
        });
        return currencyRates;
    }
}
