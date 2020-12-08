package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Integer orderId;

    private String dataOfOrder;

    private String status;

    private String pickUpPoint;

    private List<Order2ProductDTO> order2ProductsList;

    public void setLocalDate(Long time){
        this.dataOfOrder = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.of("UTC"))
                .withZoneSameLocal(ZoneId.of("CET"))
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss"));
    }

}
