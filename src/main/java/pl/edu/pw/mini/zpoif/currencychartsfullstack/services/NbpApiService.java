package pl.edu.pw.mini.zpoif.currencychartsfullstack.services;

import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;

import java.util.Date;
import java.util.List;

public interface NbpApiService {

    List<CurrencyBean> getAllCurrencies();

    CurrencyRates getCurrencyPrices(String currencyCode, Date startDate, Date endDate);

}
