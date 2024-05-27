package com.example.carsharing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int userid;
//    @ColumnDefault("No name")
    @Column(name = "name")
    String name;
//    @ColumnDefault("No contact info")
    String contact;
//    @ColumnDefault("false")
    @Column(name = "is_admin")
    boolean isAdmin;
}
