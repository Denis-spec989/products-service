package github.denisspec989.productmainservice;

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
    private HashMap<String,JsonConteinerDto> jsonContainers;
    //private final String ROOT = "root";
    JsonConteinerDto root = new JsonConteinerDto();
    public JsonMapperUsingPaths(List<DictionaryDto> dictionaryData){
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
    }

    public String process(){
        for(String jsonPath:uniqueJsonPathsForReturnContainers){
            List<String> splittedJsonPath = JsonUtil.processString(jsonPath,false);
            //for (int i=0;i<splittedJsonPath.size();i++){
            //    if(i==0){
            //        if(splittedJsonPath.get(0).contains("$.[*]")){
            //            jsonContainers.put(ROOT,new JsonConteinerDto().createArrayContainer(new JSONArray()));
            //            //root.createArrayContainer(new JSONArray());
            //        }else {
            //            jsonContainers
            //        }
            //    }
            //}
            if(root.getIsJsonObject()){
                //root - json
                for(int i =1;i<splittedJsonPath.size();i++){
                    String s7LocalVar = splittedJsonPath.get(i);
                    if(i==1){
                        if(s7LocalVar.contains("[*]")){
                            JSONArray jsonArray = new JSONArray();
                            s7LocalVar = s7LocalVar.replaceAll("[*]","");
                            root.getJsonObject().put(s7LocalVar,jsonArray);
                            jsonContainers.put(s7LocalVar,new JsonConteinerDto().createArrayContainer(jsonArray));
                        }else{
                            JSONObject jsonObject = new JSONObject();
                            root.getJsonObject().put(s7LocalVar,jsonObject);
                            jsonContainers.put(s7LocalVar,new JsonConteinerDto().createObjectContainer(jsonObject));
                        }
                    }else {
                        if(s7LocalVar.contains("[*]")){
                            JSONArray jsonArray = new JSONArray();
                            s7LocalVar = s7LocalVar.replaceAll("[*]","");
                            String s7localVariablePrevious = splittedJsonPath.get(i-1);
                            if(s7localVariablePrevious.contains("[*]")){
                                s7localVariablePrevious = s7localVariablePrevious.replaceAll("[*]","");
                                jsonContainers.get(s7localVariablePrevious).getJsonArray().add(jsonArray);
                            }else {
                                jsonContainers.get(s7localVariablePrevious).getJsonObject().put(s7LocalVar,jsonArray);
                            }
                            jsonContainers.put(s7LocalVar,new JsonConteinerDto().createArrayContainer(jsonArray));

                        }else{
                            JSONObject jsonObject = new JSONObject();
                            s7LocalVar = s7LocalVar.replaceAll("[*]","");
                            String s7localVariablePrevious = splittedJsonPath.get(i-1);
                            if(s7localVariablePrevious.contains("[*]")){

                            }else {

                            }
                        }
                    }
                }
            }else {
                // root - array json
            }
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
                    if(s7LocalExpression.contains("\\[*]")){
                        JSONArray jsonArray = new JSONArray();
                        s7LocalExpression = s7LocalExpression.replaceAll("$.","");
                        s7LocalExpression = s7LocalExpression.replaceAll("\\[*]","");
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