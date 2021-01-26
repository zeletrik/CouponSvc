package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import hu.zeletrik.couponsvc.data.repository.PageableTicketRepository;
import hu.zeletrik.couponsvc.data.repository.TicketRepository;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;
    private final PageableTicketRepository pageableTicketRepository;
    private final ConversionService conversionService;

    @Override
    public ServiceResponse<TicketDto> findTicketByNumber(String ticketNumber) {
        LOGGER.debug("Find ticket by number={}", ticketNumber);
        return ticketRepository.findByNumber(ticketNumber.toUpperCase())
                .map(entity -> conversionService.convert(entity, TicketDto.class))
                .map(dto -> ServiceResponse.<TicketDto>builder()
                        .success(true)
                        .body(dto)
                        .build())
                .orElse(ServiceResponse.<TicketDto>builder()
                        .success(false)
                        .body(TicketDto.builder().build())
                        .build());
    }

    @Override
    public List<TicketDto> findWinners(final WinnersPageWrapper winnersPageWrapper) {
        final var pager = PageRequest.of(winnersPageWrapper.getPageNumber(), winnersPageWrapper.getPageSize());
        final var country = winnersPageWrapper.getCountry();
        final var startDate = winnersPageWrapper.getStartDate();
        final var endDate = winnersPageWrapper.getEndDate();
        LOGGER.debug("Find winners for country={}, between {} and {}", country, startDate, endDate);

        return pageableTicketRepository.findByWinnerTrueAndCountryAndTimestampBetween(
                country, startDate, endDate, pager
        )
                .stream()
                .map(entity -> conversionService.convert(entity, TicketDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponse<TicketDto> updateTicket(final TicketDto ticketDto) {
        final var entityToSave = conversionService.convert(ticketDto, TicketEntity.class);
        return Objects.nonNull(entityToSave)
                ? ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(conversionService.convert(ticketRepository.save(entityToSave), TicketDto.class))
                .build()
                : ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(TicketDto.builder().build())
                .build();
    }

    @Override
    public ServiceResponse<TicketDto> setTicketWinner(final String ticketNumber) {
        final var entity = ticketRepository.findByNumber(ticketNumber.toUpperCase());
        return entity.map(it -> {
            it.setWinner(true);
            return it;
        })
                .map(ticketRepository::save)
                .map(it -> conversionService.convert(it, TicketDto.class))
                .map(dto -> ServiceResponse.<TicketDto>builder()
                        .success(true)
                        .body(dto)
                        .build())
                .orElse(ServiceResponse.<TicketDto>builder()
                        .success(false)
                        .body(TicketDto.builder().build())
                        .build());
    }

}
