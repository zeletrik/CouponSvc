package hu.zeletrik.couponsvc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class WinnersPageWrapper {

    private final String country;
    private final Instant startDate;
    private final Instant endDate;
    private final Integer pageNumber;
    private final Integer pageSize;

}
