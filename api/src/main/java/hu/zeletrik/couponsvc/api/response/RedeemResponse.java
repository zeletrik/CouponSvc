package hu.zeletrik.couponsvc.api.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class RedeemResponse {

    private final String email;
    private final String number;
    private final boolean winner;
}
