package com.nimesia.sweetvillas.services;

import com.nimesia.sweetvillas.dao.CartProductRepository;
import com.nimesia.sweetvillas.entities.CartProductEntity;
import com.nimesia.sweetvillas.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
@Transactional
public class CartProductService extends AbsService {

    @Autowired
    private CartProductRepository repository;
    @Autowired
    private ProductService productService;

    public CartProductEntity save(@NotNull  CartProductEntity cartProduct) {
        ProductEntity product = productService.get(cartProduct.getProduct().getId());
        if (product.getQuantity() < cartProduct.getQuantity()) {
                throw new IllegalStateException("QuantityInvalid");
        }
        cartProduct.setTotalPrice( product.getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantity() )) );
        CartProductEntity newCartProduct = repository.save(cartProduct);

        product.setQuantity( product.getQuantity() - newCartProduct.getQuantity());
        productService.save(product);

        return newCartProduct;
    }

}