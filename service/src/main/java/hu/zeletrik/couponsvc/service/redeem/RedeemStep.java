package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedeemStep implements RedeemChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorStep.class);

    private final TicketService ticketService;

    @Override
    public Integer getPriority() {
        return 2;
    }

    @Override
    public void chain(TicketDto ticketDto) {
        LOGGER.debug("Redeeming ticket with number={}", ticketDto.getNumber());
        final var redeemResponse = ticketService.updateTicket(ticketDto);

        if (!redeemResponse.isSuccess()) {
            throw new RuntimeException("Ticket redeem failed");
        }
    }
}
