package lk.crossorigin.agency.view.tm;


import javafx.scene.control.Button;

public class ReturnStockTM {
    private String itemCode;
    private int boxQty;
    private int itemQty;
    private double perQty;
    private Button btn;

    public ReturnStockTM() {
    }

    public ReturnStockTM(String itemCode, int boxQty, int itemQty, double perQty) {
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.perQty = perQty;
    }

    public ReturnStockTM(String itemCode, int boxQty, int itemQty, double perQty, Button btn) {
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.perQty = perQty;
        this.btn = btn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getPerQty() {
        return perQty;
    }

    public void setPerQty(double perQty) {
        this.perQty = perQty;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ReturnStockTM{" +
                "itemCode='" + itemCode + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", perQty=" + perQty +
                ", btn=" + btn +
                '}';
    }
}
