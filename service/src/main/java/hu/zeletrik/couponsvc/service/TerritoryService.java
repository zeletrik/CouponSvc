package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;

/**
 * Service to handle territory related operations.
 */
public interface TerritoryService {

    /**
     * Retrieves a territory details based on the country name
     *
     * @param country the name of the country
     * @return the given territory details wrapped with a {@link ServiceResponse}
     */
    ServiceResponse<TerritoryDto> findByCountry(final String country);
}
