package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CSV {
	/**
	 * Load CSV file by file path and file name, return an list of strings.
	 * 
	 * @param file
	 *            A string of csv file path.
	 * @return A list of String[]. If can't find csv file or the csv is empty, it
	 *         will return null.
	 */
	static public List<String[]> open(String file) {
		CSVReader reader = null;
		List<String[]> content = null;
		try {
			reader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(1).build();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		try {
			try {
				content = reader.readAll();
			} catch (CsvException e) {
				e.printStackTrace();
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Load CSV file by file path and file name, return a list of given POJO
	 * 
	 * @param file
	 *            A string of csv file path.
	 * @param pojo
	 *            The content of csv will return as this POJO type.
	 * @return A list of given pojo object. If can't find csv file or the csv is
	 *         empty, it will return null.
	 */
	static public <T> List<T> open(String file, Class<? extends T> pojo) {

		try (Reader reader = Files.newBufferedReader(Paths.get(file));) {
			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader).withType(pojo).withIgnoreLeadingWhiteSpace(true)
					.build();

			Iterator<T> ContentIterator = csvToBean.iterator();

			List<T> listOfObject = new ArrayList<T>();

			if (!ContentIterator.hasNext())
				return null;

			while (ContentIterator.hasNext()) {
				listOfObject.add(ContentIterator.next());
			}
			return listOfObject;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Save a List<String[]> into a given csv file. The first row is the column
	 * names. you can create the first row as String[] columnNames =
	 * "first#second#third".split("#"); list.add(columnNames);
	 * 
	 * @param file
	 *            A string of csv file path.
	 * @param list
	 *            A list of String[]. You will save into a given csv file.
	 */
	static public void save(String file, List<String[]> list) {
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String[] c : list)
			writer.writeNext(c);

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save POJO into a given csv file.
	 * 
	 * @param <T>
	 * 
	 * @param file
	 *            A string of csv file path.
	 * @param list
	 *            A list of String[]. You will save into a given csv file.
	 */

	static public <T> void savePOJO(String file, List<T> list) {
		// list.getClass(list(0));
		Writer writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StatefulBeanToCsv<T> beanWriter = new StatefulBeanToCsvBuilder<T>(writer).build();

		try {
			beanWriter.write(list);
		} catch (CsvDataTypeMismatchException e1) {
			e1.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e1) {
			e1.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
