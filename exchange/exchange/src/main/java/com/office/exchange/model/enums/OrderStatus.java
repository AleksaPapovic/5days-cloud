package com.office.exchange.model.enums;

public enum OrderStatus {
    OPEN("OPEN"), 
    CLOSE("CLOSE");
    
    public final String label;

    private OrderStatus(String label) {
        this.label = label;
    }
}
