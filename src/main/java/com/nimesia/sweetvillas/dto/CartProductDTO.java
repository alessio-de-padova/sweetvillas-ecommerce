package com.nimesia.sweetvillas.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;

public class CartProductDTO extends AbsDTO {
    private @Getter @Setter Integer id;
    private @Getter @Setter ProductDTO product;
    private @Getter @Setter UserDTO user;
    @DecimalMin("1")
    private @Getter @Setter Integer quantity;
    private @Getter @Setter
    BigDecimal totalPrice;
}
