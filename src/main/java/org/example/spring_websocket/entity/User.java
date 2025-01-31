package org.example.spring_websocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import org.example.spring_websocket.enums.Gender;

@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Id
    Long id;

    String email;

    String nickname;

    String name;

    @Enumerated(EnumType.STRING)
    Gender gender;

    String phoneNumber;

    LocalDate birthDay;

    String role;

}
