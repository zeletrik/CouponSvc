package hu.zeletrik.couponsvc.api.controller;

import hu.zeletrik.couponsvc.api.response.AnalyticsResponse;
import hu.zeletrik.couponsvc.service.TicketService;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/***
 * Unit tests fpr {@link AnalyticsController}
 */
public class AnalyticsControllerTest {

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
    private TicketService ticketServiceMock;
    @Mock
    private ConversionService conversionServiceMock;

    private AnalyticsController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AnalyticsController(ticketServiceMock, conversionServiceMock);
    }

    @Test
    public void pagedWinnersByCountryShouldWork() {
        //given
        var response = AnalyticsResponse.builder().number(TICKET_NUMBER).email(EMAIL).build();
        var dto = TicketDto.builder().number(TICKET_NUMBER).email(EMAIL).build();

        given(ticketServiceMock.findWinners(WRAPPER)).willReturn(List.of(dto));
        given(conversionServiceMock.convert(dto, AnalyticsResponse.class)).willReturn(response);

        //when
        var actual = underTest.pagedWinnersByCountry(
                COUNTRY, START_DATE, END_DATE, PAGE_NUMBER, PAGE_SIZE);

        //then
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), is(List.of(response)));
        verify(ticketServiceMock).findWinners(WRAPPER);
        verify(conversionServiceMock).convert(dto, AnalyticsResponse.class);
        verifyNoMoreInteractions(ticketServiceMock, conversionServiceMock);
    }

    @Test
    public void pagedWinnersByCountryShouldReturnEmptyListWhenConversionFails() {
        //given
        var dto = TicketDto.builder().number(TICKET_NUMBER).email(EMAIL).build();

        given(ticketServiceMock.findWinners(WRAPPER)).willReturn(List.of(dto));
        given(conversionServiceMock.convert(dto, AnalyticsResponse.class)).willReturn(null);

        //when
        var actual = underTest.pagedWinnersByCountry(
                COUNTRY, START_DATE, END_DATE, PAGE_NUMBER, PAGE_SIZE);

        //then
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), is(Collections.emptyList()));
        verify(ticketServiceMock).findWinners(WRAPPER);
        verify(conversionServiceMock).convert(dto, AnalyticsResponse.class);
        verifyNoMoreInteractions(ticketServiceMock, conversionServiceMock);
    }
}
