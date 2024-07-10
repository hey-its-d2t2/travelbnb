package com.travelbnb.controllers;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Booking;
import com.travelbnb.entity.Property;
import com.travelbnb.repository.BookingRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.PDFService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;
    private PDFService pdfService;

    public BookingController(PropertyRepository propertyRepository, BookingRepository bookingRepository,PDFService pdfService) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfService = pdfService;
    }

    @PostMapping("/newBooking")
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
        Booking savedBooking = bookingRepository.save(booking);

        pdfService.generatePDF("D://travelbnb//"+"-Confirmation"+".pdf",savedBooking);

        return  new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }
}
