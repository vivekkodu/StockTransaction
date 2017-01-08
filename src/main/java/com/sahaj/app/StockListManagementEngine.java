package com.sahaj.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class StockListManagementEngine {

    public static void addNewSellStockTransaction(StockTransaction stockTransaction){
        List<StockTransaction> companyStockList;
        if(!AvailableStockTransaction.companyToSellTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            AvailableStockTransaction.companyToSellTransaction.put(
                    stockTransaction.getStock().getCompanyName(), new ArrayList<StockTransaction>());
        }

        companyStockList = AvailableStockTransaction.companyToSellTransaction.get(stockTransaction.getStock().getCompanyName());
        companyStockList.add(stockTransaction);
    }

    public static void addNewPurchaseStockTransaction(StockTransaction stockTransaction){
        List<StockTransaction> companyStockList;
        if(!AvailableStockTransaction.companyToPurchaseTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            AvailableStockTransaction.companyToPurchaseTransaction.put(
                    stockTransaction.getStock().getCompanyName(), new ArrayList<StockTransaction>());
        }

        companyStockList = AvailableStockTransaction.companyToPurchaseTransaction.get(stockTransaction.getStock().getCompanyName());
        companyStockList.add(stockTransaction);
    }
}
