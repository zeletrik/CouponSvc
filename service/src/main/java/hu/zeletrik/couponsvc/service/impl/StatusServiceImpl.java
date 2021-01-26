package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.data.repository.StatusRepository;
import hu.zeletrik.couponsvc.service.StatusService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;
    private final ConversionService conversionService;

    @Override
    public ServiceResponse<StatusDto> findByCountry(String country) {
        LOGGER.info("Retrieve territory by country={}", country);

        return statusRepository.findByCountry(country.toUpperCase())
                .map(entity -> conversionService.convert(entity, StatusDto.class))
                .map(dto -> ServiceResponse.<StatusDto>builder()
                        .success(true)
                        .body(dto)
                        .build())
                .orElse(ServiceResponse.<StatusDto>builder()
                        .success(false)
                        .body(StatusDto.builder().build())
                        .build());
    }

    @Override
    public ServiceResponse<StatusDto> updateStatus(StatusDto statusDto) {
        final var entityToSave = conversionService.convert(statusDto, StatusEntity.class);
        return Objects.nonNull(entityToSave)
                ? ServiceResponse.<StatusDto>builder()
                .success(true)
                .body(conversionService.convert(statusRepository.save(entityToSave), StatusDto.class))
                .build()
                : ServiceResponse.<StatusDto>builder()
                .success(false)
                .body(StatusDto.builder().build())
                .build();
    }
}
