package github.denisspec989.productmainservice.models;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetrolStationDto {
    private String      azsId;
    private Double      latitude;
    private Double      longitude;
    private String      telephone;
    private String      azsName;
    private String      azsAddress;
    private List<PriceModelDto> priceModelList;
    private List<ServiceModelDto> serviceModelList;
}
