package pl.edu.pw.mini.zpoif.currencychartsfullstack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @GetMapping("/hello")
    public String hello() {
        return "Full Stack Java Development with Spring Boot & Vue.js";
    }
}
