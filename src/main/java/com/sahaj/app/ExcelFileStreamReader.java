package com.sahaj.app;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class ExcelFileStreamReader implements IFileStreamReader {

    /**
     * This method creates a list for each entry in the input file. Any invalid entry
     * in the file will be ignored.
     * @param fileName Name of the file which is taken as input
     * @return List of all stock transaction in ordered fashion
     * @throws IOException
     */
    public List<StockTransaction> readStream(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator rowIterator = sheet.rowIterator();
        List<StockTransaction> transactionList = new ArrayList<StockTransaction>();

        if(rowIterator.hasNext()){
            // ignoring the headers
            rowIterator.next();
        }

        while(rowIterator.hasNext()){
            HSSFRow row = (HSSFRow) rowIterator.next();
            Iterator cellIterator = row.cellIterator();
            try{
                HSSFCell cell = (HSSFCell) cellIterator.next();
                int stockId = (int)cell.getNumericCellValue();
                String transactionType = ((HSSFCell) cellIterator.next()).getRichStringCellValue().getString();
                String companyName = ((HSSFCell) cellIterator.next()).getRichStringCellValue().getString();
                int quantity = (int)((HSSFCell) cellIterator.next()).getNumericCellValue();

                if(transactionType.equalsIgnoreCase("Buy")){
                    transactionList.add(new StockTransaction(StockTransaction.TransactionType.BUY, quantity, new Stock(stockId, companyName)));
                }else{
                    transactionList.add(new StockTransaction(StockTransaction.TransactionType.SELL, quantity, new Stock(stockId, companyName)));
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return transactionList;
    }
}
