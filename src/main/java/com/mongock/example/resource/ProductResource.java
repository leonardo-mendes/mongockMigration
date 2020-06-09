package com.mongock.example.resource;

import com.mongock.example.domain.Product;
import com.mongock.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductRepository productRepository;

    @GetMapping
    @ResponseStatus(code = OK)
    public Iterable<Product> retrieveHistory() {
        return productRepository.findAll();
    }
}
