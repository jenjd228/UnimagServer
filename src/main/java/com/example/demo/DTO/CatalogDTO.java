package com.example.demo.DTO;

import com.example.demo.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {

    private String hash;

    private String mainImage;

    private String category;

    private Integer price;

    private String title;

    private String descriptions;

    private String date;

    private String listImage;

    public void setListImageDTO(Set<Image> listImage) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Image image : listImage) {
            stringBuilder.append(image.getKey().getImageName()).append(",");
        }

        if (stringBuilder.length() == 0) {
            this.listImage = "";
        } else {
            stringBuilder.delete(stringBuilder.lastIndexOf(","), stringBuilder.length());
            this.listImage = stringBuilder.toString();
        }
    }

    public void setDateByConvertTimeWithTimeZome(long time) {
        Long longD = LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond();
        Instant instant = Instant.ofEpochSecond(longD);

        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM:dd:yyyy:HH:mm:ss");
        this.date = formatter.format(zdt);
    }
}
