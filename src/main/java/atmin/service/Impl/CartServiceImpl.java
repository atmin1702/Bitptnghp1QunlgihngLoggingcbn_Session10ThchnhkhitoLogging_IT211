package atmin.service.Impl;

import atmin.dto.CartItemRequest;
import atmin.entity.CartItem;
import atmin.repository.CartItemRepository;
import atmin.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public CartItem addToCart(CartItemRequest request) {
        log.info("Bắt đầu xử lý thêm sản phẩm vào giỏ hàng: userId={}, productId={}, quantity={}",
                request.getUserId(), request.getProductId(), request.getQuantity());

        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(
                request.getUserId(), request.getProductId());

        CartItem savedItem;
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            savedItem = cartItemRepository.save(item);
            log.info("Sản phẩm đã tồn tại trong giỏ. Cộng dồn thành công. Trạng thái mới: {}", savedItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .userId(request.getUserId())
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .build();
            savedItem = cartItemRepository.save(newItem);
            log.info("Sản phẩm chưa có trong giỏ. Tạo mới thành công. Bản ghi mới: {}", savedItem);
        }

        return savedItem;
    }

    @Override
    public List<CartItem> getCartItems(String userId) {
        log.info("Lấy danh sách giỏ hàng cho userId={}", userId);
        return cartItemRepository.findByUserId(userId);
    }
}