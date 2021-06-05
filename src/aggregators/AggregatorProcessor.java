package aggregators;

import java.util.List;

import fileprocessors.StockFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AggregatorProcessor<T extends Aggregator> {
	
	private T aggregator;
	private String fileName;
	
	public AggregatorProcessor(T aggregator, String fileName) {
		this.aggregator = aggregator;
		this.fileName = fileName;
	}
	
	public double runAggregator(int column) throws FileNotFoundException, IOException {
		double value = 0.0;
		column--;
		StockFileReader fr = new StockFileReader(fileName);
		List<String> dataList = fr.readFileData();
		for (String singleLine : dataList) {
			String[] cols = singleLine.split(",");
			List<String> values = new ArrayList<String>();
			values = Arrays.asList(cols[column]);
			for (String innerValue : values) {
				aggregator.add(Double.parseDouble(innerValue));
			}
		}
		value = aggregator.calculate();

		return value;
	}
	
}
