package github.denisspec989.productmainservice.service.impl;

import github.denisspec989.productmainservice.domain.Price;
import github.denisspec989.productmainservice.models.PetrolStationDto;
import github.denisspec989.productmainservice.models.PriceModelDto;
import github.denisspec989.productmainservice.repository.feign.FileServiceRepository;
import github.denisspec989.productmainservice.repository.jpa.PriceRepository;
import github.denisspec989.productmainservice.service.PriceService;
import lombok.RequiredArgsConstructor;
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
    @Override
    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledGetNewPrices() {
        System.out.println("start scheduling");
        List<Price> savingList = fromPetrolStationDtoListToPriceList(fileServiceRepository.getJsonData("Azs_with_prices_and_services"));
        System.out.println(savingList.size());
        List<Price> xmlList = fromPetrolStationDtoListToPriceList(fileServiceRepository.getXmlData("Azs_with_prices_and_services"));
        for(Price price:xmlList){
            if(savingList.contains(price)){
                continue;
            } else {
                savingList.add(price);
            }
        }
        System.out.println(savingList.size());
        priceRepository.saveAll(savingList);
    }
}
