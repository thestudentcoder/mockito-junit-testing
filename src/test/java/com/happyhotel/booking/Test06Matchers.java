package com.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

class Test06Matchers {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        // create mocks for all 4 dependencies
        this.paymentServiceMock = mock(PaymentService.class);
        // mock will return an empty list
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_NotCompleteBooking_When_PriceTooHigh() {
        // given
        BookingRequest bookingRequest = new BookingRequest("2",  LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);

        // when
        // Throw exception from a mock
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(BusinessException.class, executable);
    }
}