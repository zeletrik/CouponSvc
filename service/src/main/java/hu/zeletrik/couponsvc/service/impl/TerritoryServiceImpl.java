package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import hu.zeletrik.couponsvc.data.repository.TerritoryRepository;
import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class TerritoryServiceImpl implements TerritoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerritoryServiceImpl.class);

    private final TerritoryRepository territoryRepository;
    private final ConversionService conversionService;

    public TerritoryServiceImpl(TerritoryRepository territoryRepository, ConversionService conversionService) {
        this.territoryRepository = territoryRepository;
        this.conversionService = conversionService;
    }

    @Override
    public ServiceResponse<TerritoryDto> findByCountry(String country) {
        LOGGER.info("Retrieve territory by country={}", country);

        return territoryRepository.findByCountry(country.toUpperCase())
                .map(entity -> conversionService.convert(entity, TerritoryDto.class))
                .map(dto -> ServiceResponse.<TerritoryDto>builder()
                        .success(true)
                        .body(dto)
                        .build())
                .orElse(ServiceResponse.<TerritoryDto>builder()
                        .success(false)
                        .body(TerritoryDto.builder().build())
                        .build());
    }
}
