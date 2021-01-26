package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.request.RedeemRequest;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for {@link RedeemRequestToDtoConverter }.
 */
public class RedeemRequestToDtoConverterTest {

    public static final String HUNGARY = "HUNGARY";
    public static final String EMAIL = "foo@bar.com";
    public static final String CITY = "Foo";
    public static final String TICKET_NUMBER = "ABCDEFGHIJ";

    private RedeemRequestToDtoConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new RedeemRequestToDtoConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(RedeemRequest request, TicketDto expected) {
        //given

        //when
        var actual = underTest.convert(request);

        //then
        assertThat(actual.getNumber(), is(expected.getNumber()));
        assertThat(actual.getCity(), is(expected.getCity()));
        assertThat(actual.getEmail(), is(expected.getEmail()));
        assertThat(actual.getTime().truncatedTo(ChronoUnit.MINUTES), is(Instant.now().truncatedTo(ChronoUnit.MINUTES)));
    }

    @DataProvider
    private Object[][] convertProvider() {
        var request = new RedeemRequest();
        request.setCountry(HUNGARY);
        request.setEmail(EMAIL);
        request.setCity(CITY);
        request.setNumber(TICKET_NUMBER);
        var dto = TicketDto.builder()
                .country(HUNGARY)
                .number(TICKET_NUMBER)
                .email(EMAIL)
                .city(CITY)
                .build();

        var partialRequest = new RedeemRequest();
        partialRequest.setNumber(TICKET_NUMBER);
        partialRequest.setCountry(HUNGARY);
        var partialDto = TicketDto.builder()
                .country(HUNGARY)
                .number(TICKET_NUMBER)
                .build();

        return new Object[][]{
                {request, dto},
                {partialRequest, partialDto},
        };
    }
}
