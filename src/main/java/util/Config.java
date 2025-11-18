package util;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties prop = new Properties();

    static{
        try(
                InputStream input =Config.class.getClassLoader().getResourceAsStream("db.properties")){
            prop.load(input);
        }catch(Exception ex){
            System.out.println("Error al cargar db.properties");
        }
    }
    public static String get(String key){
        return prop.getProperty(key);
    }
}
