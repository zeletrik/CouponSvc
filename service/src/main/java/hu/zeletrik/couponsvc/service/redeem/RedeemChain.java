package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.dto.TicketDto;

public interface RedeemChain {

    Integer getPriority();

    void chain(TicketDto ticketDto);
}
