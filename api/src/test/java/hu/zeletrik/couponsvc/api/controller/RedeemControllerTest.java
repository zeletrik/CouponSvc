package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.request.RedeemRequest;
import hu.zeletrik.couponsvc.api.response.RedeemResponse;
import hu.zeletrik.couponsvc.service.RedeemService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/***
 * Unit tests fpr {@link RedeemController}
 */
public class RedeemControllerTest {

    private static final String COUNTRY = "HUNGARY";
    private static final LocalDateTime START_DATE = LocalDateTime.now();
    private static final LocalDateTime END_DATE = LocalDateTime.now();
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 1;
    public static final String TICKET_NUMBER = "ABCDEFGHIJ";
    public static final String EMAIL = "foo@bar.com";

    public static final WinnersPageWrapper WRAPPER = WinnersPageWrapper.builder()
            .country(COUNTRY)
            .startDate(START_DATE.toInstant(ZoneOffset.UTC))
            .endDate(END_DATE.toInstant(ZoneOffset.UTC))
            .pageNumber(PAGE_NUMBER)
            .pageSize(PAGE_SIZE)
            .build();

    @Mock
    private RedeemService redeemService;
    @Mock
    private ConversionService conversionServiceMock;

    private RedeemController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RedeemController(redeemService, conversionServiceMock);
    }

    @Test
    public void redeemTicketShouldWork() {
        //given
        var request = RedeemRequest.builder().email(EMAIL).number(TICKET_NUMBER).build();
        var response = RedeemResponse.builder().email(EMAIL).winner(true).number(TICKET_NUMBER).build();
        var dto = TicketDto.builder().number(TICKET_NUMBER).email(EMAIL).build();
        var serviceResponse = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(dto)
                .build();

        given(conversionServiceMock.convert(request, TicketDto.class)).willReturn(dto);
        given(redeemService.redeem(dto)).willReturn(serviceResponse);
        given(conversionServiceMock.convert(dto, RedeemResponse.class)).willReturn(response);

        //when
        var actual = underTest.redeemTicket(request);

        //then
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), is(response));
        verify(conversionServiceMock).convert(request, TicketDto.class);
        verify(redeemService).redeem(dto);
        verify(conversionServiceMock).convert(dto, RedeemResponse.class);
        verifyNoMoreInteractions(conversionServiceMock);
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void redeemTicketShouldWorkWhenResponseConversionFails() {
        //given
        var request = RedeemRequest.builder().email(EMAIL).number(TICKET_NUMBER).build();
        var dto = TicketDto.builder().number(TICKET_NUMBER).email(EMAIL).build();
        var serviceResponse = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(dto)
                .build();

        given(conversionServiceMock.convert(request, TicketDto.class)).willReturn(dto);
        given(redeemService.redeem(dto)).willReturn(serviceResponse);
        given(conversionServiceMock.convert(dto, RedeemResponse.class)).willReturn(null);

        //when
        underTest.redeemTicket(request);

        //then
        // expected exception
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void redeemTicketShouldWorkWhenRedeemFails() {
        //given
        var request = RedeemRequest.builder().email(EMAIL).number(TICKET_NUMBER).build();
        var dto = TicketDto.builder().number(TICKET_NUMBER).email(EMAIL).build();
        var serviceResponse = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(dto)
                .build();

        given(conversionServiceMock.convert(request, TicketDto.class)).willReturn(dto);
        given(redeemService.redeem(dto)).willReturn(serviceResponse);

        //when
        underTest.redeemTicket(request);

        //then
        // expected exception
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void redeemTicketShouldWorkWhenRequestConversionFails() {
        //given
        var request = RedeemRequest.builder().email(EMAIL).number(TICKET_NUMBER).build();

        given(conversionServiceMock.convert(request, TicketDto.class)).willReturn(null);

        //when
        underTest.redeemTicket(request);

        //then
        // expected exception
    }
}
