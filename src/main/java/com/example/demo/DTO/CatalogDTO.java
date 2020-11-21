package com.example.demo.DTO;

import com.example.demo.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {

    private Integer id;

    private String imageName;

    private String category;

    private Integer price;

    private String title;

    private String descriptions;

    private LocalDateTime date;

    private String listImage;

    public void setListImageDTO(List<Image> listImage){
        StringBuilder stringBuilder = new StringBuilder();
        for (Image image : listImage){
            stringBuilder.append(image.getImageName());
            if (listImage.size()-1 != listImage.indexOf(image)){
                stringBuilder.append(",");
            }
        }
        if (stringBuilder.length() == 0){
            this.listImage = " ";
        }else {
            this.listImage = stringBuilder.toString();
        }
    }
}
