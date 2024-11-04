package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {

    //add items to cart
    //case 1 :: if cart is not availabel for user then we will create the cart and add the items
    //case 2 :: if cart is availabel then we will add items in cart
    CartDto addItemsToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //remove all items from cart
    void clearCart(String userId);


    CartDto getCartByUser(String userId);



}
