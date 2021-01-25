package hu.zeletrik.couponsvc.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class StatusDto {

    private final Long id;
    private final String country;
    private final Integer redeemedToday;
    private final Integer winOverall;
}
