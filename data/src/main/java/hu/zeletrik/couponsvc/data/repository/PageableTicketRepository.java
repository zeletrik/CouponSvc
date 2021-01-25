package hu.zeletrik.couponsvc.data.repository;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PageableTicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByWinnerTrueAndCountryAndTimestampBetween(final String country, final Instant startDate, final Instant endDate, final Pageable pageable);

}
