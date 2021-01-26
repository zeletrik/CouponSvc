package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;

/**
 * Service to handle ticket redeem
 */
public interface RedeemService {

    /**
     * Redeem a given ticket
     *
     * @param ticket the ticket to redeem
     * @return the redeemed ticket wrapped with {@link ServiceResponse}
     */
    ServiceResponse<TicketDto> redeem(TicketDto ticket);
}
