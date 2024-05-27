package com.example.carsharing.repos;

import com.example.carsharing.entities.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Cars, Integer> {
}
