package pl.edu.pw.mini.zpoif.currencychartsfullstack.services;

import jakarta.annotation.Nullable;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;

import java.util.Date;
import java.util.List;

public interface NbpApiService {

    List<CurrencyBean> getAllCurrencies();

    CurrencyRates getCurrencyPrice(String currencyCode,
                                   @Nullable Date startDate,
                                   @Nullable Date endDate);


    CurrencyRates getCurrencyMovingAverage(String currencyCode,
                                    @Nullable Date startDate,
                                    @Nullable Date endDate,
                                           int windowSize);


    CurrencyRates getCurrencyExponentialMovingAverage(String currencyCode,
                                           @Nullable Date startDate,
                                           @Nullable Date endDate,
                                           double alpha);

}
