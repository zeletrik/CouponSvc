package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.response.RedeemResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoToResponseConverter implements Converter<TicketDto, RedeemResponse> {

    @Override
    public RedeemResponse convert(TicketDto dto) {
        return RedeemResponse.builder()
                .number(dto.getNumber())
                .email(dto.getEmail())
                .winner(dto.isWinner())
                .build();
    }
}
