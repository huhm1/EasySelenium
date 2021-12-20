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

public class Driver{
    public WebDriver driver;

    /**
     * Default browser, default URL
     */
    public Driver(){
        this.driver();
    }

    /**
     *
     * @param browserType
     * @param strURl
     */
    public Driver(final Browsers browserType, final String strURl){
        this.driver(browserType, strURl);
    }

    /**
     *
     * @param browserType
     */
    public Driver(final Browsers browserType){
        this.driver(browserType, Config.applicationUrl);
    }

    private void driver(final Browsers browserType, final String strURl){
        switch(browserType) {
            case FIREFOX:
                System.setProperty(
                        "webdriver.gecko.driver",
                        System.getProperty("user.dir") + "/driver/geckodriver.exe");
                this.driver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty(
                        "webdriver.chrome.driver",
                        System.getProperty("user.dir") + "/driver/chromedriver.exe");
                this.driver = new ChromeDriver();

                final HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 0);
                chromePrefs.put("multiple-automatic-downloads", 0);
                chromePrefs.put("download.prompt_for_download", false);
                final ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);
                final DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(ChromeOptions.CAPABILITY, options);

                break;
            case EDGE:
                System.setProperty(
                        "webdriver.edge.driver",
                        System.getProperty("user.dir") + "/driver/msedgedriver.exe");
                this.driver = new EdgeDriver();
                break;
            case RemoteFirefox:
                final FirefoxOptions firefoxOptions = new FirefoxOptions();
                try{
                    this.driver = new RemoteWebDriver(new URL(Config.remoteAddress), firefoxOptions);
                }
                catch(final MalformedURLException e){
                    e.printStackTrace();
                }
                break;
            case RemoteChrome:
                final ChromeOptions chromeOptions = new ChromeOptions();
                try{
                    this.driver = new RemoteWebDriver(new URL(Config.remoteAddress), chromeOptions);
                }
                catch(final MalformedURLException e){
                    e.printStackTrace();
                }
                break;
            default:
                throw new InvalidParameterException("Unknown browser type");
        }

        this.driver.get(strURl);
        this.driver.manage().timeouts().implicitlyWait(Config.implicitwait, TimeUnit.SECONDS);
        this.driver.manage().timeouts().setScriptTimeout(Config.explicitwait, TimeUnit.SECONDS);
        this.driver.manage().deleteAllCookies();

    }

    private void driver(){
        this.driver(Config.browser, Config.applicationUrl);
    }

    public enum Browsers{
        CHROME, FIREFOX, EDGE, RemoteFirefox, RemoteChrome,;
    }
}
