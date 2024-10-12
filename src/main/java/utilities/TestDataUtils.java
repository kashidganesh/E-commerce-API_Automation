package utilities;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class TestDataUtils {

    private static final String TEST_DATA_JSON_PATH = "src/test/resources/testData.json";
    private static JSONObject testData;

    static {
        try {
            testData = new JSONObject(new JSONTokener(new FileInputStream(TEST_DATA_JSON_PATH)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Test data file not found.", e);
        }
    }

    public static JSONObject getTestData(String scenarioKey) {
        return testData.getJSONObject("testData").getJSONObject(scenarioKey);
    }
}
