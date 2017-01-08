package com.sahaj.app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class TextFileWriter implements IFileWriter {
    public void writeObject(List<StockTransaction> transactionList, String fileName) throws IOException{
        FileWriter writer=new FileWriter(fileName);
        for (StockTransaction transaction : transactionList){
            writer.write(transaction.toString());
            writer.write("\n");
        }

        writer.close();
    }
}
