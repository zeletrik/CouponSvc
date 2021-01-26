package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.service.RedeemService;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.redeem.RedeemChain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RedeemServiceFacade implements RedeemService {

    private final List<RedeemChain> processors;
    private final TicketService ticketService;

    @Override
    public ServiceResponse<TicketDto> redeem(TicketDto ticket) {
        processors.stream()
                .sorted(Comparator.comparing(RedeemChain::getPriority))
                .forEach(next -> next.chain(ticket));

        return ticketService.findTicketByNumber(ticket.getNumber());
    }
}
