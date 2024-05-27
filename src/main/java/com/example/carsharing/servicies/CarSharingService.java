package com.example.carsharing.servicies;

import com.example.carsharing.entities.Cars;
import com.example.carsharing.repos.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarSharingService {
    @Autowired
    CarsRepository carsRepository;
    public List<Cars> getAllAvailableCars() {
        return carsRepository.findAll().stream().filter(Cars::isAvailable).toList();
    }
    public List<Cars> getAllCars() {
        return carsRepository.findAll();
    }
}
