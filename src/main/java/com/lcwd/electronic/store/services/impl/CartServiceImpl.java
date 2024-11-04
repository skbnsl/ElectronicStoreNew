package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CartItemrepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.modelmbean.ModelMBean;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemrepository cartItemrepository;

    @Override
    public CartDto addItemsToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        if(quantity<=0){
            throw new BadApiRequest("Requested Quantity is not valid!!");
        }

        String productId = request.getProductId();

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found with given productId"));

        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found with given userId"));
        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform cart operation
        //if cart items already present, then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item->{
            if(item.getProduct().getProductId().equals(productId)){
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getDiscountedPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        //create items
        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        //cartItem is id of cartItem

        //cartItem1 is object of cartItem
        CartItem cartItem1 = cartItemrepository.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("cart is not found with given id"));
        cartItemrepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found with given userId"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("cart not found with given user"));
        cart.getItems().clear();
        System.out.println(cart.getItems());

        cartRepository.delete(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found with given userId"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("cart not found with given user"));

        return mapper.map(cart,CartDto.class);
    }
}
