package com.example.carsharing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int booking_id;
    @Column(name = "user_id")
    int userid;
    int car_id;
    @Column(name = "fromd")
    String from;
    @Column(name = "tod")
    String to;

    public Bookings(int userid, int car_id, String to) {
        this.userid = userid;
        this.car_id = car_id;
        this.from = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        this.to = to;
    }
}
