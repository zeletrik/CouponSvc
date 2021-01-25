package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;

import java.util.List;

public interface TicketService {

    ServiceResponse<TicketDto> findTicketByNumber(final String ticketNumber);

    List<TicketDto> findWinners(final WinnersPageWrapper winnersPageWrapper);

    ServiceResponse<TicketDto> updateTicket(final TicketDto ticketDto);

    ServiceResponse<TicketDto> setTicketWinner(final String ticketNumber);

}
