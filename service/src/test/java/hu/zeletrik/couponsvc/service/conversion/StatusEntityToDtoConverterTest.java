package hu.zeletrik.couponsvc.service.conversion;


import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.service.coversion.StatusEntityToDtoConverter;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for {@link StatusEntityToDtoConverter }.
 */
public class StatusEntityToDtoConverterTest {

    private static final long ID = 1L;
    public static final String HUNGARY = "HUNGARY";
    public static final int WIN_OVERALL = 100;

    private StatusEntityToDtoConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new StatusEntityToDtoConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(StatusEntity entity, StatusDto dto) {
        //given

        //when
        var actual = underTest.convert(entity);

        //then
        assertThat(actual, is(dto));
    }


    @DataProvider
    private Object[][] convertProvider() {
        var entity = new StatusEntity();
        entity.setCountry(HUNGARY);
        entity.setWinOverall(WIN_OVERALL);
        entity.setId(ID);
        var dto = StatusDto.builder()
                .country(HUNGARY)
                .winOverall(WIN_OVERALL)
                .id(ID)
                .build();

        var emptyEntity = new StatusEntity();
        var emptyDto = StatusDto.builder().build();

        var partialEntity = new StatusEntity();
        partialEntity.setCountry(HUNGARY);
        var partialDto = StatusDto.builder()
                .country(HUNGARY)
                .build();

        return new Object[][]{
                {entity, dto},
                {emptyEntity, emptyDto},
                {partialEntity, partialDto},
        };
    }
}
