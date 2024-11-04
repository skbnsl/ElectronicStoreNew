package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.Product;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OrderItemDto {


    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;

}
