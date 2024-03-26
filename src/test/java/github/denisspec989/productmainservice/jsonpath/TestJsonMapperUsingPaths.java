package github.denisspec989.productmainservice.jsonpath;

import github.denisspec989.productmainservice.DictionaryDto;
import github.denisspec989.productmainservice.JsonMapperUsingPaths;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestJsonMapperUsingPaths {
    @Test
    public void testJsonMapping(){
        DictionaryDto dictionaryDto1 = new DictionaryDto();
        dictionaryDto1.setCode("id_corpus");
        dictionaryDto1.setJsonContainer("$.result[]");
        dictionaryDto1.setReturnContainers("$.result.buildings[]");
        dictionaryDto1.setReturnCode("id");

        DictionaryDto dictionaryDto2 = new DictionaryDto();
        dictionaryDto2.setCode("cost_1m");
        dictionaryDto2.setJsonContainer("$.result[]");
        dictionaryDto2.setReturnContainers("$.result.buildings[]");
        dictionaryDto2.setReturnCode("cost1m");

        List<DictionaryDto> dictionaries = new ArrayList<>();
        dictionaries.add(dictionaryDto1);
        dictionaries.add(dictionaryDto2);
        JsonMapperUsingPaths jsonMapperUsingPaths = new JsonMapperUsingPaths(dictionaries);
        System.out.println(jsonMapperUsingPaths.getUniqueJsonPathsForReturnContainers());


    }
}
