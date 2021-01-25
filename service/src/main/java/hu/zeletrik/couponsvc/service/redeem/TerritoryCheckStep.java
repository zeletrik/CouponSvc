package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.StatusService;
import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TerritoryCheckStep implements RedeemChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerritoryCheckStep.class);

    private final TicketService ticketService;
    private final TerritoryService territoryService;
    private final StatusService statusService;

    @Override
    public Integer getPriority() {
        return 3;
    }

    @Override
    public void chain(final TicketDto ticketDto) {
        LOGGER.info("Determine if ticket wins");
        final var country = ticketDto.getCountry();
        final var territoryDetails = territoryService.findByCountry(country).getBody();
        final var territoryStatus = statusService.findByCountry(country).getBody();
        final var winnersToday = winnersToday(territoryStatus.getRedeemedToday(), territoryDetails.getWinAfter());
        final var isTicketWinner = isWinner(territoryStatus.getRedeemedToday(), territoryDetails.getWinAfter())
                && isTodayAvailable(winnersToday, territoryDetails.getMaxWinPerDay())
                && isOverallAvailable(winnersToday, territoryStatus.getWinOverall(), territoryDetails.getMaxWinOverall());
        if (isTicketWinner) {
            LOGGER.info("Winner ticket!");
            ticketService.setTicketWinner(ticketDto.getNumber());
        }
        final var updatedStatus = increaseTodayRedeemed(territoryStatus, isTicketWinner);
        statusService.updateStatus(updatedStatus);
    }

    private int winnersToday(final int redeemedToday, final int winAfter) {
        return (int) Math.floor((double) redeemedToday / winAfter);
    }

    private boolean isTodayAvailable(final int winnersToday, final int maxWinPerDay) {
        return winnersToday < maxWinPerDay;
    }

    private boolean isOverallAvailable(final int winnersToday, final int overallWinnersCount, final int overallWinnerLimit) {
        return winnersToday + overallWinnersCount < overallWinnerLimit;
    }

    private boolean isWinner(final int redeemedToday, final int winAfter) {
        return (redeemedToday + 1) % winAfter == 0;
    }

    private StatusDto increaseTodayRedeemed(final StatusDto actual, final boolean isWinner) {
        return StatusDto.builder()
                .id(actual.getId())
                .winOverall(
                        isWinner
                                ? actual.getWinOverall() + 1
                                : actual.getWinOverall()
                )
                .redeemedToday(actual.getRedeemedToday() + 1)
                .country(actual.getCountry())
                .build();
    }
}
