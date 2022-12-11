package com.ssk.ng.clickhouseclient.controller;

import com.ssk.ng.clickhouseclient.model.Currency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static  com.ssk.ng.clickhouseclient.Constants.*;

@RestController
public class GuiStaticData {
    @GetMapping("/forwardCcyPairs")
    public List<Currency> getForwardCcyPairs() {
        List<Currency> currencies = new ArrayList<>();
        for(int i = 0; i< FWD_CCY_PAIRS.length; i++) {
            double p = initialPrices[i];
            double l = p * 0.8;
            double u = p * 1.2;
            currencies.add(new Currency(FWD_CCY_PAIRS[i], p, l, u));
        }
        return currencies;
    }
}
