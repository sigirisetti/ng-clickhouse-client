package com.ssk.ng.clickhouseclient.ws;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MassQuotes {

    private String symbol;
    private Date tradeDate;
    private Date spotDate;
    private Date settleDate;
    private int exchangeId;
    private String customerId;
    private String sessionId;
    private String stream;
    private double bid;
    private double ask;
    private double spread;
}
