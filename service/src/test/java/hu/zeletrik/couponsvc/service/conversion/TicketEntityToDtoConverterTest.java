package hu.zeletrik.couponsvc.service.conversion;


import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import hu.zeletrik.couponsvc.service.coversion.TicketEntityToDtoConverter;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for {@link TicketEntityToDtoConverter }.
 */
public class TicketEntityToDtoConverterTest {

    private static final long ID = 1L;
    public static final String HUNGARY = "HUNGARY";
    public static final String EMAIL = "foo@bar.com";

    private TicketEntityToDtoConverter underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new TicketEntityToDtoConverter();
    }

    @Test(dataProvider = "convertProvider")
    public void convertShouldWork(TicketEntity entity, TicketDto dto) {
        //given

        //when
        var actual = underTest.convert(entity);

        //then
        assertThat(actual, is(dto));
    }


    @DataProvider
    private Object[][] convertProvider() {
        var entity = new TicketEntity();
        entity.setCountry(HUNGARY);
        entity.setEmail(EMAIL);
        entity.setId(ID);
        var dto = TicketDto.builder()
                .country(HUNGARY)
                .email(EMAIL)
                .id(ID)
                .build();

        var emptyEntity = new TicketEntity();
        var emptyDto = TicketDto.builder().build();

        var partialEntity = new TicketEntity();
        partialEntity.setCountry(HUNGARY);
        var partialDto = TicketDto.builder()
                .country(HUNGARY)
                .build();

        return new Object[][]{
                {entity, dto},
                {emptyEntity, emptyDto},
                {partialEntity, partialDto},
        };
    }
}
