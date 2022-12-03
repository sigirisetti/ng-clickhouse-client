package com.ssk.ng.clickhouseclient.ws.com.ssk.ng.guimock.rest;

import com.ssk.ng.clickhouseclient.model.Currency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static  com.ssk.ng.clickhouseclient.Constants.*;

@RestController
public class GuiStaticData {

    @GetMapping("/tfxCurrencies")
    public List<Currency> getTfxCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        for(int i=0;i<TFX_CURRENCIES.length;i++) {
            double p = initialPrices[i];
            double l = p * 0.8;
            double u = p * 1.2;
            currencies.add(new Currency(TFX_CURRENCIES[i], p, l, u));
        }
        return currencies;

    }

    @GetMapping("/tfxLargeCurrencies")
    public void getTfxLargeCurrencies() {

    }
}
