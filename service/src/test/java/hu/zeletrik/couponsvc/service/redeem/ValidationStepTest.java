package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.exception.NonExistingTerritoryException;
import hu.zeletrik.couponsvc.service.exception.TicketAlreadyExistException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ValidationStepTest {

    private static final int STEP_PRIORITY = 1;
    public static final String HUNGARY = "HUNGARY";
    public static final String TICKET_NUMBER = "ABCDEFGHIJ";

    @Mock
    private TicketService ticketServiceMock;

    @Mock
    private TerritoryService territoryServiceMock;

    private RedeemChain underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ValidatorStep(ticketServiceMock, territoryServiceMock);
    }

    @Test
    public void getPriorityShouldConsistent() {
        //given
        //when
        var actual = underTest.getPriority();

        //then
        assertThat(actual, is(STEP_PRIORITY));
    }

    @Test
    public void chainShouldWorkWhenSuccess() {
        //given
        var DTO = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();

        var ticketResult = ServiceResponse.<TicketDto>builder().success(false).build();
        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).build();

        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(ticketServiceMock.findTicketByNumber(TICKET_NUMBER)).willReturn(ticketResult);

        //when
        underTest.chain(DTO);

        //then
        verify(territoryServiceMock).findByCountry(HUNGARY);
        verify(ticketServiceMock).findTicketByNumber(TICKET_NUMBER);
        verifyNoMoreInteractions(territoryServiceMock, ticketServiceMock);
    }

    @Test(expectedExceptions = TicketAlreadyExistException.class)
    public void chainShouldWorkWhenTicketAlreadyRedeemed() {
        //given
        var DTO = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();

        var ticketResult = ServiceResponse.<TicketDto>builder().success(true).build();
        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).build();

        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(ticketServiceMock.findTicketByNumber(TICKET_NUMBER)).willReturn(ticketResult);

        //when
        underTest.chain(DTO);

        //then
        // Expected exception
    }

    @Test(expectedExceptions = NonExistingTerritoryException.class)
    public void chainShouldWorkWhenTerritoryNotExist() {
        //given
        var DTO = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();

        var ticketResult = ServiceResponse.<TicketDto>builder().success(false).build();
        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(false).build();

        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(ticketServiceMock.findTicketByNumber(TICKET_NUMBER)).willReturn(ticketResult);

        //when
        underTest.chain(DTO);

        //then
        // Expected exception
    }
}
