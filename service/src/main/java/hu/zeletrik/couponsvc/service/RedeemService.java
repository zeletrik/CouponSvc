package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;

public interface RedeemService {

    ServiceResponse<TicketDto> redeem(TicketDto ticket);
}
