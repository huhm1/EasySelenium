package models;

import com.opencsv.bean.CsvBindByName;

public class Customer {
	@CsvBindByName(column = "Company Id")
	public String companyId;

	@CsvBindByName(column = "Customer name")
	public String customerName;

	@CsvBindByName(column = "City")
	public String city;

	@CsvBindByName(column = "Country")
	public String country;

	@CsvBindByName(column = "Province")
	public String province;

	@CsvBindByName(column = "Industry")
	public String industry;

	@CsvBindByName(column = "Admin E-mail")
	public String adminEmail;

	@CsvBindByName(column = "Tech e-mail")
	public String techEmail;

}
