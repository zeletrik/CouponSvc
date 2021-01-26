package hu.zeletrik.couponsvc.api.conversion;

import hu.zeletrik.couponsvc.api.response.AnalyticsResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/***
 * Unit tests fpr {@link TicketDtoToAnalyticsResponseConverter}
 */
public class TicketDtoToAnalyticsResponseConverterTest {

    public static final String EMAIL = "foo@bar.com";
    public static final String TICKET_NUMBER = "ABCDEFGHIJ";

    private TicketDtoToAnalyticsResponseConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new TicketDtoToAnalyticsResponseConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(AnalyticsResponse response, TicketDto dto) {
        //given

        //when
        var actual = underTest.convert(dto);

        //then
        assertThat(actual, is(response));
    }

    @DataProvider
    private Object[][] convertProvider() {
        var response = AnalyticsResponse.builder()
                .number(TICKET_NUMBER)
                .email(EMAIL)
                .build();

        var dto = TicketDto.builder()
                .number(TICKET_NUMBER)
                .email(EMAIL)
                .winner(true)
                .build();

        var emptyResponse = AnalyticsResponse.builder().build();
        var emptyDto = TicketDto.builder().build();

        var partialResponse = AnalyticsResponse.builder()
                .number(TICKET_NUMBER)
                .build();
        var partialDto = TicketDto.builder()
                .number(TICKET_NUMBER)
                .build();

        return new Object[][]{
                {response, dto},
                {emptyResponse, emptyDto},
                {partialResponse, partialDto},
        };
    }
}
