package atmin.controller;

import atmin.dto.CartItemRequest;
import atmin.entity.CartItem;
import atmin.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@Valid @RequestBody CartItemRequest request) {
        log.info("Nhận request thêm vào giỏ hàng từ endpoint /api/cart/add: {}", request);
        CartItem result = cartService.addToCart(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUser(@PathVariable String userId) {
        log.info("Nhận request lấy giỏ hàng từ endpoint /api/cart/{}", userId);
        List<CartItem> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(items);
    }
}