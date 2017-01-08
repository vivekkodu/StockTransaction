
package com.sahaj.app;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Unit test for file reader.
 */
public class ExcelFileStreamReaderTest
        extends TestCase
{
    private static final String INPUT_FILE_NAME = "src\\test\\java\\com\\sahaj\\app\\SahajInput.xls";
    private static final String INVALD_INPUT_FILE_NAME = "src\\test\\java\\com\\sahaj\\app\\SahajInput.xlsx";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExcelFileStreamReaderTest( String testName )
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

    public void testReaderOutput()
    {
        IFileStreamReader fileStreamReader = new ExcelFileStreamReader();
        try {
            List<StockTransaction> stockTransactionList = fileStreamReader.readStream(INPUT_FILE_NAME);
            Iterator<StockTransaction> stockTransactionIterator = stockTransactionList.iterator();
            StockTransaction stockTransaction = stockTransactionIterator.next();
            assertTrue(stockTransaction.getStock().getCompanyName().equals("ABC"));
            assertTrue(stockTransaction.getRemainingStockNumber() == 10);
            assertTrue(stockTransaction.getTransactionType() == StockTransaction.TransactionType.BUY);

            stockTransaction = stockTransactionIterator.next();
            assertTrue(stockTransaction.getStock().getCompanyName().equals("XYZ"));
            assertTrue(stockTransaction.getRemainingStockNumber() == 15);
            assertTrue(stockTransaction.getTransactionType() == StockTransaction.TransactionType.SELL);

            stockTransaction = stockTransactionIterator.next();
            assertTrue(stockTransaction.getStock().getCompanyName().equals("ABC"));
            assertTrue(stockTransaction.getRemainingStockNumber() == 13);
            assertTrue(stockTransaction.getTransactionType() == StockTransaction.TransactionType.SELL);

            stockTransaction = stockTransactionIterator.next();
            assertTrue(stockTransaction.getStock().getCompanyName().equals("XYZ"));
            assertTrue(stockTransaction.getRemainingStockNumber() == 10);
            assertTrue(stockTransaction.getTransactionType() == StockTransaction.TransactionType.BUY);

            stockTransaction = stockTransactionIterator.next();
            assertTrue(stockTransaction.getStock().getCompanyName().equals("XYZ"));
            assertTrue(stockTransaction.getRemainingStockNumber() == 8);
            assertTrue(stockTransaction.getTransactionType() == StockTransaction.TransactionType.BUY);
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testInvalidInputFile(){
        IFileStreamReader fileStreamReader = new ExcelFileStreamReader();
        try{
            List<StockTransaction> stockTransactionList = fileStreamReader.readStream(INVALD_INPUT_FILE_NAME);
            assertTrue(false);
        }catch (IOException exception){
            assertTrue(true);
        }catch (Exception exception){
            assertTrue(false);
        }
    }
}
