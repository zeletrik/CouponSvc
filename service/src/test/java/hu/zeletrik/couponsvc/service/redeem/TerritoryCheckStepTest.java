package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.StatusService;
import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TerritoryCheckStepTest {

    private static final int STEP_PRIORITY = 3;
    public static final String HUNGARY = "HUNGARY";
    public static final String TICKET_NUMBER = "ABCDEFGHIJ";

    @Mock
    private TicketService ticketServiceMock;
    @Mock
    private TerritoryService territoryServiceMock;
    @Mock
    private StatusService statusServiceMock;

    @Captor
    ArgumentCaptor<StatusDto> statusCaptor = ArgumentCaptor.forClass(StatusDto.class);

    @Captor
    ArgumentCaptor<String> ticketNumberCaptor = ArgumentCaptor.forClass(String.class);

    private RedeemChain underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new TerritoryCheckStep(ticketServiceMock, territoryServiceMock, statusServiceMock);
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
    public void chainShouldWorkWhenWins() {
        //given
        var redeemedToday = 19;
        var winOverall = 99;
        var ticketDto = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();
        var territoryDto = TerritoryDto.builder()
                .winAfter(10)
                .maxWinPerDay(20)
                .maxWinOverall(100)
                .build();
        var statusDto = StatusDto.builder()
                .redeemedToday(redeemedToday)
                .winOverall(winOverall)
                .build();

        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).body(territoryDto).build();
        var statusResult = ServiceResponse.<StatusDto>builder().success(true).body(statusDto).build();
        var ticketResult = ServiceResponse.<TicketDto>builder().success(true).body(ticketDto).build();
        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(statusServiceMock.findByCountry(HUNGARY)).willReturn(statusResult);
        given(statusServiceMock.updateStatus(statusCaptor.capture())).willReturn(statusResult);
        given(ticketServiceMock.setTicketWinner(ticketNumberCaptor.capture())).willReturn(ticketResult);

        //when
        underTest.chain(ticketDto);

        //then
        var updatedStatus = statusCaptor.getValue();
        assertThat(ticketNumberCaptor.getValue(), is(TICKET_NUMBER));
        assertThat(updatedStatus.getRedeemedToday(), is(redeemedToday + 1));
        assertThat(updatedStatus.getWinOverall(), is(winOverall + 1));
        verify(territoryServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).updateStatus(any());
        verify(ticketServiceMock).setTicketWinner(TICKET_NUMBER);
        verifyNoMoreInteractions(territoryServiceMock, statusServiceMock, ticketServiceMock);
    }

    @Test
    public void chainShouldWorkWhenNotWinsDueToCounter() {
        //given
        var redeemedToday = 8;
        var winOverall = 5;
        var ticketDto = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();
        var territoryDto = TerritoryDto.builder()
                .winAfter(10)
                .maxWinPerDay(20)
                .maxWinOverall(100)
                .build();
        var statusDto = StatusDto.builder()
                .redeemedToday(redeemedToday)
                .winOverall(winOverall)
                .build();

        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).body(territoryDto).build();
        var statusResult = ServiceResponse.<StatusDto>builder().success(true).body(statusDto).build();
        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(statusServiceMock.findByCountry(HUNGARY)).willReturn(statusResult);
        given(statusServiceMock.updateStatus(statusCaptor.capture())).willReturn(statusResult);

        //when
        underTest.chain(ticketDto);

        //then
        var updatedStatus = statusCaptor.getValue();
        assertThat(updatedStatus.getRedeemedToday(), is(redeemedToday + 1));
        assertThat(updatedStatus.getWinOverall(), is(winOverall));
        verify(territoryServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).updateStatus(any());
        verifyNoMoreInteractions(territoryServiceMock, statusServiceMock, ticketServiceMock);
    }

    @Test
    public void chainShouldWorkWhenNotWinsDueToOverall() {
        //given
        var redeemedToday = 9;
        var winOverall = 100;
        var ticketDto = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();
        var territoryDto = TerritoryDto.builder()
                .winAfter(10)
                .maxWinPerDay(20)
                .maxWinOverall(100)
                .build();
        var statusDto = StatusDto.builder()
                .redeemedToday(redeemedToday)
                .winOverall(winOverall)
                .build();

        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).body(territoryDto).build();
        var statusResult = ServiceResponse.<StatusDto>builder().success(true).body(statusDto).build();
        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(statusServiceMock.findByCountry(HUNGARY)).willReturn(statusResult);
        given(statusServiceMock.updateStatus(statusCaptor.capture())).willReturn(statusResult);

        //when
        underTest.chain(ticketDto);

        //then
        var updatedStatus = statusCaptor.getValue();
        assertThat(updatedStatus.getRedeemedToday(), is(redeemedToday + 1));
        assertThat(updatedStatus.getWinOverall(), is(winOverall));
        verify(territoryServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).updateStatus(any());
        verifyNoMoreInteractions(territoryServiceMock, statusServiceMock, ticketServiceMock);
    }

    @Test
    public void chainShouldWorkWhenNotWinsDueToTodayCounter() {
        //given
        var redeemedToday = 90;
        var winOverall = 99;
        var ticketDto = TicketDto.builder().country(HUNGARY).number(TICKET_NUMBER).build();
        var territoryDto = TerritoryDto.builder()
                .winAfter(10)
                .maxWinPerDay(9)
                .maxWinOverall(100)
                .build();
        var statusDto = StatusDto.builder()
                .redeemedToday(redeemedToday)
                .winOverall(winOverall)
                .build();

        var territoryResult = ServiceResponse.<TerritoryDto>builder().success(true).body(territoryDto).build();
        var statusResult = ServiceResponse.<StatusDto>builder().success(true).body(statusDto).build();
        given(territoryServiceMock.findByCountry(HUNGARY)).willReturn(territoryResult);
        given(statusServiceMock.findByCountry(HUNGARY)).willReturn(statusResult);
        given(statusServiceMock.updateStatus(statusCaptor.capture())).willReturn(statusResult);

        //when
        underTest.chain(ticketDto);

        //then
        var updatedStatus = statusCaptor.getValue();
        assertThat(updatedStatus.getRedeemedToday(), is(redeemedToday + 1));
        assertThat(updatedStatus.getWinOverall(), is(winOverall));
        verify(territoryServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).findByCountry(HUNGARY);
        verify(statusServiceMock).updateStatus(any());
        verifyNoMoreInteractions(territoryServiceMock, statusServiceMock, ticketServiceMock);
    }
}
