package hu.zeletrik.couponsvc.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@EqualsAndHashCode
public class TicketDto {

    private final Long id;
    private final String number;
    private final String name;
    private final String email;
    private final String country;
    private final String city;
    private final String street;
    private final String zip;
    private final boolean winner;
    private final Instant time;
}
