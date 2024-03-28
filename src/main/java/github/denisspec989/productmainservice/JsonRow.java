package github.denisspec989.productmainservice;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JsonRow {
    private String jsonPath;
    private Object value;

    public JsonRow(String jsonPath, Object value) {
        this.jsonPath = jsonPath;
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
