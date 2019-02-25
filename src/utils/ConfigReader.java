package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader{
    public static Properties prop = null;

    static{
        InputStream input = null;
        if(ConfigReader.prop == null){
            ConfigReader.prop = new Properties();
            try{
                input = new FileInputStream("config.properties");
                ConfigReader.prop.load(input);
            }
            catch(final IOException ex){
                ex.printStackTrace();
            }
            finally{
                if(input != null){
                    try{
                        input.close();
                    }
                    catch(final IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
