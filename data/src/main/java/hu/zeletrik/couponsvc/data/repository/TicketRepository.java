package hu.zeletrik.couponsvc.data.repository;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByCountry(String country);

    Optional<TicketEntity> findByNumber(String ticket);
}
