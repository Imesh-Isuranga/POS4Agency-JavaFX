package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class MonthlyReportTM {
    private Date date;
    private double total;
    private double return_tot;
    private double discount;

    public MonthlyReportTM() {
    }

    public MonthlyReportTM(Date date, double total, double return_tot, double discount) {
        this.date = date;
        this.total = total;
        this.return_tot = return_tot;
        this.discount = discount;
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

    public double getReturn_tot() {
        return return_tot;
    }

    public void setReturn_tot(double return_tot) {
        this.return_tot = return_tot;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "MonthlyReportTM{" +
                "date=" + date +
                ", total=" + total +
                ", return_tot=" + return_tot +
                ", discount=" + discount +
                '}';
    }
}
