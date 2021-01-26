package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.response.AnalyticsResponse;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controller to handle analytics related requests
 */
@RestController
@RequestMapping("/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsController.class);

    private final TicketService ticketService;
    private final ConversionService conversionService;

    /**
     * Retrieves a list of winners based on the arguments
     *
     * @param country    the country to check
     * @param startDate  the start date to check winners
     * @param endDate    the end date to check winners
     * @param pageNumber the number of the page
     * @param pageSize   the size of the page
     * @return a list of {@link AnalyticsResponse}
     */
    @GetMapping("/{country}")
    public ResponseEntity<List<AnalyticsResponse>> pagedWinnersByCountry(final @PathVariable String country,
                                                                         final @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                         final @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                                                         final @RequestParam int pageNumber,
                                                                         final @RequestParam int pageSize) {
        LOGGER.info("Retrieve winners for country={}, between {} and {}", country, startDate, endDate);
        final var wrapper = WinnersPageWrapper.builder()
                .country(country.toUpperCase())
                .startDate(startDate.toInstant(ZoneOffset.UTC))
                .endDate(endDate.toInstant(ZoneOffset.UTC))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        return ResponseEntity.ok(ticketService.findWinners(wrapper)
                .stream()
                .map(dto -> conversionService.convert(dto, AnalyticsResponse.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }
}
