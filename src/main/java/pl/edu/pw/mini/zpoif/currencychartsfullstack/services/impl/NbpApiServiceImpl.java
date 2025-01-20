package pl.edu.pw.mini.zpoif.currencychartsfullstack.services.impl;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiClient;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;

import java.util.Calendar;
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
    public CurrencyRates getCurrencyPrices(String currencyCode,
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

        return nbpApiClient.getCurrencyPrices(currencyCode, startDate, endDate);
    }
}
