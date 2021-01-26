package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.service.RedeemService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.redeem.RedeemChain;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class RedeemServiceFacadeTest {

    public static final String TICKET_NUMBER = "ABCDEFGHJ";
    @Mock
    private RedeemChain firstProcessorsMock;

    @Mock
    private RedeemChain secondProcessorsMock;

    @Mock
    private TicketService ticketServiceMock;

    @Captor
    ArgumentCaptor<TicketDto> firstTicketCaptor = ArgumentCaptor.forClass(TicketDto.class);

    @Captor
    ArgumentCaptor<TicketDto> secondTicketCaptor = ArgumentCaptor.forClass(TicketDto.class);

    private RedeemService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RedeemServiceFacade(List.of(secondProcessorsMock, firstProcessorsMock), ticketServiceMock);
    }

    @Test
    public void redeemShouldWork() {
        //given
        var expected = TicketDto.builder().number(TICKET_NUMBER).build();
        var serviceResponse = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(expected)
                .build();

        given(firstProcessorsMock.getPriority()).willReturn(1);
        given(secondProcessorsMock.getPriority()).willReturn(2);

        Mockito.doNothing().when(firstProcessorsMock).chain(firstTicketCaptor.capture());
        Mockito.doNothing().when(secondProcessorsMock).chain(secondTicketCaptor.capture());

        given(ticketServiceMock.findTicketByNumber(TICKET_NUMBER)).willReturn(serviceResponse);

        //when
        var actual = underTest.redeem(expected);

        //then
        assertThat(actual.getBody(), is(expected));
        verify(firstProcessorsMock).getPriority();
        verify(secondProcessorsMock).getPriority();
        verify(firstProcessorsMock).chain(expected);
        verify(secondProcessorsMock).chain(expected);
        verify(ticketServiceMock).findTicketByNumber(TICKET_NUMBER);
        verifyNoMoreInteractions(firstProcessorsMock, secondProcessorsMock, ticketServiceMock);

    }
}
