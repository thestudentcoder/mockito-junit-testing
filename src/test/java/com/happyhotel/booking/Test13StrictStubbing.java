package com.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

    // injects the Mocks annotated with the @Mock annotation and @Spy as well
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Spy
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    // define captors
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Captor
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

    @Test
    public void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1",  LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, false);

        // this will not be invoked when prepaid is false
        // strict stubbing enforced
        // means you need to use the mock methods you defined in a given section or else exception will be thrown
        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");

        // when
        bookingService.makeBooking(bookingRequest);

        // then
       // no exception is thrown
    }

}