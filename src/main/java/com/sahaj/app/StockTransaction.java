package com.sahaj.app;

import java.security.InvalidParameterException;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class StockTransaction {
    public enum TransactionStatus {
        OPEN("Open"), CLOSED("Closed");

        TransactionStatus(String status){
            this.status = status;
        }
        private String status;
        public String getStatus(){
            return this.status;
        }
    }

    public enum TransactionType {
        BUY("Buy"), SELL("Sell");

        TransactionType(String type){
            this.type = type;
        }
        private String type;
        public String getType(){
            return this.type;
        }
    }

    static final String COMMA_SEPARATOR = ",";

    private TransactionType transactionType;

    //
    // In case of BUY type, this will represent total number of stocks which needs
    // to be purchased. In case of SELL type, it represents total number of stocks
    // which needs to be sold.
    //
    private int totalStockNumber;

    //
    // In case of BUY, it represents more stocks to be purchased. In case of SELL,
    // it represents more stocks to be sold.
    //
    private int remainingStockNumber;
    private TransactionStatus transactionStatus = TransactionStatus.OPEN;
    private Stock stock;

    public StockTransaction(TransactionType type, int totalStockNumber, Stock stock) {
        if(Validator.ValidateIsNegative(totalStockNumber)){
            throw new InvalidParameterException("TotalStockNumber cannot be negative");
        }

        this.transactionType = type;
        this.totalStockNumber = totalStockNumber;
        this.stock = stock;
        this.remainingStockNumber = totalStockNumber;
    }

    public void updateStockCount(int processedStock) throws IllegalArgumentException{
        if(processedStock > this.remainingStockNumber){
            throw new IllegalArgumentException("Processed stock cannot exceed remaining stock count");
        }

        this.remainingStockNumber -= processedStock;

        if(this.remainingStockNumber == 0){
            this.transactionStatus = TransactionStatus.CLOSED;
        }
    }

    @Override
    public String toString(){
        return this.stock.getStockId() + COMMA_SEPARATOR + this.transactionType.getType() + COMMA_SEPARATOR
                + this.stock.getCompanyName() + COMMA_SEPARATOR + this.totalStockNumber + COMMA_SEPARATOR
                + this.remainingStockNumber + COMMA_SEPARATOR + this.transactionStatus.getStatus();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public int getRemainingStockNumber() {
        return remainingStockNumber;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public Stock getStock() {
        return stock;
    }

}
