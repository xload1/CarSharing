package com.example.carsharing.servicies;

import com.example.carsharing.entities.Bookings;
import com.example.carsharing.entities.Cars;
import com.example.carsharing.repos.BookingsRepository;
import com.example.carsharing.repos.CarsRepository;
import com.example.carsharing.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    BookingsRepository bookingsRepository;
    CarsRepository carsRepository;
    UsersRepository usersRepository;
    @Autowired
    public ReservationService(BookingsRepository bookingsRepository, CarsRepository carsRepository, UsersRepository usersRepository) {
        this.bookingsRepository = bookingsRepository;
        this.carsRepository = carsRepository;
        this.usersRepository = usersRepository;
    }

    public String reserveCar(int car_id, int user_id, int hours) {
        var car = carsRepository.findById(car_id).orElse(null);
        var user = usersRepository.findById(user_id).orElse(null);
        if (car == null || user == null || !car.isAvailable()) {
            return "Reservation failed";
        }
        car.setAvailable(false);
        carsRepository.save(car);
        bookingsRepository.save(new Bookings(user.getUserid(), car.getCar_id(), LocalDateTime.now().plusHours(hours).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
        return "Reservation successful";
    }
    public List<Bookings> getBookings(int user_id) {
        return bookingsRepository.findAllByUserid(user_id);
    }
    public void cancelBooking(int booking_id) {

        try {
            Cars car = carsRepository.findById(Objects.requireNonNull(bookingsRepository.findById(booking_id).orElse(null)).getCar_id()).orElse(null);
            assert car != null;
            car.setAvailable(true);
            carsRepository.save(car);
            bookingsRepository.deleteById(booking_id);
        } catch (NullPointerException e) {
            System.out.println("Booking not found");
        }
    }
    public void removeExpiredBookings() {
        List<Bookings> bookings = bookingsRepository.findAll();
        for (Bookings booking : bookings) {
            if (LocalDateTime.now().isAfter(LocalDateTime.parse(booking.getTo(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))) {
                cancelBooking(booking.getBooking_id());
            }
        }
    }
}
