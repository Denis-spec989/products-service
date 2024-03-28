package github.denisspec989.productmainservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String fillPlaceholders(String inputString, String targetString) {
        StringBuilder result = new StringBuilder();
        int inputIndex = 0;
        int targetIndex = 0;

        while (inputIndex < inputString.length() && targetIndex < targetString.length()) {
            if (inputString.charAt(inputIndex) == '[' && targetString.charAt(targetIndex) == '[') {
                // Находим значение внутри квадратных скобок в inputString
                StringBuilder value = new StringBuilder();
                inputIndex++;
                while (inputString.charAt(inputIndex) != ']') {
                    value.append(inputString.charAt(inputIndex));
                    inputIndex++;
                }

                // Вставляем найденное значение в targetString
                result.append(targetString, targetIndex, targetString.indexOf('[', targetIndex));
                result.append("[");
                result.append(value);
                result.append("]");

                // Продвигаем индексы к следующим позициям после найденных скобок
                inputIndex++;
                targetIndex = targetString.indexOf('[', targetIndex) + 1;
            } else {
                result.append(targetString.charAt(targetIndex));
                targetIndex++;
            }
        }

        // Добавляем оставшуюся часть targetString, если она еще не добавлена
        result.append(targetString.substring(targetIndex));

        return result.toString();
    }
    public static String fillString(String inputStr, String outputStr) {
        String[] inputValues = extractValues(inputStr);
        String[] outputValues = extractValues(outputStr);

        for (int i = 0; i < inputValues.length; i++) {
            outputStr = outputStr.replaceFirst("\\[\\]", "[" + inputValues[i] + "]");
        }

        return outputStr;
    }

    private static String[] extractValues(String str) {
        List<String> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            values.add(matcher.group(1));
        }

        return values.toArray(new String[0]);
    }

}
