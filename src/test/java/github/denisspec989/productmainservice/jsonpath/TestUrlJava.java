package github.denisspec989.productmainservice.jsonpath;

import github.denisspec989.productmainservice.JsonConteinerDto;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TestUrlJava {
    private HashMap<String, JsonConteinerDto> jsonConteinersMap = new HashMap<>();
    private JSONObject root = new JSONObject();
    @Test
    public void testUrl(){

        JSONObject saleDistrib = new JSONObject();
        saleDistrib.put("year",2019);
        root.put("saleDistrib",saleDistrib);
        JsonConteinerDto jsonConteinerDto = new JsonConteinerDto();
        jsonConteinerDto.setJsonObject(saleDistrib);
        jsonConteinersMap.put("saleDistrib",jsonConteinerDto);
        System.out.println("v1 " + root.toJSONString());
        System.out.println("v1 " +jsonConteinersMap.get("saleDistrib").getJsonObject().toJSONString());
        saleDistrib.put("year","2020");
        JSONArray riskMetricDataArray = new JSONArray();
        JSONObject riskMetricDataJsonObject = new JSONObject();
        riskMetricDataJsonObject.put("kasha","nasha");
        riskMetricDataArray.add(riskMetricDataJsonObject);
        saleDistrib.put("riskMetricData",riskMetricDataArray);
        System.out.println("v2 " + root.toJSONString());
        System.out.println("v2 " +jsonConteinersMap.get("saleDistrib").getJsonObject().toJSONString());

    }


}
