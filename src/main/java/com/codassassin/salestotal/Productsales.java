package com.codassassin.salestotal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class Productsales {
    Dictionary sales = new Hashtable();
    long revenue = 0L;
    String line = "";
    String splitBy = ",";

    public List<Hashtable> aggregateProductSales(Long productId) {
        List<Hashtable> salesList = new ArrayList<>();
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("CSVDemo.csv"));
            String[] itemList = line.split(splitBy);
            LocalDate salesDate1 = LocalDate.parse(itemList[4]);
            Month month1;
            Month month = salesDate1.getMonth();
            while(br.readLine() != null) {

                String[] item = line.split(splitBy);
                String date = item[4];
                salesDate1 = LocalDate.parse(date);
                month1 = salesDate1.getMonth();
                if(item[1].equals(productId)) {
                    if(month != month1) {
                        salesList.add((Hashtable) sales.put(month1, revenue));
                        revenue = 0L;
                    }
                    revenue += Long.parseLong(item[3]);
                }
                month = month1;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return salesList;
    }
}