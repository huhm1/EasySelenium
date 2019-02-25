package test.cases;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import models.Customer;
import utils.CSV;
import utils.TLog;

public class CSVTest {
	WebDriver driver;

	@BeforeTest

	public void setup() {

		// driver = new Driver().driver;
	}

	@Test(priority = 0)

	public void test1() {
		// Load csv file as string array list
		List<String[]> Customers = CSV.open("Data/CreateCustomer.csv");
		for (String[] c : Customers)
			for (String s : c)
				TLog.pc(s + c[1]);

		// Save string array list as csv file
		String[] columnNames = "Company Id#Customer name#City#Country#Province#Industry#Admin_E-mail#Tech_e-mail"
				.split("#");
		Customers.add(0, columnNames);
		CSV.save("Data/saveStringArrayList.csv", Customers);

		// Load csv file as POJO list
		List<Customer> CustomerList = CSV.open("Data/CreateCustomer.csv", Customer.class);
		for (Customer c : CustomerList)
			TLog.pc(c.adminEmail + c.city + c.industry);

		// Save POJO list as csv file
		CSV.savePOJO("Data/savePOJOList.csv", CustomerList);
	}

}
