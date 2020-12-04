package com.example.demo.DTO;

import com.example.demo.Model.Order2Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Integer orderId;

    private String dataOfOrder;

    private String status;

    private String pickUpPoint; //Пункт выдачи

    private List<Order2ProductDTO> order2ProductsList;

    public void setLocalDate(Long time){
        this.dataOfOrder = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.of("UTC"))
                .withZoneSameLocal(ZoneId.of("CET"))
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss"));
    }

}
