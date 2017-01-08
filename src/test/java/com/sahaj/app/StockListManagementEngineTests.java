package com.sahaj.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for StockListManagementEngine
 */
public class StockListManagementEngineTests
        extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StockListManagementEngineTests( String testName )
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

    public void testAddNewSellStockTransaction()
    {
        AvailableStockTransaction.companyToSellTransaction.clear();
        StockListManagementEngine stockListManagementEngine = new StockListManagementEngine();
        assertEquals(0, AvailableStockTransaction.companyToSellTransaction.size());
        stockListManagementEngine.addNewSellStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        assertEquals(1, AvailableStockTransaction.companyToSellTransaction.size());
        stockListManagementEngine.addNewSellStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "ABC")));
        assertEquals(1, AvailableStockTransaction.companyToSellTransaction.size());
        stockListManagementEngine.addNewSellStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.SELL, 10, new Stock(1, "XYZ")));
        assertEquals(2, AvailableStockTransaction.companyToSellTransaction.size());
        assertEquals(2, AvailableStockTransaction.companyToSellTransaction.get("ABC").size());
        assertEquals(1, AvailableStockTransaction.companyToSellTransaction.get("XYZ").size());

    }

    public void testAddNewPurchaseStockTransaction()
    {
        AvailableStockTransaction.companyToPurchaseTransaction.clear();
        StockListManagementEngine stockListManagementEngine = new StockListManagementEngine();
        assertEquals(0, AvailableStockTransaction.companyToPurchaseTransaction.size());
        stockListManagementEngine.addNewPurchaseStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "ABC")));
        assertEquals(1, AvailableStockTransaction.companyToPurchaseTransaction.size());
        stockListManagementEngine.addNewPurchaseStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "ABC")));
        assertEquals(1, AvailableStockTransaction.companyToPurchaseTransaction.size());
        stockListManagementEngine.addNewPurchaseStockTransaction(
                new StockTransaction(StockTransaction.TransactionType.BUY, 10, new Stock(1, "XYZ")));
        assertEquals(2, AvailableStockTransaction.companyToPurchaseTransaction.size());
        assertEquals(2, AvailableStockTransaction.companyToPurchaseTransaction.get("ABC").size());
        assertEquals(1, AvailableStockTransaction.companyToPurchaseTransaction.get("XYZ").size());

    }
}
