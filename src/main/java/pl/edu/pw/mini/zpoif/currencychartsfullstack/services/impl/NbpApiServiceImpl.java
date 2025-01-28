package pl.edu.pw.mini.zpoif.currencychartsfullstack.services.impl;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators.ExponentialMovingAverageCalculator;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.calculators.MovingAverageCalculator;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.exceptions.NbpApiClientException;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiClient;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NbpApiServiceImpl implements NbpApiService {

    private final int API_MAX_DAYS_RANGE = 367;

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
            startDate = DateUtils.getDate(2002, Calendar.JANUARY, 2);
        }

        long dateDiff = DateUtils.DateDiffDays(startDate, endDate);
        if (dateDiff <= API_MAX_DAYS_RANGE) {
            return nbpApiClient.getCurrencyPrices(currencyCode, startDate, endDate);
        }
        else {
            CurrencyRates currencyRates = null;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while (calendar.getTime().before(endDate)) {
                dateDiff = DateUtils.DateDiffDays(startDate, endDate);
                calendar.add(Calendar.DATE, Math.min(API_MAX_DAYS_RANGE - 1, (int)dateDiff));

                try {
                    CurrencyRates response = nbpApiClient.getCurrencyPrices(currencyCode, startDate, calendar.getTime());
                    if (currencyRates == null) {
                        currencyRates = response;
                    }
                    else {
                        currencyRates.addRates(response.getRates());
                    }
                }
                catch (NbpApiClientException ex)
                {
                    String msg = "Error when getting data for this period: ${startDate} - ${endDate}";
                    Logger.getLogger(NbpApiServiceImpl.class.getName()).log(Level.WARNING, msg, ex);
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
        MovingAverageCalculator<BigDecimal> maCalculator = new MovingAverageCalculator<>(windowSize);
        currencyRates.calculateIndicator(maCalculator);
        return currencyRates;
    }

    @Override
    public CurrencyRates getCurrencyExponentialMovingAverage(String currencyCode,
                                                             @Nullable Date startDate,
                                                             @Nullable Date endDate,
                                                             double alpha) {
        CurrencyRates currencyRates = getCurrencyPrice(currencyCode, startDate, endDate);
        ExponentialMovingAverageCalculator<BigDecimal> emaCalculator =
                new ExponentialMovingAverageCalculator<>(alpha);
        currencyRates.calculateIndicator(emaCalculator);
        return currencyRates;
    }
}
