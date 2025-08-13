package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.Product;
import java.io.File;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Product readProduct(String filePath, String scenarioKey) {
        try {
            JsonNode root = mapper.readTree(new File(filePath));
            JsonNode node = root.path(scenarioKey); // "positive" or "negative"
            return mapper.treeToValue(node, Product.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Product from JSON", e);
        }
    }

    public static int readId(String filePath, String scenarioKey) {
        try {
            JsonNode root = mapper.readTree(new File(filePath));
            return root.path(scenarioKey).asInt();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read int from JSON", e);
        }
    }

    public static String readName(String filePath, String scenarioKey) {
        try {
            JsonNode root = mapper.readTree(new File(filePath));
            return root.path(scenarioKey).asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read String from JSON", e);
        }
    }

}
