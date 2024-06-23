package lk.crossorigin.agency.view.tm;


import javafx.scene.control.Button;

public class ReturnStockTM {
    private String itemCode;
    private int boxQty;
    private int itemQty;
    private Button btn;

    public ReturnStockTM() {
    }

    public ReturnStockTM(String itemCode, int boxQty, int itemQty, Button btn) {
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
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
                ", btn=" + btn +
                '}';
    }
}
