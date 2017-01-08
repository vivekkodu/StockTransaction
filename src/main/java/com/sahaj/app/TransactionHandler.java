package com.sahaj.app;

import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class TransactionHandler {

    /***
     * This method processes all the transactions read from the input stream.
     * @param stockTransactions List of all the transactions
     */
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

    /**
     * It processes a single sell stock transaction. If stocks are already available
     * to be purchased, they will equalize, else it will be added to sell queue.
     * @param stockTransaction Stock transaction details for stock to be sold.
     */
    private void processSingleSellTransactionRequest(StockTransaction stockTransaction){
        if(!AvailableStockTransaction.companyToPurchaseTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            StockListManagementEngine.addNewSellStockTransaction(stockTransaction);
        }

        List<StockTransaction> companyPurchaseTransactionList = AvailableStockTransaction.companyToPurchaseTransaction.get(
                stockTransaction.getStock().getCompanyName());

        if(companyPurchaseTransactionList!=null){
            CompleteTransactionForSingleStock(stockTransaction, companyPurchaseTransactionList);
        }

        if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.OPEN){
            StockListManagementEngine.addNewSellStockTransaction(stockTransaction);
        }
    }

    /**
     * It processes a single buy stock transaction. If stocks are already available
     * to be sold, they will equalize, else it will be added to purchase queue.
     * @param stockTransaction Stock transaction details for stock to be purchased.
     */
    private void processSinglePurchaseTransactionRequest(StockTransaction stockTransaction){
        if(!AvailableStockTransaction.companyToSellTransaction.containsKey(stockTransaction.getStock().getCompanyName())){
            StockListManagementEngine.addNewPurchaseStockTransaction(stockTransaction);
        }

        List<StockTransaction> companySellTransactionList = AvailableStockTransaction.companyToSellTransaction.get(
                stockTransaction.getStock().getCompanyName());

        if(companySellTransactionList!=null){
            CompleteTransactionForSingleStock(stockTransaction, companySellTransactionList);
        }

        if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.OPEN){
            StockListManagementEngine.addNewPurchaseStockTransaction(stockTransaction);
        }
    }

    /**
     * For a single stock transaction, it iterates through the buy/sell transaction list
     * and equalizes with them
     * @param stockTransaction The transaction which is being done
     * @param companyTransactionList The list of transaction of opposite type which are in queue.
     */
    private void CompleteTransactionForSingleStock(StockTransaction stockTransaction, List<StockTransaction> companyTransactionList) {
        while (companyTransactionList.size() > 0) {
            StockTransaction existingTransactionRequest = companyTransactionList.get(0);
            completeSingleTransaction(stockTransaction, existingTransactionRequest);
            if(existingTransactionRequest.getTransactionStatus() == StockTransaction.TransactionStatus.CLOSED){
                companyTransactionList.remove(existingTransactionRequest);
            }else{
                return;
            }

            if(stockTransaction.getTransactionStatus() == StockTransaction.TransactionStatus.CLOSED){
                return;
            }
        }
    }

    /**
     * For the new transaction, proper adjustment is being done against a single stock in the queue.
     * @param currentTransaction Current transaction
     * @param existingTransactionRequest Transaction from the queue
     */
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
