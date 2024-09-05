package com.petproject.app.service;

import com.petproject.app.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();
}
