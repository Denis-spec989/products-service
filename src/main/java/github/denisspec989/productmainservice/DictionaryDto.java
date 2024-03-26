package github.denisspec989.productmainservice;

import lombok.Data;

@Data
public class DictionaryDto {
    private String templateId;
    private String type;
    private String code;
    private String name;
    private Boolean required;
    private String invalidValueRule;
    private String jsonContainer;
    private String jsonType;
    private String returnContainers;
    private String returnCode;
}
