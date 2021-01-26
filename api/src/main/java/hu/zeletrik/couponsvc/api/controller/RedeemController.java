package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.request.RedeemRequest;
import hu.zeletrik.couponsvc.api.response.RedeemResponse;
import hu.zeletrik.couponsvc.service.RedeemService;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/redeem")
@AllArgsConstructor
public class RedeemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedeemController.class);

    private final RedeemService redeemService;
    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<RedeemResponse> redeemTicket(final @Valid @RequestBody RedeemRequest request) {
        LOGGER.info("Retrieve ticket details by number for={}", request.getNumber());
        final var converted = conversionService.convert(request, TicketDto.class);
        if (Objects.isNull(converted)) {
            throw new RuntimeException("Could not convert request");
        }

        final var redeemResponse = redeemService.redeem(converted);
        if (!redeemResponse.isSuccess()) {
            throw new RuntimeException("Ticket redeem failed!");
        }

        final var body = conversionService.convert(redeemResponse.getBody(), RedeemResponse.class);
        if (Objects.isNull(body)) {
            throw new RuntimeException("Could not convert data");
        }
        return ResponseEntity.ok(body);
    }
}
