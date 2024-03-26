package github.denisspec989.productmainservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<JsonRow> parseJson(String jsonString) {
        DocumentContext jsonContext = JsonPath.parse(jsonString);
        return extractJsonRows("", jsonContext.json());
    }

    private static List<JsonRow> extractJsonRows(String currentPath, Object jsonValue) {
        List<JsonRow> result = new ArrayList<>();

        if (jsonValue instanceof List) {
            List<Object> jsonArray = (List<Object>) jsonValue;
            for (int i = 0; i < jsonArray.size(); i++) {
                result.addAll(extractJsonRows(currentPath + "[" + i + "]", jsonArray.get(i)));
            }
        } else if (jsonValue instanceof  java.util.LinkedHashMap) {
            java.util.LinkedHashMap<String, Object> jsonObj = (java.util.LinkedHashMap<String, Object>) jsonValue;
            for (String key : jsonObj.keySet()) {
                result.addAll(extractJsonRows(currentPath + "." + key, jsonObj.get(key)));
            }
        } else {
            result.add(new JsonRow(currentPath, jsonValue));
        }

        return result;
    }

    private static List<JsonRow> applyFilter(List<JsonRow> jsonRows, List<String> filterPaths) {
        List<JsonRow> filteredRows = new ArrayList<>();

        for (JsonRow row : jsonRows) {
            if (filterPaths.stream().noneMatch(row.jsonPath::contains)) {
                filteredRows.add(row);
            }
        }

        return filteredRows;
    }



    private static boolean matchesFilter(String jsonPath, String filter) {
            String[] jsonPathParts = jsonPath.split("\\.");
            String[] filterParts = filter.split("\\.");

            if (jsonPathParts.length != filterParts.length) {
                    return false;
                }

            for (int i = 0; i < jsonPathParts.length; i++) {
                jsonPathParts[i]=removeSquareBrackets(jsonPathParts[i]);
                filterParts[i]=removeSquareBrackets(filterParts[i]);
                    if (!jsonPathParts[i].equals(filterParts[i])) {
                            return false;
                        }
                }

            return true;
        }
    private static boolean matchesAnyFilter(String jsonPath, List<String> filters) {
            for (String filter : filters) {
                    if (matchesFilter(jsonPath, filter)) {
                            return true;
                        }
                }
            return false;
        }
    public static List<JsonRow> filterJsonRows(List<JsonRow> jsonRows, List<String> filters) {
            List<JsonRow> filteredRows = new ArrayList<>();

            for (JsonRow row : jsonRows) {
                    if (matchesAnyFilter(row.jsonPath, filters)) {
                            filteredRows.add(row);
                        }
                }

            return filteredRows;
        }
    public static String removeSquareBrackets(String input) {
        StringBuilder sb = new StringBuilder();
        boolean insideBrackets = false;

        for (char c : input.toCharArray()) {
            if (c == '[') {
                insideBrackets = true;
            }
            if (!insideBrackets) {
                sb.append(c);
            }
            if (c == ']') {
                insideBrackets = false;
            }
        }

        return sb.toString();
    }
}
