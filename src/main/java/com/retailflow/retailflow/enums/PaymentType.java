package com.retailflow.retailflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentType {
    cash,
    installment;
    @JsonCreator
    public static PaymentType toString(String paymentType){
        for(PaymentType paymentType1:PaymentType.values()){
            if(paymentType1.name().equals(paymentType)){
                return paymentType1;
            }
        }
        return null;
    }
}
