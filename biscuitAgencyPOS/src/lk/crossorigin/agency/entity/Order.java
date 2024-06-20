package lk.crossorigin.agency.entity;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String id;
    private Date date;
    private ArrayList<OrderDetail> orderDetails;


    public Order() {
    }

    public Order(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Order(String id, Date date, ArrayList<OrderDetail> orderDetails) {
        this.id = id;
        this.date = date;
        this.orderDetails = orderDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
