package pl.edu.pw.mini.zpoif.currencychartsfullstack.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrenciesWrapper;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.excpetions.NbpApiClientException;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiClient;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

@Slf4j
@Service
public class NbpApiClientImpl implements NbpApiClient {

    private final RestTemplate restTemplate;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public NbpApiClientImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CurrenciesWrapper getAllCurrencies() {
        final ParameterizedTypeReference<List<CurrenciesWrapper>> listCurrencies =
                new ParameterizedTypeReference<List<CurrenciesWrapper>>() {};

        try {
            final ResponseEntity<List<CurrenciesWrapper>> response = restTemplate.exchange(
                    "https://api.nbp.pl/api/exchangerates/tables/A/",
                    HttpMethod.GET, null, listCurrencies);

            if (!response.getStatusCode().is2xxSuccessful()) {
                //log.debug(response.getBody().toString());
                throw new NbpApiClientException("Non 200 response from NBP API.");
            }

            return response.getBody().getFirst();
        } catch (RestClientException ex) {
            throw new NbpApiClientException(ex);
        }
    }

    @Override
    public CurrencyRates getCurrencyPrices(String currencyCode, Date startDate, Date endDate) {
        final ParameterizedTypeReference<CurrencyRates> currencyRates =
                new ParameterizedTypeReference<CurrencyRates>() {};

        try {
            final ResponseEntity<CurrencyRates> response = restTemplate.exchange(
                    "https://api.nbp.pl/api/exchangerates/rates/A/{currencyCode}/{startDate}/{endDate}/",
                    HttpMethod.GET, null, currencyRates,
                    currencyCode,
                    dateFormatter.format(startDate),
                    dateFormatter.format(endDate));

            if (!response.getStatusCode().is2xxSuccessful()) {
                //log.debug(response.getBody().toString());
                throw new NbpApiClientException("Non 200 response from NBP API.");
            }

            return response.getBody();
        } catch (RestClientException ex) {
            throw new NbpApiClientException(ex);
        }
    }
}
