package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.response.TerritoryResponse;
import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TerritoryDtoToResponseConverter implements Converter<TerritoryDto, TerritoryResponse> {

    @Override
    public TerritoryResponse convert(TerritoryDto territoryEntity) {
        return TerritoryResponse.builder()
                .country(territoryEntity.getCountry())
                .winAfter(territoryEntity.getWinAfter())
                .maxWinOverall(territoryEntity.getMaxWinOverall())
                .maxWinPerDay(territoryEntity.getMaxWinPerDay())
                .build();
    }
}
