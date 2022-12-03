package com.ssk.ng.clickhouseclient.ws;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MassQuotes {

    private String symbol;
    private Date settleDate;
    private int exchangeId;
    private String customerId;
    private String sessionId;
    private int pricingProfileId;
    private String instrumentType;
    private boolean deliverable;
    private Date spotDate;
    private double bid;
    private double ask;
    private boolean tfxLarge;
    private double spread;

}
