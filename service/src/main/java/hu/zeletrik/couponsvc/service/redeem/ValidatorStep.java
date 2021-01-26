package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.exception.NonExistingTerritoryException;
import hu.zeletrik.couponsvc.service.exception.TicketAlreadyExistException;
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
        if (isExist) {
            throw new TicketAlreadyExistException(String.format("Ticket with number %s already redeemed", ticketDto.getNumber()));
        }
        if (!isTerritoryExist) {
            throw new NonExistingTerritoryException(String.format("Territory, %s, not exist or not supported", ticketDto.getCountry()));
        }
    }
}
