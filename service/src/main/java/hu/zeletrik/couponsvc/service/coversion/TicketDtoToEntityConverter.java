package hu.zeletrik.couponsvc.service.coversion;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoToEntityConverter implements Converter<TicketDto, TicketEntity> {

    @Override
    public TicketEntity convert(TicketDto ticketDto) {
        var entity = new TicketEntity();
        entity.setId(ticketDto.getId());
        entity.setWinner(ticketDto.isWinner());
        entity.setCity(ticketDto.getCity());
        entity.setCountry(ticketDto.getCountry());
        entity.setEmail(ticketDto.getEmail());
        entity.setName(ticketDto.getName());
        entity.setNumber(ticketDto.getNumber());
        entity.setTimestamp(ticketDto.getTime());
        entity.setStreet(ticketDto.getStreet());
        entity.setZip(ticketDto.getZip());
        return entity;
    }
}
