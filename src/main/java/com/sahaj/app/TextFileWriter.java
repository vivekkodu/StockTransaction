package com.sahaj.app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public class TextFileWriter implements IFileWriter {
    /**
     * It writes the input object to the file in append mode
     * @param transactionList
     * @param fileName
     * @throws IOException
     */
    public void writeObject(List<StockTransaction> transactionList, String fileName) throws IOException{
        FileWriter writer=new FileWriter(fileName, true);
        for (StockTransaction transaction : transactionList){
            writer.write(transaction.toString());
            writer.write("\n");
        }

        writer.close();
    }

    /**
     * It writes header( Or any other data) to the file in write mode.
     * @param fileName Name of the file in which it should write
     * @param header Header which will be written to the file
     * @throws IOException
     */
    public void writeHeader(String fileName, String header) throws IOException{
        FileWriter writer=new FileWriter(fileName);
        writer.write(header);
        writer.close();
    }
}
