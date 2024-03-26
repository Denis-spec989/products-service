package github.denisspec989.productmainservice.jsonpath;

import com.jayway.jsonpath.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.List;

import static github.denisspec989.productmainservice.JsonUtil.processString;

public class JsonPathExample {
    @Test
    public void testJson(){
        // Ваш JSON
        String json = "{\n" +
                "  \"error_code\": 0,\n" +
                "  \"error_message\": \"ut\",\n" +
                "  \"response_schema_version\": 3676,\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id_corpus\": \"in sint esse\",\n" +
                "      \"cost_1m\": -1272.006,\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": 2019,\n" +
                "          \"quarter\": 1,\n" +
                "          \"sale_pct\": 1,\n" +
                "          \"sale_square\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id_corpus\": \"quis amet tempor\",\n" +
                "      \"cost_1m\": 1375,\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": 2018,\n" +
                "          \"quarter\": 3,\n" +
                "          \"sale_pct\": 3,\n" +
                "          \"sale_square\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"year\": 2017,\n" +
                "          \"quarter\": 4,\n" +
                "          \"sale_pct\": 4,\n" +
                "          \"sale_square\": 4\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //Используем библиотеку JsonPath для извлечения пути ко всем элементам массива "sale_distrib"
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String path = "$.result[*].sale_distrib[*]";
        JSONArray saleDistribElements = JsonPath.read(document, path);
//
        //// Выводим результат
        //System.out.println("Путь ко всем элементам массива 'sale_distrib': " + saleDistribElements);


    }
    @Test
    public void testJsonTwo(){
        // Ваш JSON
        String json = "{\n" +
                "  \"result\": {\n" +
                "    \"riskMetricData\": {\n" +
                "      \"limitFzhnData\": {\n" +
                "        \"buildings\": [\n" +
                "          {\n" +
                "            \"saleDistrib\": [\n" +
                "              {\n" +
                "                \"year\": \"2019\",\n" +
                "                \"quarter\": \"1\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"year\": \"2022\",\n" +
                "                \"quarter\": \"2\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"saleDistrib\": [\n" +
                "              {\n" +
                "                \"year\": \"2018\",\n" +
                "                \"quarter\": \"1\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"year\": \"2017\",\n" +
                "                \"quarter\": \"2\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // Используем библиотеку JsonPath для извлечения пути ко всем элементам массива "saleDistrib"
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String path = "$.result.riskMetricData.limitFzhnData.buildings[*].saleDistrib[*]";
        Object saleDistribElements = JsonPath.read(document, path);

        // Выводим результат
        System.out.println("Путь ко всем элементам массива 'saleDistrib': " + saleDistribElements);

    }
    @Test
    public void testJsonThree(){
        // Ваш исходный JSON
        String json = "{\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id_corpus\": \"1\",\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": \"2019\",\n" +
                "          \"quarter\": \"1\",\n" +
                "          \"sale_pct\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"year\": \"2022\",\n" +
                "          \"quarter\": \"2\",\n" +
                "          \"sale_pct\": 2\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id_corpus\": \"2\",\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": \"2018\",\n" +
                "          \"quarter\": \"1\",\n" +
                "          \"sale_pct\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"year\": \"2017\",\n" +
                "          \"quarter\": \"2\",\n" +
                "          \"sale_pct\": 4\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Используем библиотеку JsonPath для обработки JSON
        ReadContext context = JsonPath.parse(json);

        // Создаем новый объект JSON для результирующего JSON
        JSONObject resultJson = new JSONObject();
        JSONArray buildingsArray = new JSONArray();

        JSONArray results = context.read("$.result");
        for (Object result : results) {
            JSONObject building = new JSONObject();
            building.put("id", JsonPath.read(result, "$.id_corpus"));

            JSONArray saleDistribArray = new JSONArray();
            JSONArray saleDistrib = JsonPath.read(result, "$.sale_distrib");
            for (Object sale : saleDistrib) {
                JSONObject saleDistribObj = new JSONObject();
                saleDistribObj.put("year", JsonPath.read(sale, "$.year"));
                saleDistribObj.put("quarter", JsonPath.read(sale, "$.quarter"));
                saleDistribObj.put("salePct", JsonPath.read(sale, "$.sale_pct"));
                saleDistribArray.add(saleDistribObj);
            }

            building.put("saleDistrib", saleDistribArray);
            buildingsArray.add(building);
        }

        resultJson.put("buildings", buildingsArray);

        // Выводим результат
        System.out.println(resultJson.toJSONString());
    }
    @Test
    public void testJsonFour(){
        String json = "{\n" +
                "  \"error_code\": 0,\n" +
                "  \"error_message\": \"ut\",\n" +
                "  \"response_schema_version\": 3676,\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id_corpus\": \"in sint esse\",\n" +
                "      \"cost_1m\": -1272.006,\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": 2019,\n" +
                "          \"quarter\": 1,\n" +
                "          \"sale_pct\": 1,\n" +
                "          \"sale_square\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id_corpus\": \"quis amet tempor\",\n" +
                "      \"cost_1m\": 1375,\n" +
                "      \"sale_distrib\": [\n" +
                "        {\n" +
                "          \"year\": 2018,\n" +
                "          \"quarter\": 3,\n" +
                "          \"sale_pct\": 3,\n" +
                "          \"sale_square\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"year\": 2017,\n" +
                "          \"quarter\": 4,\n" +
                "          \"sale_pct\": 4,\n" +
                "          \"sale_square\": 4\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ReadContext context = JsonPath.parse(json);

        //JSONArray results = context.read("$.result");
        //JsonArray buildingsArray = new JsonArray();
//
        //for (Object result : results) {
        //    JsonObject building = new JsonObject();
        //    building.addProperty("id", (String) JsonPath.read(result,"$.id_corpus"));
        //    building.addProperty("cost1m", (Number) JsonPath.read(result, "$.cost_1m"));
//
        //    JsonArray saleDistribArray = new JsonArray();
        //    JSONArray saleDistrib = JsonPath.read(result, "$.sale_distrib");
        //    for (Object sale : saleDistrib) {
        //        JsonObject saleDistribObj = new JsonObject();
        //        saleDistribObj.addProperty("year", (Number) JsonPath.read(sale, "$.year"));
        //        saleDistribObj.addProperty("quarter", (Number) JsonPath.read(sale, "$.quarter"));
        //        saleDistribObj.addProperty("salePct", (Number) JsonPath.read(sale, "$.sale_pct"));
        //        saleDistribObj.addProperty("saleSquare", (Number) JsonPath.read(sale, "$.sale_square"));
        //        saleDistribArray.add(saleDistribObj);
        //    }
//
        //    building.add("saleDistrib", saleDistribArray);
        //    buildingsArray.add(building);
        //}
        //JsonObject outputJson = new JsonObject();
        //JsonObject resultJson = new JsonObject();
        //JsonObject riskMetricData = new JsonObject();
        //JsonObject limitFzhnData = new JsonObject();
        //limitFzhnData.add("buildings", buildingsArray);
        //riskMetricData.add("limitFzhnData", limitFzhnData);
        //resultJson.add("riskMetricData", riskMetricData);
        //outputJson.add("result",resultJson);
        //System.out.println(new Gson().toJson(outputJson));
    }
    @Test
    public void testJsonFive(){

    }
    @Test
    public void separateString(){
        String s7expression = "$.result.riskMetricData.limitFzhnData.buildings[*]";
        String s7expression2 = "$.result[*].saleDistrib[*]";
        List<String> outputList = processString(s7expression,false);

        for (String element : outputList) {
            System.out.println(element);
        }

    }
    @Test
    public void testS7Expression(){
        String json = "[\n" +
                "  { result:{\n" +
                "    \"name\": \"Alice\",\n" +
                "    \"age\": 30\n" +
                "     }\n" +
                "  },\n" +
                "  {\n" +
                "\n" +
                "result:{\n" +
                "    \"name\": \"Bob\",\n" +
                "    \"age\": 25\n" +
                "  }\n" +
                "}\n" +
                "]";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String path = "$.[*]";
        System.out.println((JSONArray) JsonPath.read(document,path));
        /*
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String path = "$.result.riskMetricData.limitFzhnData.buildings[*].saleDistrib[*]";
        Object saleDistribElements = JsonPath.read(document, path);
         */

    }
    /*
    String[] jsonStrings = {
                "$.result.riskMetricData.limitFzhnData.buildings[]",
                "$.result.riskMetricData.limitFzhnData.buildings[].saleDistrib[]"
        };
        String[] jsonStrings2 = {
                "$.result[]",
                "$.result[].saleDistrib[].year"
        };
     */
    @Data
    private class SaleClass{
        private String year;
        private String quarter;
    }
}
