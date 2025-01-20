package pl.edu.pw.mini.zpoif.currencychartsfullstack.services;

import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrenciesWrapper;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;

import java.util.Date;

public interface NbpApiClient {

    CurrenciesWrapper getAllCurrencies();

    CurrencyRates getCurrencyPrices(String currencyCode, Date startDate, Date endDate);
}
