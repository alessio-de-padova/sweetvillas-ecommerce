package com.nimesia.sweetvillas.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

public class ProductInfoDTO {
    private @Getter
    @Setter
    Integer id;
    @Valid
    private @Getter @Setter
    Set<TextDTO> names;
    @Valid
    private @Getter @Setter Set<CategoryDTO> categories;
    private @Getter @Setter
    BigDecimal price;
}
