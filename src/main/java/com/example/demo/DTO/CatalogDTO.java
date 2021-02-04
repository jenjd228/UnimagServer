package com.example.demo.DTO;

import com.example.demo.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private Long date;

    private String listImage;

    public void setListImageDTO(Set<Image> listImage){
        List<Image> list = (ArrayList) listImage;
        StringBuilder stringBuilder = new StringBuilder();
        for (Image image : list){
            stringBuilder.append(image.getKey().getImageName());
            if (listImage.size()-1 != list.indexOf(image)){
                stringBuilder.append(",");
            }
        }
        if (stringBuilder.length() == 0){
            this.listImage = "";
        }else {
            this.listImage = stringBuilder.toString();
        }
    }
}
