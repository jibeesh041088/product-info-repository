package com.jibeesh.productinfo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductModel {

    private Long id;

    private String name;

    private String description;

    private Double price;
}
