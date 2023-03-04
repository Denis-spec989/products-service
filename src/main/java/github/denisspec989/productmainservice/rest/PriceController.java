package github.denisspec989.productmainservice.rest;

import github.denisspec989.productmainservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    @PostMapping("/load")
    public ResponseEntity manuallyLoadDataFromFileHandlerService(){
        priceService.scheduledGetNewPrices();
        return new ResponseEntity(HttpStatus.OK);
    }
}
