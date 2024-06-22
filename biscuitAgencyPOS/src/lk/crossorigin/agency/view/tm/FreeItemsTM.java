package lk.crossorigin.agency.view.tm;


import javafx.scene.control.Button;

public class FreeItemsTM {
    private String freeItemCode;
    private int freeBoxQty;
    private int freeItemQty;
    private double discountItemTotal;
    private Button btn;


    public FreeItemsTM() {
    }

    public FreeItemsTM(String freeItemCode, int freeBoxQty, int freeItemQty, double discountItemTotal, Button btn) {
        this.freeItemCode = freeItemCode;
        this.freeBoxQty = freeBoxQty;
        this.freeItemQty = freeItemQty;
        this.discountItemTotal = discountItemTotal;
        this.btn = btn;
    }

    public String getFreeItemCode() {
        return freeItemCode;
    }

    public void setFreeItemCode(String freeItemCode) {
        this.freeItemCode = freeItemCode;
    }

    public int getFreeBoxQty() {
        return freeBoxQty;
    }

    public void setFreeBoxQty(int freeBoxQty) {
        this.freeBoxQty = freeBoxQty;
    }

    public int getFreeItemQty() {
        return freeItemQty;
    }

    public void setFreeItemQty(int freeItemQty) {
        this.freeItemQty = freeItemQty;
    }

    public double getDiscountItemTotal() {
        return discountItemTotal;
    }

    public void setDiscountItemTotal(double discountItemTotal) {
        this.discountItemTotal = discountItemTotal;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "DiscountTM{" +
                "freeItemCode='" + freeItemCode + '\'' +
                ", freeBoxQty=" + freeBoxQty +
                ", freeItemQty=" + freeItemQty +
                ", discountItemTotal=" + discountItemTotal +
                ", btn=" + btn +
                '}';
    }
}


