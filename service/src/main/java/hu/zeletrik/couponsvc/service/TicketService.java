package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;

import java.util.List;

/**
 * Service to handle ticket related operations.
 */
public interface TicketService {

    /**
     * Retrieve all data from a ticket based on the number
     *
     * @param ticketNumber the number of the ticket to retrieve
     * @return the given ticket wrapped with a {@link ServiceResponse}
     */
    ServiceResponse<TicketDto> findTicketByNumber(final String ticketNumber);

    /**
     * Find the winners with a given constraints
     *
     * @param winnersPageWrapper contains the country, dates etc
     * @return the list of the winners
     */
    List<TicketDto> findWinners(final WinnersPageWrapper winnersPageWrapper);

    /**
     * Pass a dto to  upsert it.
     *
     * @param ticketDto the dto to update
     * @return the given ticket wrapped with a {@link ServiceResponse}
     */
    ServiceResponse<TicketDto> updateTicket(final TicketDto ticketDto);

    /**
     * Make a ticket winner based on the ticket number
     *
     * @param ticketNumber the number of the ticket to update
     * @return the given ticket wrapped with a {@link ServiceResponse}
     */
    ServiceResponse<TicketDto> setTicketWinner(final String ticketNumber);

}
