package pl.edu.pw.mini.zpoif.currencychartsfullstack.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiClient;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;

import java.util.Date;
import java.util.List;

@Service
public class NbpApiServiceImpl implements NbpApiService {

    private final NbpApiClient nbpApiClient;

    public NbpApiServiceImpl(final NbpApiClient nbpApiClient) {
        this.nbpApiClient = nbpApiClient;
    }

    @Override
    public List<CurrencyBean> getAllCurrencies() {
        return nbpApiClient.getAllCurrencies().getRates();
    }

    @Override
    public CurrencyRates getCurrencyPrices(String currencyCode, Date startDate, Date endDate) {
        return nbpApiClient.getCurrencyPrices(currencyCode, startDate, endDate);
    }
}
