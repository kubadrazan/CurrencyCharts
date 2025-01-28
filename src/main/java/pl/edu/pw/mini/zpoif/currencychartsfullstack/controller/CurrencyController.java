package pl.edu.pw.mini.zpoif.currencychartsfullstack.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.mini.zpoif.currencychartsfullstack.services.NbpApiService;

import java.util.Date;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final NbpApiService nbpApiService;

    public CurrencyController(final NbpApiService nbpApiService) {
        this.nbpApiService = nbpApiService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCurrencies() {

        return ResponseEntity.ok(nbpApiService.getAllCurrencies());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getCurrencyPrice(
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
    public ResponseEntity<?> getCurrencyMovingAverage(
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

        if (windowSize <= 0) {
            return ResponseEntity.badRequest().body("400 BadRequest - WindowSize must be greater than 0");
        }

        return ResponseEntity.ok(nbpApiService.getCurrencyMovingAverage(code, startDate, endDate, windowSize));
    }

    @GetMapping("/{code}/ema")
    public ResponseEntity<?> getCurrencyExponentialMovingAverage(
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

        if (alpha <= 0 || alpha > 1) {
            return ResponseEntity.badRequest().body("400 BadRequest - Alpha must be in the range (0, 1]");
        }

        return ResponseEntity.ok(nbpApiService.getCurrencyExponentialMovingAverage(code, startDate, endDate, alpha));
    }

}
