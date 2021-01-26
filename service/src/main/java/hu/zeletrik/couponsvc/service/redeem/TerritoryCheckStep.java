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
import org.springframework.transaction.annotation.Transactional;

/**
 * Check if the ticket wins based on the territory constraints
 */
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
    @Transactional
    public void chain(final TicketDto ticketDto) {
        final var country = ticketDto.getCountry();
        final var territoryDetails = territoryService.findByCountry(country).getBody();
        final var territoryStatus = statusService.findByCountry(country).getBody();
        final var winnersToday = winnersToday(territoryStatus.getRedeemedToday(), territoryDetails.getWinAfter());
        final var isTicketWinner = isWinner(territoryStatus.getRedeemedToday(), territoryDetails.getWinAfter())
                && isTodayAvailable(winnersToday, territoryDetails.getMaxWinPerDay())
                && isOverallAvailable(territoryStatus.getWinOverall(), territoryDetails.getMaxWinOverall());
        if (isTicketWinner) {
            LOGGER.debug("Winner ticket!, number={}", ticketDto.getNumber());
            ticketService.setTicketWinner(ticketDto.getNumber());
        }
        final var updatedStatus = increaseTodayRedeemed(territoryStatus, isTicketWinner);
        statusService.updateStatus(updatedStatus);
    }

    /**
     * Calculates if the ticket is a winner ticket for today
     *
     * @param redeemedToday the number of the currently redeemed tickets
     * @param winAfter      the count of the wins
     * @return true if winner
     */
    private int winnersToday(final int redeemedToday, final int winAfter) {
        return (int) Math.floor((double) redeemedToday / winAfter);
    }

    /**
     * Calculates if today is available day for winning
     *
     * @param winnersToday the current winner count for today
     * @param maxWinPerDay the constraint of the daily winners
     * @return true if today is still valid
     */
    private boolean isTodayAvailable(final int winnersToday, final int maxWinPerDay) {
        return winnersToday < maxWinPerDay;
    }

    /**
     * Calculates if the limit is reached for the overall winners
     *
     * @param overallWinnersCount the count of the overall winners
     * @param overallWinnerLimit  the limit of the overall winners
     * @return true if the limit is not yet reached
     */
    private boolean isOverallAvailable(final int overallWinnersCount, final int overallWinnerLimit) {
        return overallWinnersCount < overallWinnerLimit;
    }

    /**
     * Calculates if the ticket is a winner
     *
     * @param redeemedToday the count of the currently redeemed tickets for today
     * @param winAfter      the count where the ticket wins
     * @return true if the ticket is a winner
     */
    private boolean isWinner(final int redeemedToday, final int winAfter) {
        return (redeemedToday + 1) % winAfter == 0;
    }

    /**
     * Increase redeemed status
     *
     * @param current  the current status
     * @param isWinner true if the ticket won
     * @return the modified status
     */
    private StatusDto increaseTodayRedeemed(final StatusDto current, final boolean isWinner) {
        return StatusDto.builder()
                .id(current.getId())
                .winOverall(
                        isWinner
                                ? current.getWinOverall() + 1
                                : current.getWinOverall()
                )
                .redeemedToday(current.getRedeemedToday() + 1)
                .country(current.getCountry())
                .build();
    }
}
