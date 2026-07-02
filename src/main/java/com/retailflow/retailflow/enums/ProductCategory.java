package com.retailflow.retailflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

// Defines the allowed product categories in the system
public enum ProductCategory {
    electronics,
    clothes,
    mobiles;

    // @JsonCreator tells Jackson to use this method when deserializing category from JSON request body
    // It does a case-sensitive match — returns null if the value doesn't match any enum constant
    @JsonCreator
    public static ProductCategory toString(String category){
        for(ProductCategory productCategory:ProductCategory.values()){
            if(productCategory.name().equals(category)){
                return productCategory;
            }
        }
        return null;
    }
}

