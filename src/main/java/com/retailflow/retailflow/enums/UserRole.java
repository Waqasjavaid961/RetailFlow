package com.retailflow.retailflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

// Defines the two types of users in the system
// Owner — manages the store and products
// Customer — browses and purchases products
public enum UserRole {
    customer,
    owner;

    // Custom JSON deserializer — converts a string from the request into a UserRole enum value
    @JsonCreator
    public static  UserRole toString(String role){
        for(UserRole userRole:UserRole.values()){
            if(userRole.name().equals(role)){
                return userRole;
            }
        }
        return null;

    }
}

