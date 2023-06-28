package ru.isshepelev.userbankapi.userWalletLogic.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.isshepelev.userbankapi.CBbankApi.CurrencyApiHandler;

import java.io.IOException;

@RestController
public class APIController {
    CurrencyApiHandler response = new CurrencyApiHandler();

    @GetMapping("/date")
    public String getDate() throws IOException {
        return response.getDate();
    }
    @GetMapping("/valute")
    public String valute() throws IOException {
        return response.currencyList().toString();
    }
}
