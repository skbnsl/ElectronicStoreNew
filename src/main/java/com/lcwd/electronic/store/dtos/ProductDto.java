package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Category;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

    private String productId;
    private String title;

    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

    private String prodctImageName;

    private CategoryDto category;
}
