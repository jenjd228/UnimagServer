package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "data_of_order")
    private Long dataOfOrder;

    private String status;

    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "order_id",insertable=false, updatable=false)
    private List<Order2Product> order2ProductsList;

}
