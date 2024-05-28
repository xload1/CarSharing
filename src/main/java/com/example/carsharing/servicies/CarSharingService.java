package com.example.carsharing.servicies;

import com.example.carsharing.entities.Bookings;
import com.example.carsharing.entities.Cars;
import com.example.carsharing.repos.BookingsRepository;
import com.example.carsharing.repos.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CarSharingService {
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    BookingsRepository bookingsRepository;
    public List<Cars> getAllAvailableCars() {
        return carsRepository.findAll().stream().filter(Cars::isAvailable).toList();
    }
    public List<Cars> getAllCars() {
        return carsRepository.findAll().stream().sorted(Comparator.comparing(Cars::isAvailable).reversed()).toList();
    }
    public Cars getCar(int id) {
        return carsRepository.findById(id).orElse(null);
    }
    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }
    public void addCar(Cars car) {
        carsRepository.save(car);
    }
    public void removeCar(int id) {
        carsRepository.deleteById(id);
    }
}
