package com.travelbnb.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelbnb.entity.Booking;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
@Service
public class PDFService {
    //private final String PDF_DIR = "D://travelbnb//";
    public boolean generatePDF(String filename, Booking booking) {
        try {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        Chunk bookingConfirmation = new Chunk("Booking Confirmation", font);
        Chunk guestName = new Chunk("Guest Name : "+booking.getName(),font);
        Chunk totalNights = new Chunk("Price per night: "+booking.getTotalNights(),font);
        Chunk totalPrice = new Chunk("Total Price : "+booking.getPrice());


        document.add(bookingConfirmation);
        document.add(new Paragraph("\n"));
        document.add(guestName);
        document.add(new Paragraph("\n"));
        document.add(totalNights);
        document.add(new Paragraph("\n"));
        document.add(totalPrice);

        document.close();
        return  true;
    }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
