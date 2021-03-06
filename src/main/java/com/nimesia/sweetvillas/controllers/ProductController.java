package com.nimesia.sweetvillas.controllers;

import com.nimesia.sweetvillas.dto.ProductDTO;
import com.nimesia.sweetvillas.models.ProductEntity;
import com.nimesia.sweetvillas.mappers.ProductMapper;
import com.nimesia.sweetvillas.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ProductController extends AbsController {

    @Autowired
    private ProductService svc;
    @Autowired
    private ProductMapper mapper;

    /**
     * Get by id request
     *
     * @param id
     */
    @GetMapping("/products/get")
    public ResponseEntity<ProductDTO> get(
            @RequestParam(name = "id") Integer id
    ) {
        ProductDTO product = mapper.map(svc.get(id));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(product);
    }

    /**
     * Create product
     *
     * @param product
     */
    @PostMapping("/products/save")
    @Valid
    public ResponseEntity save(
            @Valid @RequestBody ProductDTO product
    ) {

        if ( !getRequestUser().getRole().getId().equals( getSTR() ) ) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Cannot create product");
        }

        ProductEntity productEntity = mapper.map(product);
        ProductDTO productDTO = mapper.map(svc.save(productEntity));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( productDTO.getId() );

    }

}
