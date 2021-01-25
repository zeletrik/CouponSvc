package hu.zeletrik.couponsvc.service.coversion;

import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusDtoToEntityConverter implements Converter<StatusDto, StatusEntity> {

    @Override
    public StatusEntity convert(StatusDto statusDto) {
        var entity = new StatusEntity();
        entity.setId(statusDto.getId());
        entity.setCountry(statusDto.getCountry());
        entity.setRedeemedToday(statusDto.getRedeemedToday());
        entity.setWinOverall(statusDto.getWinOverall());
        return entity;
    }
}
