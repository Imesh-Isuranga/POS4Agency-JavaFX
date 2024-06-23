package lk.crossorigin.agency.entity;

import java.util.Date;

public class OrderBook {
    private int id;
    private String bookId;
    private String InvId;
    private String orderId;
    private String shopId;

    public OrderBook() {
    }

    public OrderBook(String bookId, String invId, String orderId, String shopId) {
        this.bookId = bookId;
        InvId = invId;
        this.orderId = orderId;
        this.shopId = shopId;
    }

    public OrderBook(int id, String bookId, String invId, String orderId, String shopId) {
        this.id = id;
        this.bookId = bookId;
        InvId = invId;
        this.orderId = orderId;
        this.shopId = shopId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getInvId() {
        return InvId;
    }

    public void setInvId(String invId) {
        InvId = invId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", InvId='" + InvId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
