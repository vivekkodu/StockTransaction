package com.sahaj.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for main method.
 */
public class TransactionHandlerTests
        extends TestCase
{
    private static final String INPUT_FILE_NAME = "src\\test\\java\\com\\sahaj\\app\\SahajInput.xls";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TransactionHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testProcessStockTransaction()
    {
        AvailableStockTransaction.companyToSellTransaction.clear();
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        List<StockTransaction> stockTransactions = new ArrayList<StockTransaction>();
        TransactionHandler handler = new TransactionHandler();
        handler.processStockTransaction(stockTransactions);
        // Empty input list test
        assertEquals(0, AvailableStockTransaction.companyToPurchaseTransaction.size());
        assertEquals(0, AvailableStockTransaction.companyToSellTransaction.size());

        // Test when purchase is more than sell
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 12, new Stock(1, "ABC")));
        handler.processStockTransaction(stockTransactions);
        assertEquals(0, AvailableStockTransaction.companyToSellTransaction.size());
        assertEquals(1, AvailableStockTransaction.companyToPurchaseTransaction.size());
        assertEquals(8, AvailableStockTransaction.companyToPurchaseTransaction.get("ABC").get(0).getRemainingStockNumber());
        assertEquals(0, stockTransactions.get(0).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(0).getTransactionStatus());
        assertEquals(8, stockTransactions.get(1).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(1).getTransactionStatus());
        assertEquals(0, stockTransactions.get(2).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(2).getTransactionStatus());

        // Test when sell is more than purchase
        AvailableStockTransaction.companyToSellTransaction.clear();
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        stockTransactions = new ArrayList<StockTransaction>();
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 12, new Stock(1, "ABC")));
        handler.processStockTransaction(stockTransactions);
        assertEquals(0, AvailableStockTransaction.companyToPurchaseTransaction.size());
        assertEquals(1, AvailableStockTransaction.companyToSellTransaction.size());
        assertEquals(8, AvailableStockTransaction.companyToSellTransaction.get("ABC").get(0).getRemainingStockNumber());
        assertEquals(0, stockTransactions.get(0).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(0).getTransactionStatus());
        assertEquals(8, stockTransactions.get(1).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(1).getTransactionStatus());
        assertEquals(0, stockTransactions.get(2).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(2).getTransactionStatus());

        // Test when purchase and sell is done in mixed order
        AvailableStockTransaction.companyToSellTransaction.clear();
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        stockTransactions = new ArrayList<StockTransaction>();
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 12, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        handler.processStockTransaction(stockTransactions);
        assertEquals(8, AvailableStockTransaction.companyToSellTransaction.get("ABC").get(0).getRemainingStockNumber());
        assertEquals(0, AvailableStockTransaction.companyToPurchaseTransaction.get("ABC").size());
        assertEquals(1, AvailableStockTransaction.companyToPurchaseTransaction.size());
        assertEquals(1, AvailableStockTransaction.companyToSellTransaction.size());
        assertEquals(0, stockTransactions.get(0).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(0).getTransactionStatus());
        assertEquals(0, stockTransactions.get(1).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(1).getTransactionStatus());
        assertEquals(8, stockTransactions.get(2).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(2).getTransactionStatus());

        // Test when only purchase/sell is done for a specific company
        AvailableStockTransaction.companyToSellTransaction.clear();
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        stockTransactions = new ArrayList<StockTransaction>();
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 12, new Stock(1, "XYZ")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        handler.processStockTransaction(stockTransactions);
        assertEquals(10, AvailableStockTransaction.companyToSellTransaction.get("ABC").get(0).getRemainingStockNumber());
        assertEquals(12, AvailableStockTransaction.companyToPurchaseTransaction.get("XYZ").get(0).getRemainingStockNumber());
        assertEquals(10, AvailableStockTransaction.companyToSellTransaction.get("ABC").get(1).getRemainingStockNumber());
        assertEquals(10, stockTransactions.get(0).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(0).getTransactionStatus());
        assertEquals(12, stockTransactions.get(1).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(1).getTransactionStatus());
        assertEquals(10, stockTransactions.get(2).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(2).getTransactionStatus());

        // Test of sample which was given in input file
        AvailableStockTransaction.companyToSellTransaction.clear();
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        stockTransactions = new ArrayList<StockTransaction>();
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 15, new Stock(1, "XYZ")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.SELL, 13, new Stock(1, "ABC")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "XYZ")));
        stockTransactions.add(new StockTransaction(StockTransaction.TransactionType.BUY, 8, new Stock(1, "XYZ")));
        handler.processStockTransaction(stockTransactions);
        assertEquals(0, stockTransactions.get(0).getRemainingStockNumber());
        assertEquals(0, stockTransactions.get(1).getRemainingStockNumber());
        assertEquals(3, stockTransactions.get(2).getRemainingStockNumber());
        assertEquals(0, stockTransactions.get(3).getRemainingStockNumber());
        assertEquals(3, stockTransactions.get(4).getRemainingStockNumber());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(0).getTransactionStatus());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(1).getTransactionStatus());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(2).getTransactionStatus());
        assertEquals(StockTransaction.TransactionStatus.CLOSED, stockTransactions.get(3).getTransactionStatus());
        assertEquals(StockTransaction.TransactionStatus.OPEN, stockTransactions.get(4).getTransactionStatus());
    }
}
