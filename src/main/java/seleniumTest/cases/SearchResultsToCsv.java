package seleniumTest.cases;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import config.Config;
import models.SearchKeyWord;
import seleniumTest.pageObjects.google.Search;
import utils.CSV;
import utils.Driver;
import utils.TestBase;

public class SearchResultsToCsv extends TestBase {
	WebDriver driver = new Driver().driver;
	Search search = new Search(this.driver);

	/**
	 * Use search keyword which stored in SearchKeyWords.csv, search at goole.com.
	 * And store the search results in csv files.
	 */
	@Test
	public void googleSeach() {
		super.driver = this.driver;
		// Load csv file as POJO list
		final List<SearchKeyWord> KeyWordList = CSV.open("Data/SearchKeyWords.csv", SearchKeyWord.class);
		int resultSize = 0;

		final String[] columnNames = "Title#URL#Description".split("#");
		String resultTitle, resultUrl, resultDescription;
		for (final SearchKeyWord s : KeyWordList) {
			final List<String[]> searchResults = new ArrayList<>();
			searchResults.add(0, columnNames);

			this.search.setSearchTextField(s.keyWord);
			resultSize = this.search.getResultsList().size();
			for (int i = 0; i < resultSize; i++) {
				resultTitle = this.search.getResultTitle(i);
				resultUrl = this.search.getResultUrl(i);
				resultDescription = this.search.getResultDescription(i);

				searchResults.add(new String[] { resultTitle, resultUrl, resultDescription });
			}

			CSV.save(Config.reportFolder + s.keyWord + ".csv", searchResults);

		}

	}

}
