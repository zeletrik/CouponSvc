package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.request.RedeemRequest;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RedeemRequestToDtoConverter implements Converter<RedeemRequest, TicketDto> {

    @Override
    public TicketDto convert(RedeemRequest request) {
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
