package seleniumTest.cases;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import config.Config;
import models.Customer;
import utils.CSV;
import utils.TLog;

public class CSVTest{
    WebDriver driver;

    @BeforeTest

    public void setup(){

        // driver = new Driver().driver;
    }

    @Test(priority = 0)

    public void test1(){
        // Load csv file as string array list
        final List<String[]> Customers = CSV.open("Data/CreateCustomer.csv");
        for(final String[] c : Customers){
            for(final String s : c){
                TLog.pc(s + c[1]);
            }
        }

        // Save string array list as csv file
        final String[] columnNames = "Company Id#Customer name#City#Country#Province#Industry#Admin_E-mail#Tech_e-mail"
                .split("#");
        Customers.add(0, columnNames);
        CSV.save(Config.reportFolder +"savedStringArrayList.csv", Customers);

        // Load csv file as POJO list
        final List<Customer> CustomerList = CSV.open("Data/CreateCustomer.csv", Customer.class);
        for(final Customer c : CustomerList){
            TLog.pc(c.adminEmail + c.city + c.industry);
        }

        // Save POJO list as csv file
        CSV.savePOJO(Config.reportFolder +"savedPOJOList.csv", CustomerList);
    }

}
