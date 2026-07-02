package com.retailflow.retailflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SaleStatus {
    completed,
    refund;
    @JsonCreator
    public static SaleStatus toString(String refunded){
        for(SaleStatus saleStatus:SaleStatus.values()){
            if(saleStatus.name().equals(refunded)){
                return saleStatus;
            }
        }
        return null;
    }

}
