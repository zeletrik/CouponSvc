package hu.zeletrik.couponsvc.api.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class TerritoryResponse {

    private final String country;
    private final Integer winAfter;
    private final Integer maxWinPerDay;
    private final Integer maxWinOverall;
}
