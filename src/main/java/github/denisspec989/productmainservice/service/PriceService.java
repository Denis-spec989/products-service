package github.denisspec989.productmainservice.service;

import github.denisspec989.productmainservice.models.PriceDTO;

import java.util.List;

public interface PriceService {
    void scheduledGetNewPrices();
    List<PriceDTO>  getPricesOnAzs(String azsId);
}
