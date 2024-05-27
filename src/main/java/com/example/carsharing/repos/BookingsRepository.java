package com.example.carsharing.repos;

import com.example.carsharing.entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
}
