package pl.edu.pw.mini.zpoif.currencychartsfullstack.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyBean;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.domain.CurrencyRates;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final NbpApiService nbpApiService;

    public CurrencyController(final NbpApiService nbpApiService) {
        this.nbpApiService = nbpApiService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyBean>> getAllCurrencies() {

        return ResponseEntity.ok(nbpApiService.getAllCurrencies());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyRates> getCurrencyPrice(
            @PathVariable
            String code,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date startDate,
            @RequestParam(name ="endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date endDate) {

        return ResponseEntity.ok(nbpApiService.getCurrencyPrice(code, startDate, endDate));
    }

    @GetMapping("/{code}/ma")
    public ResponseEntity<CurrencyRates> getCurrencyMovingAverage(
            @PathVariable
            String code,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date startDate,
            @RequestParam(name ="endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date endDate,
            @RequestParam(name = "windowSize", required = false, defaultValue = "5")
            int windowSize) {

        return ResponseEntity.ok(nbpApiService.getCurrencyMovingAverage(code, startDate, endDate, windowSize));
    }

    @GetMapping("/{code}/ema")
    public ResponseEntity<CurrencyRates> getCurrencyExponentialMovingAverage(
            @PathVariable
            String code,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date startDate,
            @RequestParam(name ="endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date endDate,
            @RequestParam(name = "alpha", required = false, defaultValue = "0.4")
            double alpha) {

        return ResponseEntity.ok(nbpApiService.getCurrencyExponentialMovingAverage(code, startDate, endDate, alpha));
    }


}
