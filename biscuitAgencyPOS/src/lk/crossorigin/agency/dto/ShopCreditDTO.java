package lk.crossorigin.agency.dto;

import java.util.Date;

public class ShopCreditDTO {

    private int id;
    private String shopId;
    private Date date_paid;
    private double amount;
    private String paymentDetails;

    public ShopCreditDTO() {
    }

    public ShopCreditDTO(String shopId, Date date_paid, double amount, String paymentDetails) {
        this.shopId = shopId;
        this.date_paid = date_paid;
        this.amount = amount;
        this.paymentDetails = paymentDetails;
    }


    public ShopCreditDTO(int id, String shopId, Date date_paid, double amount, String paymentDetails) {
        this.id = id;
        this.shopId = shopId;
        this.date_paid = date_paid;
        this.amount = amount;
        this.paymentDetails = paymentDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getDate_paid() {
        return date_paid;
    }

    public void setDate_paid(Date date_paid) {
        this.date_paid = date_paid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        return "ShopCreditDTO{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", date_paid=" + date_paid +
                ", amount=" + amount +
                ", paymentDetails='" + paymentDetails + '\'' +
                '}';
    }
}
