package hu.zeletrik.couponsvc.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketRedeemRequest {

    private String number;
    private String name;
    private String email;
    private String country;
    private String city;
    private String street;
    private String zip;
}
