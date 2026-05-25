package atmin.service;

import atmin.dto.CartItemRequest;
import atmin.entity.CartItem;

import java.util.List;

public interface CartService {
    CartItem addToCart(CartItemRequest request);
    List<CartItem> getCartItems(String userId);
}