package hu.zeletrik.couponsvc.service.conversion;


import hu.zeletrik.couponsvc.data.entity.TerritoryEntity;
import hu.zeletrik.couponsvc.service.coversion.TerritoryEntityToDtoConverter;
import hu.zeletrik.couponsvc.service.dto.TerritoryDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for {@link TerritoryEntityToDtoConverter }.
 */
public class TerritoryEntityToDtoConverterTest {

    private static final long ID = 1L;
    public static final String HUNGARY = "HUNGARY";
    public static final int WIN_OVERALL = 100;

    private TerritoryEntityToDtoConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new TerritoryEntityToDtoConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(TerritoryEntity entity, TerritoryDto dto) {
        //given

        //when
        var actual = underTest.convert(entity);

        //then
        assertThat(actual, is(dto));
    }


    @DataProvider
    private Object[][] convertProvider() {
        var entity = new TerritoryEntity();
        entity.setCountry(HUNGARY);
        entity.setMaxWinOverall(WIN_OVERALL);
        entity.setId(ID);
        var dto = TerritoryDto.builder()
                .country(HUNGARY)
                .maxWinOverall(WIN_OVERALL)
                .id(ID)
                .build();

        var emptyEntity = new TerritoryEntity();
        var emptyDto = TerritoryDto.builder().build();

        var partialEntity = new TerritoryEntity();
        partialEntity.setCountry(HUNGARY);
        var partialDto = TerritoryDto.builder()
                .country(HUNGARY)
                .build();

        return new Object[][]{
                {entity, dto},
                {emptyEntity, emptyDto},
                {partialEntity, partialDto},
        };
    }
}
