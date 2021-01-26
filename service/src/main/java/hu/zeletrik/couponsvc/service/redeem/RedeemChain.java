package hu.zeletrik.couponsvc.service.redeem;

import hu.zeletrik.couponsvc.service.dto.TicketDto;

/**
 * Process base to provide a chain for execution
 */
public interface RedeemChain {

    /**
     * The priority of the related service
     *
     * @return the priority
     */
    Integer getPriority();

    /**
     * Processes a ticket in a chain
     *
     * @param ticketDto the processable ticket
     */
    void chain(TicketDto ticketDto);
}
