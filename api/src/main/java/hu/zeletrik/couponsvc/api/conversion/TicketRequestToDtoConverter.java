package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.request.TicketRedeemRequest;
import hu.zeletrik.couponsvc.api.response.TerritoryResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TicketRequestToDtoConverter implements Converter<TicketRedeemRequest, TicketDto> {

    @Override
    public TicketDto convert(TicketRedeemRequest request) {
        return TicketDto.builder()
                .country(request.getCountry())
                .email(request.getEmail())
                .zip(request.getZip())
                .time(Instant.now())
                .street(request.getStreet())
                .number(request.getNumber().toUpperCase())
                .name(request.getName())
                .city(request.getCity())
                .build();
    }
}
