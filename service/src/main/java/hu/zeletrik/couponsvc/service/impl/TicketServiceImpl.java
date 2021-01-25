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
        var pager = PageRequest.of(winnersPageWrapper.getPageNumber(), winnersPageWrapper.getPageSize());
        var country = winnersPageWrapper.getCountry();
        var startDate = winnersPageWrapper.getStartDate();
        var endDate = winnersPageWrapper.getEndDate();

        var winners = pageableTicketRepository.findByWinnerTrueAndCountryAndTimestampBetween(
                country, startDate, endDate, pager
        )
                .stream()
                .map(entity -> conversionService.convert(entity, TicketDto.class))
                .collect(Collectors.toList());

        return winners;
    }

    @Override
    public ServiceResponse<TicketDto> updateTicket(TicketDto ticketDto) {
        var entity = ticketRepository.save(conversionService.convert(ticketDto, TicketEntity.class));
        var dto = conversionService.convert(entity, TicketDto.class);
        return ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(dto)
                .build();
    }

    @Override
    public ServiceResponse<TicketDto> setTicketWinner(String ticketNumber) {
        var entity = ticketRepository.findByNumber(ticketNumber.toUpperCase());
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
