package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "id", insertable=false, updatable=false)
    private List<BasketProduct> basketProducts;

    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<Orders> ordersList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_2_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Orders> roles;

    public void addPoints(Integer points) {
        this.points += points;
    }
}
