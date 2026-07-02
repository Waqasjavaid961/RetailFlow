package com.retailflow.retailflow.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.retailflow.retailflow.model.InstallmentPayment;

public enum InstallmentStatus {
    completed,
    pending,
    cancelled;
    @JsonCreator
    public static InstallmentStatus toString(String status){
        status=status.toLowerCase();
        for(InstallmentStatus status1:InstallmentStatus.values()){
            if(status1.name().equals(status)){
                return status1;
            }
        }
        return null;
    }
}
