package com.travelbnb.controllers;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Booking;
import com.travelbnb.entity.Property;
import com.travelbnb.repository.BookingRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.PDFService;
import com.travelbnb.service.S3Service;
import com.travelbnb.service.SmsSenderService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final S3Service s3Service;
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private final PDFService pdfService;
    private SmsSenderService smsSenderService;


    public BookingController(PropertyRepository propertyRepository, BookingRepository bookingRepository, PDFService pdfService, S3Service s3Service, SmsSenderService smsSenderService) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.pdfService = pdfService;
        this.s3Service = s3Service;
        this.smsSenderService = smsSenderService;
    }

    @PostMapping("/newBooking")
    public ResponseEntity<Booking> createBooking(
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user,
            @RequestBody Booking booking
    ) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        int nightlyPrice = property.getPrice();
        int totalPrice = nightlyPrice * booking.getTotalNights();
        booking.setProperty(property);
        booking.setAppUser(user);
        booking.setPrice(totalPrice);
        Booking savedBooking = bookingRepository.save(booking);

        String pdfFilePath = "D://travelbnb//" + savedBooking.getName() + "-Confirmation" + savedBooking.getId() + ".pdf";
        Boolean isPdfGenerated = pdfService.generatePDF(pdfFilePath, savedBooking);

        // Upload file to S3
        if (isPdfGenerated) {
            try {
                MultipartFile file = convertToMultipartFile(pdfFilePath);
                String uploadFileUrl = s3Service.uploadFile(file, "travelbnb");
                System.out.println(uploadFileUrl);
               String smsId =  smsSenderService.sendSms(booking.getMobile(),
                        "Your booking is confirmed. Click here for more details: "+uploadFileUrl);
                System.out.println(smsId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    private MultipartFile convertToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public Resource getResource() {
                return MultipartFile.super.getResource();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }

            @Override
            public void transferTo(Path dest) throws IOException, IllegalStateException {
                MultipartFile.super.transferTo(dest);
            }
        };
        input.close();
        return multipartFile;
    }
}
