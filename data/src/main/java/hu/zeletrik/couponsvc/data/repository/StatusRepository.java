package hu.zeletrik.couponsvc.data.repository;

import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface StatusRepository extends CrudRepository<StatusEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StatusEntity> findByCountry(String country);
}
