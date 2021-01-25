package hu.zeletrik.couponsvc.service;

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
public class RedeemService {

    private final List<RedeemChain> processors;

    public void redeem(TicketDto ticket) {
        processors.stream()
                .sorted(Comparator.comparing(RedeemChain::getPriority))
                .forEach(next -> next.chain(ticket));
    }
}
