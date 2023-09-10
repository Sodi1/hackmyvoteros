package com.rosatom.myvote.model.enums;

public enum Order {
    negative(4),
    positive(3),
    neutral(2),
    count(5);

    private int order;
    Order(int order) {
        this.order = order;
    }

    public int getOrder(){
        return this.order;
    }
}
