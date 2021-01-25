package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.StatusDto;

public interface StatusService {

    ServiceResponse<StatusDto> findByCountry(final String country);

    ServiceResponse<StatusDto> updateStatus(final StatusDto statusDto);

}
