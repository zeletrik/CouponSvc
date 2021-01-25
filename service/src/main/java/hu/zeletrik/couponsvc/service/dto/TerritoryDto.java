package hu.zeletrik.couponsvc.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TerritoryDto {

    private final Long id;
    private final String country;
    private final Integer winAfter;
    private final Integer maxWinPerDay;
    private final Integer maxWinOverall;

}
