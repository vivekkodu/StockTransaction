package com.sahaj.app;

import java.io.IOException;
import java.util.List;


public class App 
{
    private static final String OUTPUT_FILE_NAME = "SahajOutput.txt";
    public static void main( String[] args )
    {
        if(args.length == 0){
            System.out.println("Please provide file name as first argument");
            return;
        }

        TransactionHandler transactionHandler = new TransactionHandler();
        IFileWriter fileWriter = new TextFileWriter();
        try{
            List<StockTransaction> stockTransactions = new ExcelFileStreamReader().readStream(args[0]);
            transactionHandler.processStockTransaction(stockTransactions);
            fileWriter.writeHeader(OUTPUT_FILE_NAME, "Stock Id,Side,Company,Quantity\n");
            fileWriter.writeObject(stockTransactions, OUTPUT_FILE_NAME);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
