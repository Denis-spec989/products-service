package github.denisspec989.productmainservice.jsonpath;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DynamicJson {
    @Test
    public void testBuildingJson() {
        String[] jsonStrings = {
                "$.result.riskMetricData.limitFzhnData.buildings[]",
                "$.result.riskMetricData.limitFzhnData.saleDistrib[]"
        };
        String[] jsonStrings2 = {
                "$.result[]",
                "$.result[].saleDistrib[].year"
        };

        JsonObject jsonResult = new JsonObject();

        for (String jsonString : jsonStrings2) {
            addJsonField(jsonResult, jsonString);
        }

        System.out.println(jsonResult);
    }

    private static void addJsonField(JsonObject jsonResult, String jsonString) {
        // Считаем jsonString разделенными точками
        List<String> elements = Arrays.stream(jsonString.split("\\.")).collect(Collectors.toList());
        elements.remove(0);

        JsonObject currentObject = jsonResult;

        for (String element : elements) {
            if (element.contains("[]")) {
                String arrayKey = element.replace("[]", "");
                if (!currentObject.has(arrayKey)) {
                    currentObject.add(arrayKey, new JsonArray());
                }
                JsonArray jsonArray = currentObject.getAsJsonArray(arrayKey);
                JsonObject newObject = new JsonObject();
                jsonArray.add(newObject);
                currentObject = newObject;
            } else {
                if (!currentObject.has(element)) {
                    currentObject.add(element, new JsonObject());
                }
                JsonElement jsonElement = currentObject.get(element);
                currentObject = jsonElement.getAsJsonObject();
            }
        }
    }
}
