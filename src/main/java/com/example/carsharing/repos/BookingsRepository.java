package com.example.carsharing.repos;

import com.example.carsharing.entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    List<Bookings> findAllByUserid(int userid);
}
