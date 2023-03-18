package github.denisspec989.productmainservice.service.impl;

import github.denisspec989.productmainservice.domain.Price;
import github.denisspec989.productmainservice.models.PetrolStationDto;
import github.denisspec989.productmainservice.models.PriceDTO;
import github.denisspec989.productmainservice.models.PriceModelDto;
import github.denisspec989.productmainservice.repository.feign.FileServiceRepository;
import github.denisspec989.productmainservice.repository.jpa.PriceRepository;
import github.denisspec989.productmainservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final FileServiceRepository fileServiceRepository;
    private final PriceRepository priceRepository;

    List<Price> fromPetrolStationDtoListToPriceList(List<PetrolStationDto> petrolStationDtoList){
        List<Price> priceList = new ArrayList<>();
        for(PetrolStationDto petrolStationDto : petrolStationDtoList){
            for(PriceModelDto priceModelDto: petrolStationDto.getPriceModelList()){
                Price price = new Price();
                price.setProductPrice(priceModelDto.getFuelPrice());
                price.setProductName(priceModelDto.getFuelName());
                price.setAzsId(petrolStationDto.getAzsId());
                priceList.add(price);
            }
        }
        return priceList;
    }
    List<PriceDTO> fromPriceListToPriceDTOList(List<Price> priceList){
        List<PriceDTO> priceDTOList = new ArrayList<>();
        for(Price price:priceList){
            PriceDTO priceDTO = new PriceDTO();
            priceDTO.setFuelPrice(price.getProductPrice());
            priceDTO.setFuelName(price.getProductName());
            priceDTOList.add(priceDTO);
        }
        return priceDTOList;
    }
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledGetNewPrices() {
        System.out.println("start scheduling");
        ResponseEntity<List<PetrolStationDto>> responseJson;
        ResponseEntity<List<PetrolStationDto>> responseXML;
        do {
            responseJson = fileServiceRepository.getJsonData("Azs_with_prices_and_services");
            responseXML=fileServiceRepository.getXmlData("Azs_with_prices_and_services");
        } while (!(responseJson.getStatusCode().value()==200&&responseXML.getStatusCode().value()==200));
        List<Price> savingList = fromPetrolStationDtoListToPriceList(fileServiceRepository.getJsonData("Azs_with_prices_and_services").getBody());
        List<Price> xmlList = fromPetrolStationDtoListToPriceList(fileServiceRepository.getXmlData("Azs_with_prices_and_services").getBody());
        for(Price price:xmlList){
            if(savingList.contains(price)){
                continue;
            } else {
                savingList.add(price);
            }
        }
        priceRepository.saveAll(savingList);
    }

    @Override
    @Transactional
    public List<PriceDTO> getPricesOnAzs(String azsId) {
        return fromPriceListToPriceDTOList(priceRepository.findAllByAzsId(azsId));
    }
}
