package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.response.TerritoryResponse;
import hu.zeletrik.couponsvc.service.TerritoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/territory")
@AllArgsConstructor
public class TerritoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerritoryController.class);

    private final TerritoryService territoryService;
    private final ConversionService conversionService;

    @GetMapping("/details")
    public ResponseEntity<TerritoryResponse> retrieveTerritoryDetails(final @RequestParam String country) {
        LOGGER.info("Retrieve territory details by country");
        var serviceResponse = territoryService.findByCountry(country);
        var details = conversionService.convert(serviceResponse.getBody(), TerritoryResponse.class);

        var status = serviceResponse.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.NO_CONTENT;

        return ResponseEntity
                .status(status)
                .body(details);
    }
}
