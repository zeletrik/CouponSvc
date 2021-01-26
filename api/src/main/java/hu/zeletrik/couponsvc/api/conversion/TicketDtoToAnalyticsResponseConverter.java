package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.response.AnalyticsResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoToAnalyticsResponseConverter implements Converter<TicketDto, AnalyticsResponse> {

    @Override
    public AnalyticsResponse convert(TicketDto dto) {
        return AnalyticsResponse.builder()
                .number(dto.getNumber())
                .email(dto.getEmail())
                .build();
    }
}
