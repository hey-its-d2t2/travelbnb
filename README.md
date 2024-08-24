### TravelBnB - Hotel Booking System
TravelBnB is a backend-only hotel booking system developed using Java and Spring Boot. This project offers a comprehensive set of RESTful APIs that manage hotel bookings, room searches, user reservations, and more. It also includes advanced features like secure user authentication, media uploads, and communication integrations. The system is thoroughly tested using Postman to ensure reliability and performance.

## Features
- **User Registration and Secure Authentication:** Users can register and log in securely using Spring Security, which handles authentication and authorization processes.

- **Hotel Management:** Admins can add, update, and delete hotel information, including details such as location, room types, pricing, availability, and photos.

- **Room Search:** Users can search for available rooms by location, check-in/check-out dates, room type, and price range.

- **Booking Management:** Users can book rooms, view booking history, and cancel reservations. Booking confirmations are sent to users via email, SMS, or WhatsApp.

- **Review and Rating System:** Users can leave reviews and ratings for hotels they have stayed in, helping future guests make informed decisions.

- **Media Uploads:** Admins can upload hotel photos and other related media files to AWS S3 for secure and scalable storage.

- ** PDF Generation and Upload:** The system can generate booking invoices and other important documents in PDF format and upload them to AWS for easy access and download.

- **Automated Notifications:** Users receive notifications via email, WhatsApp, or SMS for booking confirmations, cancellations, and special offers.

- **Admin Dashboard:** Admins have access to a dashboard where they can manage all aspects of the hotels, bookings, user activity, and system settings.

-**Pricing and Discounts:** Supports dynamic pricing models and discount codes that can be applied during booking.

- **Data Security:** Sensitive data is protected using industry-standard encryption and security practices, ensuring user data remains confidential.

## Technologies Used
- **Java:** Core programming language for building the application.
- **Spring Boot:** Framework for developing the backend services, including RESTful APIs.
- **Spring Security:** For secure user authentication and authorization.
- **Hibernate/JPA:** ORM tool for managing database interactions.
- **MySQL:** Relational database for storing user, hotel, and booking information.
- **AWS S3:** For secure storage and retrieval of hotel photos, PDFs, and other media.
- **Postman:** Used for testing the APIs to ensure functionality and reliability.
- **Twilio/WhatsApp API:** For sending SMS and WhatsApp notifications to users.
- **JavaMail API:** For sending email notifications to users.
## Testing
The backend has been thoroughly tested with Postman, covering all core features, including user authentication, room search, booking, media uploads, and notification systems.
