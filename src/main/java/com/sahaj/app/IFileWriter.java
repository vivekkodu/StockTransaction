package com.sahaj.app;

import java.io.IOException;
import java.util.List;

/**
 * Created by VIVEK VERMA on 1/8/2017.
 */
public interface IFileWriter {
    void writeObject(List<StockTransaction> transactionList, String fileName)  throws IOException;
}
