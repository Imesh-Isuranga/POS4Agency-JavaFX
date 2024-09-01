package lk.crossorigin.agency.entity;

import java.util.Date;

public class OrderHistory {

    private int id;
    private String invoice_num;
    private String name_of_dealer;
    private double total;
    private double cash;
    private double credit;
    private double cheque;
    private String cheque_no;
    private double mr;
    private String discount;

    public OrderHistory() {
    }

    public OrderHistory(int id, String invoice_num, String name_of_dealer, double total, double cash, double credit, double cheque, String cheque_no, double mr, String discount) {
        this.id = id;
        this.invoice_num = invoice_num;
        this.name_of_dealer = name_of_dealer;
        this.total = total;
        this.cash = cash;
        this.credit = credit;
        this.cheque = cheque;
        this.cheque_no = cheque_no;
        this.mr = mr;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice_num() {
        return invoice_num;
    }

    public void setInvoice_num(String invoice_num) {
        this.invoice_num = invoice_num;
    }

    public String getName_of_dealer() {
        return name_of_dealer;
    }

    public void setName_of_dealer(String name_of_dealer) {
        this.name_of_dealer = name_of_dealer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCheque() {
        return cheque;
    }

    public void setCheque(double cheque) {
        this.cheque = cheque;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public double getMr() {
        return mr;
    }

    public void setMr(double mr) {
        this.mr = mr;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "id=" + id +
                ", invoice_num='" + invoice_num + '\'' +
                ", name_of_dealer='" + name_of_dealer + '\'' +
                ", total=" + total +
                ", cash=" + cash +
                ", credit=" + credit +
                ", cheque='" + cheque + '\'' +
                ", cheque_no='" + cheque_no + '\'' +
                ", mr=" + mr +
                ", discount=" + discount +
                '}';
    }
}
