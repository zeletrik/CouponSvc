package hu.zeletrik.couponsvc.service.impl;

import hu.zeletrik.couponsvc.data.entity.TicketEntity;
import hu.zeletrik.couponsvc.data.repository.PageableTicketRepository;
import hu.zeletrik.couponsvc.data.repository.TicketRepository;
import hu.zeletrik.couponsvc.service.TicketService;
import hu.zeletrik.couponsvc.service.dto.ServiceResponse;
import hu.zeletrik.couponsvc.service.dto.TicketDto;
import hu.zeletrik.couponsvc.service.dto.WinnersPageWrapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TicketServiceImplTest {

    private static final String TICKET_NUMBER = "ABCDEFGHIJK";
    public static final String HUNGARY = "HUNGARY";
    private TicketEntity entity;

    @Mock
    private TicketRepository ticketRepositoryMock;

    @Mock
    private PageableTicketRepository pageableTicketRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    private TicketService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new TicketServiceImpl(ticketRepositoryMock, pageableTicketRepositoryMock, conversionServiceMock);
        entity = new TicketEntity();
    }

    @Test
    public void findTicketByNumberReturnSuccess() {
        //given
        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().number(TICKET_NUMBER).build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(DTO);

        //when
        var actual = underTest.findTicketByNumber(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(ticketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock);
    }

    @Test
    public void findTicketByNumberFailWhenDBError() {
        //given
        var DTO = TicketDto.builder().build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.empty());

        //when
        var actual = underTest.findTicketByNumber(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verifyNoMoreInteractions(ticketRepositoryMock);
        verifyNoInteractions(pageableTicketRepositoryMock, conversionServiceMock);
    }

    @Test
    public void findTicketByNumberFailWhenConversionError() {
        //given
        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.of(entity));
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(null);

        //when
        var actual = underTest.findTicketByNumber(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(ticketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock);
    }


    @Test
    public void findWinnersShouldWork() {
        //given
        var date = Instant.now();
        var page = PageRequest.of(1, 1);
        var wrapper = WinnersPageWrapper
                .builder()
                .country(HUNGARY)
                .startDate(date)
                .endDate(date)
                .pageNumber(1)
                .pageSize(1)
                .build();

        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().number(TICKET_NUMBER).build();

        var expected = List.of(DTO);

        given(pageableTicketRepositoryMock.findByWinnerTrueAndCountryAndTimestampBetween(
                HUNGARY, date, date, page
        )).willReturn(List.of(entity));
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(DTO);

        //when
        var actual = underTest.findWinners(wrapper);

        //then
        assertThat(actual, is(expected));
        verify(pageableTicketRepositoryMock).findByWinnerTrueAndCountryAndTimestampBetween(HUNGARY, date, date, page);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(pageableTicketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(ticketRepositoryMock);
    }

    @Test
    public void findWinnersShouldWorkWhenNoMatch() {
        //given
        var date = Instant.now();
        var page = PageRequest.of(1, 1);
        var wrapper = WinnersPageWrapper
                .builder()
                .country(HUNGARY)
                .startDate(date)
                .endDate(date)
                .pageNumber(1)
                .pageSize(1)
                .build();

        var expected = Collections.emptyList();

        given(pageableTicketRepositoryMock.findByWinnerTrueAndCountryAndTimestampBetween(
                HUNGARY, date, date, page
        )).willReturn(Collections.emptyList());
        //when
        var actual = underTest.findWinners(wrapper);

        //then
        assertThat(actual, is(expected));
        verify(pageableTicketRepositoryMock).findByWinnerTrueAndCountryAndTimestampBetween(HUNGARY, date, date, page);
        verifyNoMoreInteractions(pageableTicketRepositoryMock);
        verifyNoInteractions(ticketRepositoryMock, conversionServiceMock);
    }

    @Test
    public void updateTicketShouldReturnSuccess() {
        //given
        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().number(TICKET_NUMBER).build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(conversionServiceMock.convert(DTO, TicketEntity.class)).willReturn(entity);
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(DTO);
        given(ticketRepositoryMock.save(entity)).willReturn(entity);

        //when
        var actual = underTest.updateTicket(DTO);

        //then
        assertThat(actual, is(expected));
        verify(conversionServiceMock).convert(DTO, TicketEntity.class);
        verify(ticketRepositoryMock).save(entity);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(ticketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock);
    }

    @Test
    public void updateTicketShouldWorkWhenConversionError() {
        //given
        var DTO = TicketDto.builder().build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(conversionServiceMock.convert(DTO, TicketEntity.class)).willReturn(null);

        //when
        var actual = underTest.updateTicket(DTO);

        //then
        assertThat(actual, is(expected));
        verify(conversionServiceMock).convert(DTO, TicketEntity.class);
        verifyNoMoreInteractions(conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock, ticketRepositoryMock);
    }

    @Test
    public void setTicketWinnerShouldReturnSuccess() {
        //given
        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().number(TICKET_NUMBER).winner(true).build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(true)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.of(entity));
        given(ticketRepositoryMock.save(entity)).willReturn(entity);
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(DTO);

        //when
        var actual = underTest.setTicketWinner(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verify(ticketRepositoryMock).save(entity);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(ticketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock);
    }

    @Test
    public void setTicketWinnerShouldWorkWhenDBError() {
        //given
        var DTO = TicketDto.builder().build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.empty());

        //when
        var actual = underTest.setTicketWinner(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verifyNoMoreInteractions(ticketRepositoryMock);
        verifyNoInteractions(pageableTicketRepositoryMock, conversionServiceMock);
    }


    @Test
    public void setTicketWinnerShoulWorkWhenConversionError() {
        //given
        entity.setNumber(TICKET_NUMBER);
        var DTO = TicketDto.builder().build();

        var expected = ServiceResponse.<TicketDto>builder()
                .success(false)
                .body(DTO)
                .build();

        given(ticketRepositoryMock.findByNumber(TICKET_NUMBER)).willReturn(Optional.of(entity));
        given(ticketRepositoryMock.save(entity)).willReturn(entity);
        given(conversionServiceMock.convert(entity, TicketDto.class)).willReturn(null);

        //when
        var actual = underTest.setTicketWinner(TICKET_NUMBER);

        //then
        assertThat(actual, is(expected));
        verify(ticketRepositoryMock).findByNumber(TICKET_NUMBER);
        verify(ticketRepositoryMock).save(entity);
        verify(conversionServiceMock).convert(entity, TicketDto.class);
        verifyNoMoreInteractions(ticketRepositoryMock, conversionServiceMock);
        verifyNoInteractions(pageableTicketRepositoryMock);
    }
}
