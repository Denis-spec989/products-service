package github.denisspec989.productmainservice;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonRow {
    String jsonPath;
    Object value;

    public JsonRow(String jsonPath, Object value) {
        this.jsonPath = "$"+jsonPath;
        this.value = value;
    }
    @Override
    public String toString() {
        return "JsonRow{" +
                "jsonPath='" + jsonPath + '\'' +
                ", value=" + value +
                '}';
    }
}
