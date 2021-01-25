package hu.zeletrik.couponsvc.service.coversion;

import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TerritoryEntityToDtoConverter implements Converter<TerritoryEntity, TerritoryDto> {

    @Override
    public TerritoryDto convert(TerritoryEntity territoryEntity) {
        return TerritoryDto.builder()
                .id(territoryEntity.getId())
                .country(territoryEntity.getCountry())
                .winAfter(territoryEntity.getWinAfter())
                .maxWinOverall(territoryEntity.getMaxWinOverall())
                .maxWinPerDay(territoryEntity.getMaxWinPerDay())
                .build();
    }
}
