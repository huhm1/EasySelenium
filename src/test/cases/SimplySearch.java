package test.cases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import test.pageObjects.google.Search;
import utils.Driver;
import utils.TestBase;

public class SimplySearch extends TestBase {
	WebDriver driver = new Driver().driver;
	Search home = new Search(driver);

	@Test
	public void googleSeach() {
		super.driver = driver;
		home.setSearchTextField("selenium");
	}

}
