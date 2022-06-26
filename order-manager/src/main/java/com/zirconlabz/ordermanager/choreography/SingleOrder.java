package com.zirconlabz.ordermanager.choreography;

import java.io.Serializable;

public class SingleOrder implements Serializable {

    public int id;
    public String item;
    public int qty;
    public int cost;
    private int costFactor = 10;

    public boolean isFailed;
    public String failureReason;

    public SingleOrder() {

    }

    public SingleOrder(int id, String item, int qty) {
        this.id = id;
        this.item = item;
        this.qty = qty;
        this.cost = qty * costFactor;
    }

    @Override
    public String toString() {
        return "[ id: "+id+", qty: " + qty + ", cost: " + cost
        + ", Failed reason: " + failureReason + ", isFailed:" + isFailed
                + "]";
    }

}
