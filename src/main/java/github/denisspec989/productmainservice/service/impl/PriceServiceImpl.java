package github.denisspec989.productmainservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("start scheduling");
        ResponseEntity<List<PetrolStationDto>> responseJson;
        ResponseEntity<List<PetrolStationDto>> responseXML;
        do {
            responseJson = fileServiceRepository.getJsonData("Azs_with_prices_and_services");
            responseXML = fileServiceRepository.getXmlData("Azs_with_prices_and_services");
        } while (!(responseJson.getStatusCode().value() == 200 && responseXML.getStatusCode().value() == 200));
        
        Set<Price> savingSet = new HashSet<>(fromPetrolStationDtoListToPriceList(responseJson.getBody()));
        List<Price> xmlList = fromPetrolStationDtoListToPriceList(responseXML.getBody());
        for (Price price : xmlList) {
            if (!savingSet.contains(price)) {
                savingSet.add(price);
            }
        }
        List<Price> savingList = new ArrayList<>(savingSet);
        priceRepository.saveAll(savingList);
    }

    @Override
    @Transactional
    public List<PriceDTO> getPricesOnAzs(String azsId) {
        return fromPriceListToPriceDTOList(priceRepository.findAllByAzsId(azsId));
    }
}
