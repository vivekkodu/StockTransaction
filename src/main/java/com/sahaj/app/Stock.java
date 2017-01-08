package com.sahaj.app;

/**
 * This class defines the structure of a single stock.
 */
public class Stock {

    private int stockId;
    private String companyName;

    public Stock(int stockId, String companyName){
        this.stockId = stockId;
        this.companyName = companyName;
    }

    public int getStockId() {
        return stockId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
