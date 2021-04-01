package com.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class Test08Spies {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOSpy;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        // create mocks for all 4 dependencies
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOSpy = spy(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOSpy, mailSenderMock);
    }

    @Test
    public void should_MakeBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1",  LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);


        // when
        String bookingId = bookingService.makeBooking(bookingRequest);

        // then
        // verify that the bookingDAOMock was invoked with the bookingRequest as its parameters
        // bookingDAOMOck will return a nice result of null
        verify(bookingDAOSpy).save(bookingRequest);

    }

    @Test
    public void should_CancelBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1",  LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, true);

        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        // change the behavior of the spy to return the id fro the given section
        doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);

        // when
        bookingService.cancelBooking(bookingId);

    }
}