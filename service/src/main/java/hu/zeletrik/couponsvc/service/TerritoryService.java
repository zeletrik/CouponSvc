package hu.zeletrik.couponsvc.service;

import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;

public interface TerritoryService {

    ServiceResponse<TerritoryDto> findByCountry(final String country);
}
