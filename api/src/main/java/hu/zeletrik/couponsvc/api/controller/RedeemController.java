package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.request.TicketRedeemRequest;
import hu.zeletrik.couponsvc.api.response.TerritoryResponse;
import hu.zeletrik.couponsvc.service.RedeemService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redeem")
@AllArgsConstructor
public class RedeemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedeemController.class);

    private final RedeemService redeemService;
    private final TicketService ticketService;
    private final ConversionService conversionService;

    @PostMapping()
    public ResponseEntity<?> retrieveTerritoryDetails(final @RequestBody TicketRedeemRequest request) {
        LOGGER.info("Retrieve ticket details by number for={}", request.getNumber());

        redeemService.redeem(conversionService.convert(request, TicketDto.class));

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("ASD");
    }
}