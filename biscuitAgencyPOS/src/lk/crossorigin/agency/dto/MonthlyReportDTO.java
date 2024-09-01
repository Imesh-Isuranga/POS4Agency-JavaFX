package lk.crossorigin.agency.dto;

import java.util.Date;

public class MonthlyReportDTO {

    private int id;
    private Date date;
    private double total;
    private double mr;
    private double discount;

    public MonthlyReportDTO() {
    }

    public MonthlyReportDTO(Date date, double total, double mr, double discount) {
        this.date = date;
        this.total = total;
        this.mr = mr;
        this.discount = discount;
    }

    public MonthlyReportDTO(int id, Date date, double total, double mr, double discount) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.mr = mr;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMr() {
        return mr;
    }

    public void setMr(double mr) {
        this.mr = mr;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "MonthlyReportDTO{" +
                "id=" + id +
                ", date=" + date +
                ", total=" + total +
                ", mr=" + mr +
                ", discount=" + discount +
                '}';
    }
}
