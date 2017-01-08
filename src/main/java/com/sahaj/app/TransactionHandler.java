package com.sahaj.app;

import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class TransactionHandler {

    void processStockTransaction(List<StockTransaction> stockTransactions){
        if(!Validator.ValidateNotNull(stockTransactions)){
            throw new IllegalArgumentException("Input cannot be null");
        }

        for (StockTransaction transaction: stockTransactions) {
            if(transaction.getTransactionType() == StockTransaction.TransactionType.BUY){
                processSinglePurchaseTransactionRequest(transaction);
            }else{
                processSingleSellTransactionRequest(transaction);
            }
        }

    }

    private void processSingleSellTransactionRequest(StockTransaction stockTransaction){
        if(!AvailableStockTransaction.companyToPurchaseTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            StockListManagementEngine.addNewSellStockTransaction(stockTransaction);
        }

        List<StockTransaction> companyPurchaseTransactionList = AvailableStockTransaction.companyToPurchaseTransaction.get(
                stockTransaction.getStock().getCompanyName());

        completeSingleTransaction(stockTransaction, companyPurchaseTransactionList);

        if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.OPEN){
            StockListManagementEngine.addNewSellStockTransaction(stockTransaction);
        }
    }

    private void processSinglePurchaseTransactionRequest(StockTransaction stockTransaction){
        if(!AvailableStockTransaction.companyToSellTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            StockListManagementEngine.addNewPurchaseStockTransaction(stockTransaction);
        }

        List<StockTransaction> companySellTransactionList = AvailableStockTransaction.companyToSellTransaction.get(
                stockTransaction.getStock().getCompanyName());

        completeSingleTransaction(stockTransaction, companySellTransactionList);

        if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.OPEN){
            StockListManagementEngine.addNewPurchaseStockTransaction(stockTransaction);
        }
    }

    private void completeSingleTransaction(StockTransaction stockTransaction, List<StockTransaction> companyTransactionList) {
        for (StockTransaction existingTransactionRequest : companyTransactionList){
            completeSingleTransaction(stockTransaction, existingTransactionRequest);
            if(existingTransactionRequest.getTransactionStatus() == StockTransaction.TransactionStatus.CLOSED){
                companyTransactionList.remove(existingTransactionRequest);
            }

            if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.CLOSED){
                return;
            }
        }
    }

    private void completeSingleTransaction(StockTransaction currentTransaction, StockTransaction existingTransactionRequest){
        int processedStock = 0;
        if(currentTransaction.getRemainingStockNumber() >= existingTransactionRequest.getRemainingStockNumber()){
            processedStock = existingTransactionRequest.getRemainingStockNumber();
        }else{
            processedStock = currentTransaction.getRemainingStockNumber();
        }

        existingTransactionRequest.updateStockCount(processedStock);
        currentTransaction.updateStockCount(processedStock);
    }
}
