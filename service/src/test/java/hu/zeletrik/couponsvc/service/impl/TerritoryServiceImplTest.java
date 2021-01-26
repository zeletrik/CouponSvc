package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import hu.zeletrik.couponsvc.data.repository.TerritoryRepository;
import hu.zeletrik.couponsvc.service.TerritoryService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
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

public class TerritoryServiceImplTest {

    public static final String HUNGARY = "HUNGARY";
    private TerritoryEntity entity;

    @Mock
    private TerritoryRepository territoryRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    private TerritoryService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new TerritoryServiceImpl(territoryRepositoryMock, conversionServiceMock);
        entity = new TerritoryEntity();
    }

    @Test
    public void findByCountryShouldReturnSuccess() {
        //given
        entity.setCountry(HUNGARY);
        var DTO = TerritoryDto.builder().country(HUNGARY).build();

        var expected = ServiceResponse.<TerritoryDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(territoryRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, TerritoryDto.class)).willReturn(DTO);

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(territoryRepositoryMock).findByCountry(HUNGARY);
        verify(conversionServiceMock).convert(entity, TerritoryDto.class);
        verifyNoMoreInteractions(conversionServiceMock);
    }

    @Test
    public void findByCountryShouldWorkWhenDBError() {
        //given
        var DTO = TerritoryDto.builder().build();

        var expected = ServiceResponse.<TerritoryDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(territoryRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.empty());

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(territoryRepositoryMock).findByCountry(HUNGARY);
        verifyNoInteractions(conversionServiceMock);
    }

    @Test
    public void findByCountryShouldWorkWhenConversionError() {
        //given
        entity.setCountry(HUNGARY);
        var DTO = TerritoryDto.builder().build();

        var expected = ServiceResponse.<TerritoryDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(territoryRepositoryMock.findByCountry(HUNGARY)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, TerritoryDto.class)).willReturn(null);

        //when
        var actual = underTest.findByCountry(HUNGARY);

        //then
        assertThat(actual, is(expected));
        verify(territoryRepositoryMock).findByCountry(HUNGARY);
        verify(conversionServiceMock).convert(entity, TerritoryDto.class);
        verifyNoMoreInteractions(conversionServiceMock);
    }
}
