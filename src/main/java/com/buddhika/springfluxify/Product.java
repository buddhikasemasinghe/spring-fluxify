package com.buddhika.springfluxify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
class Product {
    private Long id;
    private String brand;
    private String year;
    private String model;
    private BigDecimal price;
}
