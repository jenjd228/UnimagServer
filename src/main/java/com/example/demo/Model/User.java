package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String fio;

    private String birthday;

    private Integer points;

    private String university;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "max_date")
    private Date maxDate;

    @Column(name = "secure_kod")
    private String secureKod;
}
