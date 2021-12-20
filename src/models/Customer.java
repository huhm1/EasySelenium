package models;

import com.opencsv.bean.CsvBindByName;

public class Customer{
    @CsvBindByName(column = "CompanyId")
    public String companyId;

    @CsvBindByName(column = "CustomerName")
    public String customerName;

    @CsvBindByName(column = "City")
    public String city;

    @CsvBindByName(column = "Country")
    public String country;

    @CsvBindByName(column = "Province")
    public String province;

    @CsvBindByName(column = "Industry")
    public String industry;

    @CsvBindByName(column = "AdminEmail")
    public String adminEmail;

    @CsvBindByName(column = "TechEmail")
    public String techEmail;

}
