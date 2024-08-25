package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class OrderDetailsTM {

    private int no;
    private String invNo;
    private String NameofDealer;
    private double total;
    private double cash;
    private double credit;
    private double cheque;
    private String chequeNum;
    private double MR;
    private String discount;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(int no, String invNo, String nameofDealer, double total, double cash, double credit, double cheque, String chequeNum, double MR, String discount) {
        this.no = no;
        this.invNo = invNo;
        NameofDealer = nameofDealer;
        this.total = total;
        this.cash = cash;
        this.credit = credit;
        this.cheque = cheque;
        this.chequeNum = chequeNum;
        this.MR = MR;
        this.discount = discount;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public String getNameofDealer() {
        return NameofDealer;
    }

    public void setNameofDealer(String nameofDealer) {
        NameofDealer = nameofDealer;
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

    public String getChequeNum() {
        return chequeNum;
    }

    public void setChequeNum(String chequeNum) {
        this.chequeNum = chequeNum;
    }

    public double getMR() {
        return MR;
    }

    public void setMR(double MR) {
        this.MR = MR;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "no=" + no +
                ", invNo='" + invNo + '\'' +
                ", NameofDealer='" + NameofDealer + '\'' +
                ", total=" + total +
                ", cash=" + cash +
                ", credit=" + credit +
                ", cheque='" + cheque + '\'' +
                ", chequeNum='" + chequeNum + '\'' +
                ", MR=" + MR +
                ", discount=" + discount +
                '}';
    }
}
