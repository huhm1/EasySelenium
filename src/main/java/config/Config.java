package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import utils.ConfigReader;
import utils.Driver.Browsers;

public class Config{

    public static Boolean  printInConsole = true;
    public static Browsers browser        = Browsers.CHROME;
    public static String   server         = null;
    public static String   reportFolder   = System.getProperty("user.dir") + "/TestReport/";
    public static String   projectName    = null;

    public static String   applicationUrl = null;
    public static String   userName       = null;
    public static String   password       = null;
    public static int      implicitwait   = 0;
    public static int      explicitwait   = 0;
    public static int      pageloadtime   = 0;

    public static String   remoteAddress  = null;

    /// initializers Config
    static{

        Config.printInConsole = Boolean.parseBoolean(ConfigReader.prop.getProperty("printInConsole"));
        Config.browser = Browsers.valueOf(ConfigReader.prop.getProperty("browserType"));
        Config.server = ConfigReader.prop.getProperty("server");
        Config.reportFolder = System.getProperty("user.dir") + ConfigReader.prop.getProperty("reportFolder");
        try{
            Files.createDirectories(Paths.get(Config.reportFolder));
        }
        catch(final IOException e){
            e.printStackTrace();
        }
        Config.projectName = ConfigReader.prop.getProperty("projectName");

        Config.applicationUrl = ConfigReader.prop.getProperty("applicationUrl");
        Config.userName = ConfigReader.prop.getProperty("userName");
        Config.password = ConfigReader.prop.getProperty("password");

        Config.implicitwait = Integer.parseInt(ConfigReader.prop.getProperty("implicitwait"));
        Config.explicitwait = Integer.parseInt(ConfigReader.prop.getProperty("explicitwait"));
        Config.pageloadtime = Integer.parseInt(ConfigReader.prop.getProperty("pageloadtime"));

        Config.remoteAddress = ConfigReader.prop.getProperty("remoteAddress");
    }
}
