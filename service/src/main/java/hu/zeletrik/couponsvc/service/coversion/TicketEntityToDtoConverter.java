package hu.zeletrik.couponsvc.service.coversion;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketEntityToDtoConverter implements Converter<TicketEntity, TicketDto> {

    @Override
    public TicketDto convert(TicketEntity ticketEntity) {
        return TicketDto.builder()
                .id(ticketEntity.getId())
                .country(ticketEntity.getCountry())
                .winner(ticketEntity.isWinner())
                .city(ticketEntity.getCity())
                .name(ticketEntity.getName())
                .email(ticketEntity.getEmail())
                .number(ticketEntity.getNumber())
                .street(ticketEntity.getStreet())
                .time(ticketEntity.getTimestamp())
                .zip(ticketEntity.getZip())
                .build();
    }
}
