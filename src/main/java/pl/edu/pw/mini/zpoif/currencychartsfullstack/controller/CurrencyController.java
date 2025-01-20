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

    @GetMapping("/hello")
    public String hello() {
        return "Full Stack Java Development with Spring Boot & Vue.js";
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyBean>> getAllCurrencies() {

        return ResponseEntity.ok(nbpApiService.getAllCurrencies());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CurrencyRates> getAllCurrencies(
            @PathVariable
            String code,
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date startDate,
            @RequestParam(name ="endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Date endDate) {

        return ResponseEntity.ok(nbpApiService.getCurrencyPrices(code, startDate, endDate));
    }
}
