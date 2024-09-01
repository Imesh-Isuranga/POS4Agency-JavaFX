package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class ShopCreditTM {
    private int id;
    private String shopId;
    private Date date;
    private Double amount;
    private String bank_details;


    public ShopCreditTM() {
    }

    public ShopCreditTM(int id, String shopId, Date date, Double amount, String bank_details) {
        this.id = id;
        this.shopId = shopId;
        this.date = date;
        this.amount = amount;
        this.bank_details = bank_details;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBank_details() {
        return bank_details;
    }

    public void setBank_details(String bank_details) {
        this.bank_details = bank_details;
    }

    @Override
    public String toString() {
        return "ShopCreditTM{" +
                "id='" + id + '\'' +
                ", shopId='" + shopId + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", bank_details='" + bank_details + '\'' +
                '}';
    }
}
