package atmin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {

    @NotBlank(message = "userId không được để trống")
    private String userId;

    @NotBlank(message = "productId không được để trống chuỗi rỗng")
    private String productId;

    @NotNull(message = "quantity không được null")
    @Min(value = 1, message = "quantity phải lớn hơn 0")
    private Integer quantity;
}