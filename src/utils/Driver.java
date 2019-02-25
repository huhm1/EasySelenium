package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import config.Config;

public class Driver {
	public WebDriver driver;

	/**
	 * Default browser, default URL
	 */
	public Driver() {
		driver();
	}

	/**
	 * 
	 * @param browserType
	 * @param strURl
	 */
	public Driver(Browsers browserType, String strURl) {
		driver(browserType, strURl);
	}

	/**
	 * 
	 * @param browserType
	 */
	public Driver(Browsers browserType) {
		driver(browserType, Config.applicationUrl);
	}

	private void driver(Browsers browserType, String strURl) {
		switch (browserType) {
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", "../automationTestLib/driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "../automationTestLib/driver/chromedriver.exe");
			driver = new ChromeDriver();

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 0);
			chromePrefs.put("multiple-automatic-downloads", 0);
			chromePrefs.put("download.prompt_for_download", false);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);

			break;
		case EDGE:
			System.setProperty("webdriver.edge.driver", "../automationTestLib/driver/msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		case RemoteFirefox:
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			try {
				driver = new RemoteWebDriver(new URL(Config.remoteAddress), firefoxOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;
		case RemoteChrome:
			ChromeOptions chromeOptions = new ChromeOptions();
			try {
				driver = new RemoteWebDriver(new URL(Config.remoteAddress), chromeOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;
		default:
			throw new InvalidParameterException("Unknown browser type");
		}

		driver.get(strURl);
		driver.manage().timeouts().implicitlyWait(Config.implicitwait, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(Config.explicitwait, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

	}

	private void driver() {
		driver(Config.browser, Config.applicationUrl);
	}

	public enum Browsers {
		CHROME, FIREFOX, EDGE, RemoteFirefox, RemoteChrome,;
	}
}
