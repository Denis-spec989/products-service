package github.denisspec989.productmainservice;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class JsonMapperUsingPaths {
    private List<DictionaryDto> dictionaryData;
    private List<String> uniqueJsonPathsForReturnContainers;
    private HashMap<String,JsonConteinerDto> jsonContainers = new HashMap<>();
    //private final String ROOT = "root";
    private JsonConteinerDto root = new JsonConteinerDto();
    private final ReadContext inputJsonContext;
    public JsonMapperUsingPaths(List<DictionaryDto> dictionaryData,String inputJson){
        if(dictionaryData.isEmpty()){
            throw new IllegalArgumentException("Empty data list");
        }
        this.dictionaryData = dictionaryData;
        uniqueJsonPathsForReturnContainers = dictionaryData.stream().map(DictionaryDto::getReturnContainers).distinct().collect(Collectors.toList());
        if(uniqueJsonPathsForReturnContainers.get(0).contains("$.[*]")){
            root.createArrayContainer(new JSONArray());
        }else {
            root.createObjectContainer(new JSONObject());
        }
        inputJsonContext = JsonPath.parse(inputJson);
    }

    public String process(){
        for(String jsonPath:uniqueJsonPathsForReturnContainers){
            List<String> splittedJsonPath = JsonUtil.processString(jsonPath,false);
            if(!jsonPath.contains("[*]")){
                for(int i=1;i<splittedJsonPath.size();i++){
                    String s7LocalVar = splittedJsonPath.get(i);
                    JsonConteinerDto jsonConteinerDto = jsonContainers.get(s7LocalVar);
                    JSONObject jsonObject;
                    if(jsonConteinerDto==null){
                        jsonObject = new JSONObject();
                        jsonConteinerDto = jsonContainers.put(s7LocalVar,new JsonConteinerDto().createObjectContainer(jsonObject));
                    }else {
                        jsonObject = jsonConteinerDto.getJsonObject();
                    }
                    if(i==1){
                        root.getJsonObject().put(s7LocalVar,jsonObject);
                    }else {
                        String s7localVariablePrevious = splittedJsonPath.get(i-1);
                        if(s7localVariablePrevious.contains("[*]")){
                            s7localVariablePrevious = s7localVariablePrevious.replaceAll("\\[\\*\\]","");
                            jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonObject);
                        }else {
                            jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalVar,jsonObject);
                        }
                    }
                    if((i+1)==splittedJsonPath.size()){
                        //конец заполняем данные
                        List<DictionaryDto> filteredDictionariesFieldToBeWriteHere = dictionaryData.stream().filter(dictionaryDto -> dictionaryDto.getReturnContainers().equals(jsonPath)).collect(Collectors.toList());
                        for(DictionaryDto dictionaryDto:filteredDictionariesFieldToBeWriteHere){
                            jsonObject.put(dictionaryDto.getReturnCode(),inputJsonContext.read(dictionaryDto.getJsonContainer()+"."+dictionaryDto.getCode()));
                        }
                    }
                }
            }
            /*
            if(root.getIsJsonObject()){
                //root - json
                for(int i =1;i<splittedJsonPath.size();i++){
                    String s7LocalVar = splittedJsonPath.get(i);
                    String s7LocalWithReplacedArraySymbol = s7LocalVar.replaceAll("\\[\\*\\]","");
                    if(jsonContainers.get(s7LocalWithReplacedArraySymbol)==null){
                        if(i==1){
                            if(s7LocalVar.contains("[*]")){
                                JSONArray jsonArray = new JSONArray();
                                root.getJsonObject().put(s7LocalWithReplacedArraySymbol,jsonArray);
                                jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createArrayContainer(jsonArray));
                            }else{
                                JSONObject jsonObject = new JSONObject();
                                root.getJsonObject().put(s7LocalVar,jsonObject);
                                jsonContainers.put(s7LocalVar,new JsonConteinerDto().createObjectContainer(jsonObject));
                            }
                        }else {
                            if(s7LocalVar.contains("[*]")){
                                JSONArray jsonArray = new JSONArray();
                                String s7localVariablePrevious = splittedJsonPath.get(i-1);
                                if(s7localVariablePrevious.contains("[*]")){
                                    s7localVariablePrevious = s7localVariablePrevious.replaceAll("\\[\\*\\]","");
                                    jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonArray);
                                }else {
                                    jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalWithReplacedArraySymbol,jsonArray);
                                }
                                jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createArrayContainer(jsonArray));

                            }else{
                                JSONObject jsonObject = new JSONObject();
                                String s7localVariablePrevious = splittedJsonPath.get(i-1);
                                if(s7localVariablePrevious.contains("[*]")){
                                    s7localVariablePrevious = s7localVariablePrevious.replaceAll("\\[\\*\\]","");
                                    jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonObject);
                                }else {
                                    jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalWithReplacedArraySymbol,jsonObject);
                                }
                                jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createObjectContainer(jsonObject));
                            }
                        }
                    }
                }
            }else {
                // root - array json
                for(int i =1;i<splittedJsonPath.size();i++){
                    String s7LocalVar = splittedJsonPath.get(i);
                    String s7LocalWithReplacedArraySymbol = s7LocalVar.replaceAll("\\[\\*\\]","");
                    if(i==1){
                        if(s7LocalVar.contains("[*]")){
                            JSONArray jsonArray = new JSONArray();
                            root.getJsonArray().add(jsonArray);
                            jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createArrayContainer(jsonArray));
                        }else{
                            JSONObject jsonObject = new JSONObject();
                            root.getJsonArray().add(jsonObject);
                            jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createObjectContainer(jsonObject));
                        }
                    }else {
                        if(s7LocalVar.contains("[*]")){
                            JSONArray jsonArray = new JSONArray();
                            String s7localVariablePrevious = splittedJsonPath.get(i-1);
                            if(s7localVariablePrevious.contains("[*]")){
                                s7localVariablePrevious = s7localVariablePrevious.replaceAll("\\[\\*\\]","");
                                jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonArray);
                            }else {
                                jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalWithReplacedArraySymbol,jsonArray);
                            }
                            jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createArrayContainer(jsonArray));

                        }else{
                            JSONObject jsonObject = new JSONObject();
                            String s7localVariablePrevious = splittedJsonPath.get(i-1);
                            if(s7localVariablePrevious.contains("[*]")){
                                s7localVariablePrevious = s7localVariablePrevious.replaceAll("\\[\\*\\]","");
                                jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonObject);
                            }else {
                                jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalWithReplacedArraySymbol,jsonObject);
                            }
                            jsonContainers.put(s7LocalWithReplacedArraySymbol,new JsonConteinerDto().createObjectContainer(jsonObject));
                        }
                    }
                }
            }
            */
        }
        if(root.getIsJsonObject()){
            return root.getJsonObject().toJSONString();
        }else {
            return root.getJsonArray().toJSONString();
        }
    }

}
    /*
    List<DictionaryDto> dictionaryData;
    List<String> uniqueJsonContainer;
    public JsonMapperUsingPaths(List<DictionaryDto> dictionaryData) {
        if(dictionaryData.isEmpty()){
            throw new IllegalArgumentException("Empty data list");
        }
        this.dictionaryData = dictionaryData;
        uniqueJsonContainer = dictionaryData.stream().map(DictionaryDto::getJsonContainer).distinct().collect(Collectors.toList());
    }
    public String process(){

        JSONObject rootJsonObject = (JSONObject) JsonPath.parse("{}");
        //создавать не на основе s7localexpression, а на основе s7 и данных входных json
        for(String jsonContainer: uniqueJsonContainer){
            List<String> splittedS7Expression = processString(jsonContainer,false);
            for(int i=0;i<splittedS7Expression.size();i++){
                String s7LocalExpression = splittedS7Expression.get(i);
                if(i==0){
                    if(s7LocalExpression.contains("\\[\\*\\]")){
                        JSONArray jsonArray = new JSONArray();
                        s7LocalExpression = s7LocalExpression.replaceAll("$.","");
                        s7LocalExpression = s7LocalExpression.replaceAll("\\[\\*\\]","");
                        rootJsonObject.put(s7LocalExpression,jsonArray);
                    }else {
                        JSONObject jsonObject = new JSONObject();
                        s7LocalExpression = s7LocalExpression.replaceAll("$.","");
                        rootJsonObject.put(s7LocalExpression,jsonObject);
                    }
                }else {

                }


            }
        }
        return "abvgddsadsa";
    }

     */