package lk.crossorigin.agency.dto;

import lk.crossorigin.agency.entity.OrderDetail;

import java.util.ArrayList;
import java.util.Date;

public class OrderDTO {
    private String id;
    private Date date;
    private ArrayList<OrderDetail> orderDetails;

    public OrderDTO() {
    }

    public OrderDTO(String id, Date date) {
        this.id = id;
        this.date = date;
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
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", orderDetails=" + orderDetails +
                '}';
    }

}
