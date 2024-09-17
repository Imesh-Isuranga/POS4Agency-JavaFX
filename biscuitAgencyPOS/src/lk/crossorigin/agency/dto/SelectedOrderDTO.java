package lk.crossorigin.agency.dto;

import lk.crossorigin.agency.entity.OrderDetail;

import java.util.ArrayList;
import java.util.Date;

public class SelectedOrderDTO {
    private int auto_id;
    private String orderId;


    public SelectedOrderDTO() {
    }

    public SelectedOrderDTO(String id) {
        this.orderId = id;
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
        return "OrderDTO{" +
                "auto_id=" + auto_id +
                ", id='" + orderId + '\'' +
                '}';
    }
}