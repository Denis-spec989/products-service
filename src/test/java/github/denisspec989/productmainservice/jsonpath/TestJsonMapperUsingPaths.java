package github.denisspec989.productmainservice.jsonpath;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.WriteContext;
import github.denisspec989.productmainservice.DictionaryDto;
import github.denisspec989.productmainservice.JsonMapperUsingPaths;
import github.denisspec989.productmainservice.JsonUtil;
import io.swagger.v3.core.util.Json;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TestJsonMapperUsingPaths {
    @Test
    public void testJsonMapping(){
        String inputJson = "{\n" +
                "  \"result\": {\n" +
                "    \"id_corpus\": \"in sint esse\",\n" +
                "    \"cost_1m\": -1272.006\n" +
                "  }\n" +
                "}";
        //WriteContext writeContext = JsonPath
        //ReadContext readContext = JsonPath.parse(inputJson);
        //System.out.println((String)readContext.read("$.result.id"));

        //DictionaryDto dictionaryDto1 = new DictionaryDto();
        //dictionaryDto1.setCode("id_corpus");
        //dictionaryDto1.setJsonContainer("$.result");
        //dictionaryDto1.setReturnContainers("$.result.riskMetricData.limitFzhnData");
        //dictionaryDto1.setReturnCode("id");




        //DictionaryDto dictionaryDto2 = new DictionaryDto();
        //dictionaryDto2.setCode("cost_1m");
        //dictionaryDto2.setJsonContainer("$.result");
        //dictionaryDto2.setReturnContainers("$.result.riskMetricData.limitFzhnData");
        //dictionaryDto2.setReturnCode("cost1m");
//
        //List<DictionaryDto> dictionaries = new ArrayList<>();
        //dictionaries.add(dictionaryDto1);
        //dictionaries.add(dictionaryDto2);
        //dictionaries = JsonUtil.preparareJsonS7Expression(dictionaries);
        //JsonMapperUsingPaths jsonMapperUsingPaths = new JsonMapperUsingPaths(dictionaries,inputJson);
        //System.out.println(jsonMapperUsingPaths.getUniqueJsonPathsForReturnContainers());
        //System.out.println(jsonMapperUsingPaths.process());




    }
    @Test
    public void replace(){
        String replaceable = "$.result.buildings[*]";

        System.out.println(JsonUtil.processString(replaceable,false));
        //replaceable = replaceable.replaceAll("\\[\\*\\]","");
        //replaceable.replaceAll("$)
        //System.out.println(replaceable);
    }
}
