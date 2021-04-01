package com.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test14StaticMethods {

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
    public void should_CalculateCorrectPrice() {
        try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
            // given
            BookingRequest bookingRequest = new BookingRequest("1",  LocalDate.of(2020, 01, 01),
                    LocalDate.of(2020, 01, 05), 2, false);

            double expected = 400.0;

            // mock the static method
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

            // when
            double actual = bookingService.calculatePriceEuro(bookingRequest);

            // then
            assertEquals(expected, actual);
        }
    }
}