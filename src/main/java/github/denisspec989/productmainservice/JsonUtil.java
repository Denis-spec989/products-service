package github.denisspec989.productmainservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    //если [] то JSONArray, если . то JSONObject
    private static final String s7VariableSymbol = "$.";
    public static List<String> processString(String inputString, Boolean needReplaceArraysSymbol) {
        inputString = inputString.replaceAll("\\[]","\\[*]");
        if(needReplaceArraysSymbol){
            inputString = inputString.replaceAll("\\[\\*\\]","");
        }
        List<String> outputList = new ArrayList<>();
        List<String> elements = Arrays.stream(inputString.split("\\.")).collect(Collectors.toList());
        elements.forEach(element->{
            //element = s7VariableSymbol.concat(element);
            outputList.add(element);
        });

        return outputList;
    }
    public static List<DictionaryDto> preparareJsonS7Expression(List<DictionaryDto> dictionaryDtos){
        List<DictionaryDto> outputList = new ArrayList<>();
        dictionaryDtos.stream().forEach(dictionaryDto -> {
            dictionaryDto.setJsonContainer(dictionaryDto.getJsonContainer().replaceAll("\\[]","[*]"));
            dictionaryDto.setReturnContainers(dictionaryDto.getReturnContainers().replaceAll("\\[]","[*]"));
            outputList.add(dictionaryDto);
        });
        return outputList;
    }

}
