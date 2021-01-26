package hu.zeletrik.couponsvc.service.conversion;


import hu.zeletrik.couponsvc.data.entity.StatusEntity;
import hu.zeletrik.couponsvc.service.coversion.StatusDtoToEntityConverter;
import hu.zeletrik.couponsvc.service.dto.StatusDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for {@link StatusDtoToEntityConverter }.
 */
public class StatusDtoToEntityConverterTest {

    private static final long ID = 1L;
    public static final String HUNGARY = "HUNGARY";
    public static final int WIN_OVERALL = 100;

    private StatusDtoToEntityConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new StatusDtoToEntityConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(StatusEntity entity, StatusDto dto) {
        //given

        //when
        var actual = underTest.convert(dto);

        //then
        assertThat(actual, is(entity));
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
