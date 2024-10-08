package lk.crossorigin.agency.entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private int auto_id;
    private String orderId;
    private Date date;
    private String shopId;
    private ArrayList<OrderDetail> orderDetails;

    public Order() {
    }



    public Order(String id, Date date, String shopId) {
        this.orderId = id;
        this.date = date;
        this.shopId = shopId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + orderId + '\'' +
                ", date=" + date +
                ", shopId='" + shopId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}