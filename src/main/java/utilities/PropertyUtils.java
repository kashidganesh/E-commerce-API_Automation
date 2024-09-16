package utilities;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    private static final Properties properties = new Properties();
    public static final String CONFIG_PROPERTY_FILE_PATH = "src/main/resources/config.properties";

    static {
        FileInputStream configFile = null;
        try {
            configFile = new FileInputStream(CONFIG_PROPERTY_FILE_PATH);
            properties.load(configFile);
        } catch (IOException e) {
            System.err.println("Failed to load configuration properties: " + e.getMessage());
        } finally {
            if (configFile != null) {
                try {
                    configFile.close();
                } catch (IOException e) {
                    e.printStackTrace(); // In practice, use logging framework rather than e.printStackTrace()
                }
            }
        }
    }

    public static String getProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue == null) {
            throw new IllegalArgumentException("Property " + propertyName + " not found in the configuration file.");
        }
        return propertyValue;
    }
}

