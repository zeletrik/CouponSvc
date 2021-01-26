package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class RedeemStepTest {

    private static final int STEP_PRIORITY = 2;

    @Mock
    private TicketService ticketServiceMock;

    private RedeemChain underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RedeemStep(ticketServiceMock);
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
        var DTO = TicketDto.builder().build();

        var result = ServiceResponse.<TicketDto>builder().success(true).build();
        given(ticketServiceMock.updateTicket(DTO)).willReturn(result);

        //when
        underTest.chain(DTO);

        //then
        verify(ticketServiceMock).updateTicket(DTO);
        verifyNoMoreInteractions(ticketServiceMock);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void chainShouldWorkWhenFail() {
        //given
        var DTO = TicketDto.builder().build();

        var result = ServiceResponse.<TicketDto>builder().success(false).build();
        given(ticketServiceMock.updateTicket(DTO)).willReturn(result);

        //when
        underTest.chain(DTO);

        //then
        // Expected exception
    }
}
