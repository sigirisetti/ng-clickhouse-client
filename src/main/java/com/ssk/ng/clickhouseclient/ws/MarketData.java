package com.ssk.ng.clickhouseclient.ws;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketData {

    private String symbol;
    private Date settleDate;
    private double marketBid;
    private double marketAsk;

}
