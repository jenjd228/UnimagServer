package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pick_up_point")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickUpPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "pick_up_point")
    private String pickUpPoint;
}
