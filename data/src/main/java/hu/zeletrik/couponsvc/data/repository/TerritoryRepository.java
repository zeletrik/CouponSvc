package hu.zeletrik.couponsvc.data.repository;

import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerritoryRepository extends CrudRepository<TerritoryEntity, Long> {

    Optional<TerritoryEntity> findByCountry(String country);
}
