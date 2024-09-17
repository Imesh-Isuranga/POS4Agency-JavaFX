package lk.crossorigin.agency.entity;

import java.util.ArrayList;
import java.util.Date;

public class SelectedOrder {
    private int auto_id;
    private String orderId;

    public SelectedOrder() {
    }


    public SelectedOrder(int auto_id, String orderId) {
        this.auto_id = auto_id;
        this.orderId = orderId;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id='" + orderId + '\'' +
                '}';
    }
}