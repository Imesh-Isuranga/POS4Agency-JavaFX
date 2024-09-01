package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class CreditTM {
    private int id;
    private String shopId;

    private double credit;


    public CreditTM() {
    }

    public CreditTM(int id, String shopId, double credit) {
        this.id = id;
        this.shopId = shopId;
        this.credit = credit;
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

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "CreditTM{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", credit=" + credit +
                '}';
    }
}
