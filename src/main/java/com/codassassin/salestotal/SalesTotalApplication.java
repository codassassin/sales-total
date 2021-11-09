package com.codassassin.salestotal;

import com.opencsv.CSVWriter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@SpringBootApplication
public class SalesTotalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesTotalApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService,
								  final TaskService taskService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
//				runtimeService.startProcessInstanceByKey("formTask1");
//				runtimeService.startProcessInstanceByKey("serviceTask2");
//				runtimeService.startProcessInstanceByKey("serviceTask3");
//				runtimeService.startProcessInstanceByKey("serviceTask4");
			}
		};
	}

}


class Validation {
	String line = "";
	String splitBy = ",";
	String[] items = new String[]{ "SNo", "product_id", "product_name", "price_per_unit", "sale_date" };

	public boolean validateFile() {

		System.out.println("Uploading and validate..");
//		try {
//			//parsing a CSV file into BufferedReader class constructor
//			BufferedReader br = new BufferedReader(new FileReader("CSVDemo.csv"));
//			String[] itemList = line.split(splitBy);
//			for(int i=0; i<items.length; i++) {
//				if(!Objects.equals(itemList[i], items[i])) return false;
//			}
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		return true;
	}
}


class ExtractProducts {
	String line = "";
	String splitBy = ",";
	List<String> list = new ArrayList<>();
	public List<String> extractProductList() {
		try {
			//parsing a CSV file into BufferedReader class constructor
			BufferedReader br = new BufferedReader(new FileReader("CSVDemo.csv"));
			String[] itemList = line.split(splitBy);
			while(br.readLine() != null) {
				String[] item = line.split(splitBy);
				list.add(item[1]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}


class ProductSales {
	Dictionary sales = new Hashtable();
	long revenue = 0L;
	String line = "";
	String splitBy = ",";

	public List<Hashtable> aggregateProductSales(long productId) {
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


class ReportGeneration {
	public ProductSales productSales;

	List<Hashtable> salesData = new ArrayList<>();

	public void generate(long productId, long revenue) {
		File file = new File("report_for_product_" + productId + ".csv");
		salesData = productSales.aggregateProductSales(productId);

		try {
			FileWriter outputFile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputFile, '|',
					CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			List<String[]> data = new ArrayList<String[]>();

			data.add(new String[] {"month", "revenue"});
			for(int i = 0; salesData.get(i) != null; i++) {
				Enumeration k = salesData.get(i).keys();
				data.add(new String[] {(String)k.nextElement(), (String)salesData.get(i).get(k.nextElement())});
			}
			writer.writeAll(data);

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}