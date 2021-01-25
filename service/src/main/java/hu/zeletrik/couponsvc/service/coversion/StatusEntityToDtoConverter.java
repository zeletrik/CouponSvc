package hu.zeletrik.couponsvc.service.coversion;

import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusEntityToDtoConverter implements Converter<StatusEntity, StatusDto> {

    @Override
    public StatusDto convert(StatusEntity statusEntity) {
        return StatusDto.builder()
                .id(statusEntity.getId())
                .country(statusEntity.getCountry())
                .redeemedToday(statusEntity.getRedeemedToday())
                .winOverall(statusEntity.getWinOverall())
                .build();
    }
}
