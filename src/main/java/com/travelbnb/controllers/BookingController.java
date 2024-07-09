package com.travelbnb.controllers;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Booking;
import com.travelbnb.entity.Property;
import com.travelbnb.repository.BookingRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/bookings")
public class BookingController {

    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;

    public BookingController(PropertyRepository propertyRepository, BookingRepository bookingRepository) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user,
            @RequestBody Booking booking
            ) {
        Property property = propertyRepository.findById(propertyId).get();
        int nightlyPrice = property.getPrice();
        int totalPrice = nightlyPrice*booking.getTotalNights();
        booking.setProperty(property);
        booking.setAppUser(user);
        booking.setPrice(totalPrice);

        return  new ResponseEntity<>(bookingRepository.save(booking), HttpStatus.CREATED);
    }
}
