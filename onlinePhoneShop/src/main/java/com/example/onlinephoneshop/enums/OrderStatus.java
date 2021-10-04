package com.example.onlinephoneshop.enums;

public enum OrderStatus {
    CREATED("Created"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private String state;

    private OrderStatus(String state) {
        this.state = state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static OrderStatus getByState(String state) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.state.equalsIgnoreCase(state)) {
                return status;
            }
        }
        return null;
    }
}
