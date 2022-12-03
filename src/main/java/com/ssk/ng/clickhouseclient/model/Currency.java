package com.ssk.ng.clickhouseclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Currency {
    private String symbol;
    private double initialPrice;
    private double chartLowerBound;
    private double chartUpperBound;
}
