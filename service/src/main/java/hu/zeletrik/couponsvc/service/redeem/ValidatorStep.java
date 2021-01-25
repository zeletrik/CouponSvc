package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidatorStep implements RedeemChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorStep.class);

    private final TicketService ticketService;
    private final TerritoryService territoryService;

    @Override
    public Integer getPriority() {
        return 1;
    }

    @Override
    public void chain(TicketDto ticketDto) {
        var isTerritoryExist = territoryService.findByCountry(ticketDto.getCountry()).isSuccess();
        var isExist = ticketService.findTicketByNumber(ticketDto.getNumber()).isSuccess();
        LOGGER.info("Ticket with number={}, isExist={}, for country={}", ticketDto.getNumber(), isExist, ticketDto.getCountry());
        if (isExist || !isTerritoryExist) {
            throw new RuntimeException("Ticket already redeemed");
        }
    }
}
