package github.denisspec989.productmainservice.rest;

import github.denisspec989.productmainservice.models.PriceDTO;
import github.denisspec989.productmainservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/list")
    List<PriceDTO> getPricesOnAzs(@RequestParam("azsId") String azsId){
       return priceService.getPricesOnAzs(azsId);
    }
}
