package hu.zeletrik.couponsvc.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RedeemRequest {

    @Size(min = 10, max = 10, message = "Ticket number should be 10 character long")
    private String number;

    @NotBlank(message = "The name filed is required")
    private String name;

    @Email(message = "The email filed is not valid")
    private String email;

    @Email(message = "The age filed is required")
    @Min(value = 13, message = "Cannot participate under 13")
    private int age;

    @NotBlank(message = "The country filed is required")
    private String country;

    @NotBlank(message = "The city filed is required")
    private String city;

    @NotBlank(message = "The street filed is required")
    private String street;

    @NotBlank(message = "The zip filed is required")
    private String zip;
}
