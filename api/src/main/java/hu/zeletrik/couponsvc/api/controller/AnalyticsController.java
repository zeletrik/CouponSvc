package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsController.class);

    private final TicketService ticketService;

    @GetMapping("/{country}")
    public ResponseEntity<?> pagedWinnersByCountry(final @PathVariable String country,
                                                   final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                   final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                   final @RequestParam int pageNumber,
                                                   final @RequestParam int pageSize) {

        var wrapper = WinnersPageWrapper.builder()
                .country(country.toUpperCase())
                .startDate(startDate.toInstant(ZoneOffset.UTC))
                .endDate(endDate.toInstant(ZoneOffset.UTC))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ticketService.findWinners(wrapper));
    }
}
