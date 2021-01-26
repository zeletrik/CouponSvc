package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.data.repository.StatusRepository;
import hu.zeletrik.couponsvc.service.StatusService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class StatusServiceImplTest {

    public static final String HUNGARY = "HUNGARY";
    private StatusEntity entity;

    @Mock
    private StatusRepository statusRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    private StatusService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StatusServiceImpl(statusRepositoryMock, conversionServiceMock);
        entity = new StatusEntity();
    }

    @Test
    public void findByCountryReturnSuccess() {
        //given
        entity.setCountry(HUNGARY);
        var DTO = StatusDto.builder().country(HUNGARY).build();

        var expected = ServiceResponse.<StatusDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(statusRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, StatusDto.class)).willReturn(DTO);

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(statusRepositoryMock).findByCountry(HUNGARY);
        verify(conversionServiceMock).convert(entity, StatusDto.class);
        verifyNoMoreInteractions(conversionServiceMock);
    }

    @Test
    public void findByCountryFailWhenDBError() {
        //given
        var DTO = StatusDto.builder().build();

        var expected = ServiceResponse.<StatusDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(statusRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.empty());

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(statusRepositoryMock).findByCountry(HUNGARY);
        verifyNoMoreInteractions(statusRepositoryMock);
        verifyNoInteractions(conversionServiceMock);
    }

    @Test
    public void findByCountryFailWhenConversionError() {
        //given
        entity.setCountry(HUNGARY);
        var DTO = StatusDto.builder().build();

        var expected = ServiceResponse.<StatusDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(statusRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, StatusDto.class)).willReturn(null);

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(statusRepositoryMock).findByCountry(HUNGARY);
        verify(conversionServiceMock).convert(entity, StatusDto.class);
        verifyNoMoreInteractions(statusRepositoryMock, conversionServiceMock);
    }

    @Test
    public void updateStatusShouldReturnSuccess() {
        //given
        entity.setCountry(HUNGARY);
        var DTO = StatusDto.builder().country(HUNGARY).build();

        var expected = ServiceResponse.<StatusDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(conversionServiceMock.convert(DTO, StatusEntity.class)).willReturn(entity);
        given(conversionServiceMock.convert(entity, StatusDto.class)).willReturn(DTO);
        given(statusRepositoryMock.save(entity)).willReturn(entity);

        //when
        var actual = underTest.updateStatus(DTO);

        //then
        assertThat(actual, is(expected));
        verify(conversionServiceMock).convert(DTO, StatusEntity.class);
        verify(statusRepositoryMock).save(entity);
        verify(conversionServiceMock).convert(entity, StatusDto.class);
        verifyNoMoreInteractions(statusRepositoryMock, conversionServiceMock);
    }

    @Test
    public void updateStatusShouldWorkWhenConversionError() {
        //given
        var DTO = StatusDto.builder().build();

        var expected = ServiceResponse.<StatusDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(conversionServiceMock.convert(DTO, StatusEntity.class)).willReturn(null);

        //when
        var actual = underTest.updateStatus(DTO);

        //then
        assertThat(actual, is(expected));
        verify(conversionServiceMock).convert(DTO, StatusEntity.class);
        verifyNoMoreInteractions(conversionServiceMock);
        verifyNoInteractions( statusRepositoryMock);
    }

}
