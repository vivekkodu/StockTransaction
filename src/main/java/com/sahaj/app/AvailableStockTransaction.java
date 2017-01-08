package com.sahaj.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class AvailableStockTransaction {
    public static final Map<String, List<StockTransaction>> companyToSellTransaction = new HashMap<String, List<StockTransaction>>();
    public static final Map<String, List<StockTransaction>> companyToPurchaseTransaction = new HashMap<String, List<StockTransaction>>();
}
