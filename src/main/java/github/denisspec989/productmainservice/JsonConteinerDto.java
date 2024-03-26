package github.denisspec989.productmainservice;

import lombok.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Data
public class JsonConteinerDto {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Boolean isJsonObject;
    public JsonConteinerDto createObjectContainer(JSONObject jsonObject){
        this.jsonObject=jsonObject;
        this.isJsonObject=true;
        return this;
    }
    public JsonConteinerDto createArrayContainer(JSONArray jsonArray){
        this.jsonArray = jsonArray;
        this.isJsonObject=false;
        return this;
    }

}
