package com.example.demo.web.model;

import com.example.demo.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Image image;

    private boolean isDelete;

}
