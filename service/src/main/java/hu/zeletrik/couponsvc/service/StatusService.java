package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.StatusDto;

/**
 * Service to handle current status related operations.
 */
public interface StatusService {

    /**
     * Returns the current status for the given country
     *
     * @param country the name of the country
     * @return the related status info wrapped with {@link ServiceResponse}
     */
    ServiceResponse<StatusDto> findByCountry(final String country);

    /**
     * Update a given status
     *
     * @param statusDto the status to update
     * @return the updated status wrapped with {@link ServiceResponse}
     */
    ServiceResponse<StatusDto> updateStatus(final StatusDto statusDto);

}
